package myT;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
class MyFileter implements CallbackFilter{
	@Override
	public int accept(Method arg0) {
		if("query".equals(arg0.getName())){
			return 1;
		}
		return 0;
	}
}

class PerCglib implements MethodInterceptor{
	private Object tar;
	public Object getInstance(Object tar){
		this.tar = tar;
		Enhancer en = new Enhancer();//创建加强器，用来创建动态代理类
		en.setSuperclass(this.tar.getClass());
		en.setCallback(this); //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
//		en.setCallbackFilter(new MyFileter());
		return en.create();
	}
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		// TODO Auto-generated method stub
		proxy.invokeSuper(obj, args);
		return null;
	}
}

//class CgL implements MethodInterceptor{
//	private Object per;
//	@Override
//	public Object intercept(Object obj,Method method,Object[] args,MethodProxy proxy) throws Throwable{
//		return proxy.invokeSuper(per, args);
//	}
//	
//	public Object getInstance(Object per){
//		this.per = per;
//		Enhancer en = new Enhancer();
//		en.setSuperclass(per.getClass());
//		en.setCallback(this);
//		return en.create();
//	}
//}

class Pro{
	private Person per = null;
	public Object getInstance(Person person){
		this.per = person;
		Class cla = per.getClass();
		return Proxy.newProxyInstance(cla.getClassLoader(),cla.getInterfaces(),new InvocationHandler(){
			@Override
			public Object invoke(Object obj,Method method,Object[] args) throws Throwable{
				return method.invoke(per, args);
			}
		});
	}
}

class TestProxy implements InvocationHandler{
	Object user = null;
	
	public Object getInstance(Object user){
		this.user = user;
		Class clazz = user.getClass();
		Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
		return obj;
	}

	@Override
	public Object invoke(Object obj, Method method, Object[] arg2)
			throws Throwable {
		System.out.println("代理开始");
		return method.invoke(user, arg2);
	}
	
}

class FanT<T> implements TestFx<T>{
	T oj;
	public void setNam(T obj){
		oj = obj;
	}
	public T getNam(){
		return oj;
	}
	
	public void setPas(FanT<? extends T> obj){
		
	}
	@Override
	public void sys() {
		
	}
	
	//泛型方法
	public <E> E show(E arg){
		return arg;
	}
}

class Thread1 extends Thread{
	@Override
	public void run(){
		add();
	}

//	public void showo(FanT<?> t){}
	static int num = 0;
	
	private synchronized void add() {
//		synchronized (this) {
			for(int i=0;i<200;i++){
				num++;
				System.out.println(num);
			}
//		}
	}
}

class Runnable1 implements Runnable{
	@Override
	public void run(){
		for(int i=0;i<100;i++){
			System.out.println("run1");
		}
	}
}

public class TestBase {
	private int next;
	private static int fis;
	{
		//初始化块，这种方式比较少见，多数采用构造方法初始化
		next = 1;
	}
	
	static {
		fis = 0;
	}
	private String name; 
	//main里面的数组接收的是命令行参数
	public static void main(String[] args) {
		float bb = 25.0f;
		float x = 10/4;
		System.out.println(x);
//		char chr = 2;
//		System.out.println(chr);
//		System.gc();
//		Runtime.getRuntime().gc();
		
//		Double.parseDouble("1");
//		Double.valueOf("1");
		
//		StringBuffer strbf = new StringBuffer("asdf");
//		strbf.reverse();
//		System.out.println(strbf);
		
		int a = 5;
		int b = 3;
		double cc = 3.5;
		float dd = 23;
		a = (int)cc;
		
//		BigDecimal bi = new BigDecimal(Double.toString(3.2));
//		BigDecimal bi2 = new BigDecimal(Double.toString(3.2));
//		bi.add(bi2).doubleValue();
		
		//运算
		a = a^b;
		b = a^b;
		a = a^b;
//		System.out.println(a^b);
//		System.out.println(b^a);
//		System.out.println(a^b^b);
//		System.out.println(a>>2);
//		System.out.println(b);
		//子串
		String sub = "hello";
		String minSub = sub.substring(0, 2);
//		System.out.println(minSub);
		//切割
		String sub2 = "h,s,d,s";
		String[] minSub2 = sub2.split(",", 3);//数组长度，3代表前面两个切割后剩下的作为一个元素
//		String[] minSub2 = sub2.split(",");//全部切割
		for(int i=0;i<minSub2.length;i++){
//			System.out.println(minSub2[i]);
		}
		//代码单元
		String sub3 = "aaswegf";
		int num = sub3.codePointCount(0, sub3.length());
		char cn = sub3.charAt(2);
		int indexof = sub3.indexOf("s", 3);
		sub3 = sub3.replaceAll("a", "s");
//		System.out.println(sub3);
		//内存
		String s0="kvill";
		String s1="kvill";
		String s3="ill";
		final String s5="ill"; 
		String s2="kv" + "ill";
		String s4="kv" + s3;
		String s6="kv" + s5;
		System.out.println( s0==s1 );
		System.out.println( s0==s2 );
		System.out.println( s0==s4 );//由于在字符串的"+"连接中，有字符串引用存在，而引用的值在程序编译期是无法确定的，即"a" + bb无法被编译器优化，只有在程序运行期来动态分配并将连接后的新地址赋给b
		System.out.println( s0==s6 );//对于final修饰的变量，它在编译时被解析为常量值的一个本地拷贝存储到自己的常量 池中或嵌入到它的字节码流中
		//		String s0="kvill";
//		String s1=new String("kvill");
//		String s2="kv" + new String("ill");
//		System.out.println( s0==s1 );
//		System.out.println( s0==s2 );
//		System.out.println( s1==s2 );
		//赋值
		String agc = "?hello?";
		String cd = agc;//地址相等，但是如果某一个改变了值，地址就不同了
		System.out.println(cd == agc);
		cd.toUpperCase();//调用的所有方法都是产生新的，没有赋值给原来的是修改不了的
		agc = agc.trim();
		System.out.println(agc + " and " + cd);
		
		//输出
		//当整数位>格式整数位, 按实际显示;当整数位<格式整数位, 显示前面缩进相应的位数
		double sub4 = 3333.3333;
		System.out.printf("%7.2f",sub4);
//		System.out.printf("%f",sub4);
		
		
		//输入
		Scanner sc = new Scanner(System.in);
//		System.out.println("what is your name?");
//		String in = sc.nextLine();//nextLine 可接收空格
//		System.out.println(in);
		
		
		//数组
		int[] arr = new int[20];
		int[] arr2 = {4,1,3};
		arr = Arrays.copyOf(arr2, arr.length);//深拷贝
		System.arraycopy(arr2, 0, arr, 0, arr2.length);//深拷贝
		Arrays.sort(arr);
//		System.out.print(Arrays.toString(arr));
		
		int[][] doublearr = {
					{1,2},
					{1,2,1,3}
				};
//		System.out.print(doublearr[1][3]);
		System.out.print(Arrays.deepToString(doublearr));
		//时间
		Date date = new Date();
		date.before(new Date());
		Calendar calendar = Calendar.getInstance();
//		System.out.println(calendar.getTime());
//		System.out.println(calendar.get(Calendar.MONTH));

//		System.out.println(new TestBase().getUser());
		
		//引用基本类型没用
		int fisnum = 10;int secnum = 9;
//		new TestBase().change(fisnum,secnum);
//		System.out.println(fisnum + " " + secnum);
		SimpleDateFormat sim = new SimpleDateFormat();
		sim.format(date);
//		sim.parse("");
		
		//反射实例化对象
		try {
			TestBase test = (TestBase)Class.forName("myT.TestBase").newInstance();
			TestBase test2 = TestBase.class.newInstance();
			Class.forName("myT.TestBase").getDeclaredFields()[0].setInt(test, 3);
			System.out.println(test.next);
//			//带参数的构造器，构造器权限得能用
//			Constructor con = TestBase.class.getConstructor(String.class,int.class);
//			con.newInstance("",1);
//			//所有权限的都能获取
//			Constructor cons = TestBase.class.getDeclaredConstructor(String.class,int.class);
//			cons.setAccessible(true);//必须设置才可以访问私有的成员 
//			con.newInstance("",1);
//			//调用属性
//			TestBase.class.getField("pass");
			Field fi = TestBase.class.getDeclaredField("name");
//			fi.setAccessible(true);//设置权限
			fi.set(test, "");
//			//调用方法
//			Method met = TestBase.class.getMethod("getName", String.class);//类的所有共有方法，这就包括自身的所有public方法，和从基类继承的、从接口实现的所有public方法
//			met.invoke(test, "");//后面是参数
//			Method me = TestBase.class.getDeclaredMethod("getName");//类自身声明的所有方法，包含public、protected和private方法
//			me.setAccessible(true);
//			me.invoke(test);
			TestBase.class.getGenericSuperclass();
//			ParameterizedType type = (ParameterizedType)TestBase.class.getGenericSuperclass();//获取泛型参数
//			Class entityClass = (Class)type.getActualTypeArguments()[0];
			
			//外部静态方法访问内部非静态
			ClaT clat = test.new ClaT();
//			Class.forName("myT.TestBase$ClaT").getDeclaredConstructors()[0].newInstance();
			
			
			//内部类
			test.testInst();
			
			//hashcode,equals,tostring
			ClaT cla = test.new ClaT();
			Map<ClaT,String> mapT = new HashMap<ClaT,String>();
			mapT.put(cla, "1");
			ClaT cla2 = test.new ClaT();
			cla2.equals(cla);
			mapT.get(cla2);
			
			ClatS cl = new ClatS();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//代理
		Person person = new Student();
		Person per = (Person)new TestProxy().getInstance(person);
		per.sys();
		
		//没实现接口的动态代理报错
//		Pr pr = (Pr)new Pro().getInstance(new Pr());
//		pr.sys();
		
		//cglib代理
		PerCglib cl = new PerCglib();
		Student stu = (Student)cl.getInstance(new Student());
		stu.sys();
		
		//泛型类
		FanT<Person> fan = new FanT<Person>();
		fan.setNam(person);
		fan.getNam();
		
		FanT<Student> student = new FanT<Student>();
		fan.setPas(student);//不行，只能使用通配符
		System.out.println(fan.getClass() == student.getClass());
		
		//容器
		List<String> coll = new ArrayList<>();
		Collections.sort(coll);//升序
		Collections.reverse(coll);//逆序，要想降序，先拍好升序再逆序
		Collections.binarySearch(coll, "");//二分法查找
		Iterator<String> it = coll.iterator();
		while(it.hasNext()){
			it.next();
		}
		Set set = new HashSet();
		set.add(123);
		set.add(456);
		set.add(new String("AA"));
		set.add(new String("AA"));
		set.add("BB");
		set.add(null);
		System.out.println(set.size());
		System.out.println(set);
		
		Map<String,String> map =  new HashMap<String, String>();
		for(Map.Entry<String, String> entry:map.entrySet()){
			entry.getKey();
		}
		for(String key:map.keySet()){
			map.get(key);
		}
//		Map<String,String> map0 = new HashMap<String,String>();
//		map0.put(null, null);
//		Map<String,String> map1 = new ConcurrentHashMap<String,String>();
//		map1.put(null, null);
//		Map<String,String> mapp = new Hashtable<String,String>();
//		mapp.put(null, null);
		//多线程
//		Thread1 thread = new Thread1();
//		thread.start();
//		Thread1 thread2 = new Thread1();
//		thread2.start();
//		new Thread(new Runnable1()).start();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("sr");
		buffer.append(1);
		System.out.println(buffer.toString());
		
		//IO
		try{
//			File file = new File("E:\\Projects\\test2.txt");//只是存在内存
//			file.createNewFile();//这才在硬盘中创建
//			file.mkdirs();
//			file.list();//返回文件名
//			file.listFiles();//返回文件类型
			
			//字节流
			int by;
			byte[] byt = new byte[1024];
			StringBuffer strb  = new StringBuffer();
			InputStream in = new FileInputStream("E://Projects//test.txt");
			OutputStream out = new FileOutputStream("E://Projects//testout.txt");
////			while((by = in.read()) != -1){
////				System.out.println((char)by);
////				out.write(by);
////			}
			//带缓冲数组
			while((by = in.read(byt)) != -1){
				strb.append(new String(strb));
//				System.out.println(byt.toString());
				out.write(byt);
			}
//			while((by = in.read(byt)) != -1){
//				out.write(byt,0,by);
//			}
//			in.close();
//			out.close();
			
			//字符流
			int ch;
			char[] cha = new char[512];
			FileReader read = new FileReader("E:\\Projects\\test.txt");
			FileWriter wri = new FileWriter("E:/Projects/testout.txt");
			while((ch = read.read(cha)) != -1){
//				System.out.println((char)cha);
				wri.write(cha);
			}
			read.close();
			wri.close();
			
			//缓冲流，速度较快
			int bybu;
			byte[] bytbu = new byte[1024];
			InputStream inbu = new FileInputStream("E://Projects//test.txt");
			OutputStream outbu = new FileOutputStream("E://Projects//testout.txt");
			BufferedInputStream buff = new BufferedInputStream(inbu);
			BufferedOutputStream outbuff = new BufferedOutputStream(outbu);
			while((bybu = buff.read(bytbu)) != -1){
				outbuff.write(bytbu,0,bybu);
				outbuff.flush();//把存在中间数组的值全部写过去
			}
			buff.close();
			outbuff.close();
			
			FileReader buread = new FileReader("E:\\Projects\\test.txt");
			FileWriter buwri = new FileWriter("E:/Projects/testout.txt");
			BufferedReader bure = new BufferedReader(buread);
			BufferedWriter buwr = new BufferedWriter(buwri);
			String strr = null;
			while((strr = bure.readLine()) != null){
//				System.out.println((char)cha);
				buwr.write(strr);
				buwr.flush();
			}
			bure.close();
			buwr.close();
			
			//对象流，要求类的及类的属性都实现序列化接口
			Student st = new Student();
			ObjectOutputStream bi = new ObjectOutputStream(new FileOutputStream("E:\\Projects\\student.txt"));
			bi.writeObject(st);
			bi.flush();
			ObjectInputStream oi = new ObjectInputStream(new FileInputStream("E:\\Projects\\student.txt"));
			Student ob = (Student)oi.readObject();
			oi.close();
			bi.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(System.currentTimeMillis());
		
	}
	
	static class ClatS{
		
	}
	
	class ClaT implements Comparable{
		//内部类可与外部类存在名字相同的变量，访问外部的时可用外部类.this.变量名
		private int next = 10;
		private String last = "";

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + last.hashCode();
			result = 31 * result + next;
			return result;
		}

		@Override
		public boolean equals(Object object) {
			if(this == object){
				return true;
			}
			if(object == null){
				return false;
			}
			if(this.getClass() != object.getClass()){
				return false;
			}
			ClaT clat = (ClaT)object;
			return 
					this.next==clat.next && last.equals(clat.last);
		}

		@Override
		public String toString() {
			return super.toString();
		}

		@Override
		public int compareTo(Object obj) {
			
			ClaT cl = (ClaT)obj;
			return last.compareTo(cl.getLast());
		}

		public int getNext() {
			return next;
		}

		public void setNext(int next) {
			this.next = next;
		}

		public String getLast() {
			return last;
		}

		public void setLast(String last) {
			this.last = last;
		}
		
	}
	
	private void testInst() {
		ClaT cla = new ClaT();
		try {
			System.out.println(cla.next + "" + TestBase.this.next);
		} catch (Exception e) {
		}
		
	}
	
	
	private void change(int k, int g) {
//		int k = 10;int g = 9;
		k = k + g;
		g = k - g;
		k = k - g;
		System.out.println(k + "," + g);
	}





	public TestBase(){
		name = "";
	}
	
//	public TestBase(String user,String pass){
//		this.user = user;
//		this.pass = pass;
//	}
	
	private String user;
	private String pass;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	

}
