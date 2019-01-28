package myT;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;
import org.mybatis.example.BeanDefinition;
import org.mybatis.example.PropertyDefinition;

import spring.Advice;
import spring.IAdvice;
import spring.Resource;
import spring.UserService;

interface Service{
	public void serviceMethod();
	public void setDao(Dao dao);
}
class ServiceImpl implements Service{
//	private Dao dao = new DaoImpl();//第一个版本，强耦合
//	private Dao dao = (Dao)Container.getBean("dao");//第二个版本，采用容器实现初步解耦
	private Dao dao = null;
	@Override
	public void serviceMethod(){
		dao.daoMethod();
	}
	
	@Override
	public void setDao(Dao dao){
		this.dao = dao;//第三个版本，采用set注入
	}
}
interface Dao{
	public void daoMethod();
}
class DaoImpl implements Dao{
	@Override
	public void daoMethod(){
		System.out.println("dao");
	}
}

class Container{
	private static Map<String,Object> container = null;
	public static void init(){
		if(null == container){
			container = new HashMap<String, Object>();
			Dao dao = new DaoImpl();
			container.put("dao", dao);
			Service service = new ServiceImpl();
			container.put("service", service);
			
			service.setDao(dao);
		}
	}
	
	public static Object getBean(String beanId){
		if(null != beanId && !"".equals(beanId)){
			return container.get(beanId);
		}
		return null;
	}
}

interface BeanFactory{
	public Object getBean(String id);
}
//第四个版本，采用读取配置文件注入
class ClassPathXmlApplicationContext implements BeanFactory{
	private Map<String,Object> container = new HashMap<>();
	private Document docu;
	@Override
	public Object getBean(String id) {
		return container.get(id);
	}
	public ClassPathXmlApplicationContext(String filename){
		readXml(filename);
	}
	private void readXml(String filename) {
		SAXBuilder sax = new SAXBuilder();
		try{
			docu = sax.build(this.getClass().getClassLoader().getResourceAsStream(filename));
			Element rootElement = docu.getRootElement();
			List listBean = XPath.selectNodes(rootElement, "/beans/bean");
			for(int i=0;i<listBean.size();i++){
				Element element = (Element)listBean.get(i);
				String id = element.getAttributeValue("id");
				String clazz = element.getAttributeValue("class");
				Object obj = Class.forName(clazz).newInstance();
				container.put(id, obj);
			}
			Service service = (Service)container.get("service");
			Dao dao = (Dao)container.get("dao");
			service.setDao(dao);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
//第五个版本，封装抽象采用读取配置文件注入
class SecClassPathXmlApplicationContext implements BeanFactory{
	private Map<String,Object> container = new HashMap<>();
	private List<BeanDefinition> beansList = new ArrayList<>();
	private Document docu;
	@Override
	public Object getBean(String id) {
		return container.get(id);
	}
	public SecClassPathXmlApplicationContext(String filename){
		readXml(filename);
		initBeans();
//		insertObject();
		annotationInsert();
	}
	private void annotationInsert() {
		for(String key:container.keySet()){
			Object obj = container.get(key);
			if(null != obj){
				propertiesAnnotation(obj);
				fieldAnnotation(obj);
			}
		}
		
	}
	private void fieldAnnotation(Object bean) {
		try {
			Field[] fields = bean.getClass().getFields();
			for(Field field:fields){
				if(null != field && field.isAnnotationPresent(Resource.class)){
					Resource resource = field.getAnnotation(Resource.class);
					String name = "";
					Object object = null;
					if(null != resource.name() && !"".equals(resource.name())){
						String className = resource.name();
						object = container.get(className);
					}else{
						for(String key:container.keySet()){
							if(field.getType().isAssignableFrom(container.get(key).getClass())){
								object = container.get(key);
								break;
							}
						}
					}
					field.setAccessible(true);
					field.set(bean, object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void propertiesAnnotation(Object bean){
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
			for(PropertyDescriptor pro:propertyDescriptors){
				Method writeMethod = pro.getWriteMethod();
				if(null != writeMethod && writeMethod.isAnnotationPresent(Resource.class)){
					Resource resource = writeMethod.getAnnotation(Resource.class);
					String name = "";
					Object object = null;
					if(null != resource.name() && !"".equals(resource.name())){
						name = resource.name();
						object = container.get(name);
					}else{
						for(String key:container.keySet()){
							if(pro.getPropertyType().isAssignableFrom(container.get(key).getClass())){
								object = container.get(key);
								break;
							}
						}
					}
					writeMethod.setAccessible(true);
					writeMethod.invoke(bean, object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void insertObject() {
//		Service service = (Service)container.get("service");
//		Dao dao = (Dao)container.get("dao");
//		service.setDao(dao);
		
		//第六个版本，读取bean信息，获取ref连接描述
		for(BeanDefinition bean:beansList){
			Object obj = container.get(bean.getId());
			if(null != bean){
				try {
					//对象本身的属性
					PropertyDescriptor[] beanProperty = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
					//当对象本身的属性和配置定义ref中的属性id一致时，则利用反射注入属性值
					for(PropertyDefinition bd:bean.getPropertys()){
						for(PropertyDescriptor bp:beanProperty){
							if(bd.getName().equals(bp.getName())){
								Method writeMethod = bp.getWriteMethod();
								if(null != writeMethod){
									writeMethod.setAccessible(true);
									//从容器中获取配置中定义的ref对应的对象实例注入到这个依赖实例中
									writeMethod.invoke(obj, container.get(bd.getRef()));
								}
							}
							break;
						}
					}
					bean.getPropertys();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void initBeans() {
		if(null != beansList && beansList.size() > 0){
			for(BeanDefinition bean:beansList){
				if(null != bean.getClassname() && !"".equals(bean.getClassname().trim())){
					Object obj = null;
					try {
						obj = Class.forName(bean.getClassname()).newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
					container.put(bean.getId(), obj);	
				}
			}
		}
	}
	private void readXml(String filename) {
		SAXBuilder sax = new SAXBuilder();
		try{
			docu = sax.build(this.getClass().getClassLoader().getResourceAsStream(filename));
			Element rootElement = docu.getRootElement();
			List listBean = XPath.selectNodes(rootElement, "/beans/bean");
			for(int i=0;i<listBean.size();i++){
				Element element = (Element)listBean.get(i);
				String id = element.getAttributeValue("id");
				String clazz = element.getAttributeValue("class");
				BeanDefinition bean = new BeanDefinition();
				bean.setId(id);
				bean.setClassname(clazz);
				
				//第六个版本，读取bean信息，获取连接描述
				List<Element> propertyList = element.getChildren("property");
				for(int index=0;index<propertyList.size();index++){
					Element property = propertyList.get(index);
					String name = property.getAttributeValue("name");
					String ref = property.getAttributeValue("ref");
					PropertyDefinition propertyDefinition = new PropertyDefinition();
					bean.getPropertys().add(propertyDefinition);
				}
				//第六个版本，读取bean信息，获取连接描述
				beansList.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public class SpringTest {
	public static void main(String[] args) {
//		Service service = new ServiceImpl();//第一个版本，强耦合
//		service.serviceMethod();
		
//		Container.init();
//		Service service = (Service)Container.getBean("service");//采用容器实现初步解耦
//		service.serviceMethod();
		
		InputStream in = SpringTest.class.getResourceAsStream("/config.properties");
		try {
			UserService user = (UserService)new ProxyFactory(in,new Advice()).getInstance("beanSpring");
			user.sys();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}




class ProxyFactory{
	private IAdvice advice;
	private Object obj;
	private Properties pro = new Properties();
	
	public ProxyFactory(InputStream input,IAdvice advice){
		try {
			pro.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.advice = advice;
	}
	
	
	public Object getInstance(String name) throws Exception{
		String className = pro.getProperty(name);
		Class clazz = Class.forName(className);
		obj = clazz.newInstance();
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler(){
			@Override
			public Object invoke(Object object,Method method,Object[] args) throws Throwable{
				advice.beforeMethod();
				Object oj = method.invoke(obj, args);
				advice.afterMethod();
				return oj;
			}
			
		});
	}
	
}


