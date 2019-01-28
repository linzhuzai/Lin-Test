package com;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.mybatis.example.Person;
import org.mybatis.example.User;


public class TestDon {

	class Annoyance extends Exception {}
	class Sneeze extends Annoyance {}
	 
	@Test
	public void mian() {
		
		try{
			//1
			short sh1 = 1; sh1 += 1;//short s1 = 1; s1 = s1 + 1;会报错
			
			//2
			System.out.println(Math.round(-11.5));//加0.5，再向下也就是小的一方取数
			System.out.println(Math.round(-11.51)); 
			
			//3
			char st = 'a';
			//在Java 5以前，switch(expr)中，expr只能是byte、short、char、int。从Java 5开始，Java中引入了枚举类型，expr也可以是enum类型，从Java 7开始，expr还可以是字符串（String），但是长整型（long）在目前所有的版本中都是不可以的
			switch(2){
				case('a'):
					System.out.println("1");
	//				break;
				case(2):
					System.out.println("2");
			}
			
			//4
			Integer a = new Integer(3);
	        Integer b = 3;                  // 将3自动装箱成Integer类型
	        int c = 3;
	        System.out.println(a == b);     // false 两个引用没有引用同一对象
	        System.out.println(a == c);     // true a自动拆箱成int类型再和c比较
	        
	        //5
	        int fir = 2;int sec = 1;
	        fir = fir + sec;
	        sec = fir - sec;
	        fir = fir - sec;
	        System.out.println(fir + "," + sec);
	       
	        //6
	        float f=3.4f;
	        
	        //7
	        System.out.println(2>1&3>1);//&非短路与 ,&&短路与 
	        
	        //8
	        System.out.println(3 << 1);//3<<1相当于3乘2的1次方，为6
	        
	        //9，用变量，或者用标志，还可以用抛异常
	        ok:
	        for(int i=0;i<3;i++){
	        	for(int j=0;j<3;j++){
	        		break ok;
	        	}
	        }
	        
	        boolean flag = false;
	        for(int i=0;i<3 && !flag;i++){
	        	for(int j=0;j<3;j++){
	        		flag = true;
	//        		throw new RuntimeException();
	        	}
	        }
	        
	        //10
	        String str = new String("first");
	        change(str);//虽然string是引用类型，但是因为final的，所以不能改变
	        System.out.println(str);
	        String s1 = "Programming";
	    	String s2 = new String("Programming");
	    	String s3 = "Program" + "ming";
	    	String s5 = "ming";
	    	final String s6 = "ming";
	    	String s4 = "Program" + s5;//编译器不确定
	    	String s7 = "Program" + s6;//编译器确定
	    	System.out.println(s1 == s2);
	    	System.out.println(s1 == s3);
	    	System.out.println(s1 == s4);
	    	System.out.println(s1 == s7);
	    	System.out.println(s1 == s1.intern());
	    	
	    	//11
	    	final StringBuffer stbf = new StringBuffer();
	//    	stbf = new StringBuffer("aa");//编译不通过
	    	stbf.append("ss");//编译通过，final的只是引用，不是对象本身
	    	final User user = new User();
	    	user.setBirth("");
	//    	user = new User();
	    	
	    	//12
	    	Clon clo = new Clon();
	    	Clon clo2 = (Clon)clo.clone();
	    	clo2.setName("aa");
	    	clo2.setUser(new User());
	    	System.out.println(clo.getName());
	    	System.out.println(clo2.getName());
	    	System.out.println(clo.getUser());
	    	System.out.println(clo2.getUser());
	    	Clon2 clo3 = new Clon2();
	    	Clon2 clo4 = (Clon2)clo3.myclone();
	    	clo3.setName("aa");
	    	clo3.setPerson(new Person());
	    	System.out.println(clo3.getName());
	    	System.out.println(clo4.getName());
	    	System.out.println(clo3.getPerson());
	    	System.out.println(clo4.getPerson());
	    	
	    	//13
	    	int in = 1;
	    	String inStr = "1";
	    	Integer.parseInt(inStr);
	    	String.valueOf(in);
	    	
	    	//14
	    	String code = "code";
	    	new String(code.getBytes("GB2312"),"ISO-8859-1");
	    	
	    	//15
	    	Calendar ca = Calendar.getInstance();
	    	System.out.println(ca.get(Calendar.YEAR));
	    	ca.add(Calendar.DATE, -1);//昨天的当前时刻
	    	System.out.println(ca.getTime());
	    	ca.getTimeInMillis();
	    	ca.set(2018, 03, 20);
	    	System.out.println(ca.get(Calendar.MONTH));
	    	Date date = ca.getTime();//calendar 转 date
	    	ca.setTime(date);//date 转 calendar
	    	System.out.println(ca.getActualMaximum(Calendar.DAY_OF_MONTH));//有疑问
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    	format.format(date);//时间转字符串
	    	System.out.println(format.parse("2005-04-20"));//字符串转时间
	//    	date.getTime() > date.getTime();//时间比较
	    	
	    	//16
	//    	List list = new ArrayList();
	//    	Collections.sort(list, new Comparator<User>() {
	//			@Override
	//			public int compare(User o1, User o2) {
	//				return o1.getName().compareTo(o2.getName());
	//			}
	//		});
	    	
	    	//17
	    	Account account = new Account();
	    	ExecutorService service = Executors.newFixedThreadPool(100);
	    	for(int i=0;i<100;i++){
	    		service.execute(new ThreadAddMoney(account,1));
	    	}
	    	service.shutdown();
	    	while(!service.isTerminated()){}
	    	System.out.println("金额;" + account.getMoney());
	    	
	    	//18
	    	int[] arr = {10,20};
	    	for(int i=0;i<2;i++){
	    		arr[i] = 1;
	    	}
	    	System.out.println(arr[0] + "," + arr[1]);
	    	
	    	//19
	    	Singleton.getInstance();
	    	
	    	//20
	    	countWordNum("E:\\Projects\\test.txt","lin");//算出字符串在文件中出现的次数
	    	File file = new File("E:\\Projects");
	    	for(String filename:file.list()){
	    		System.out.println(filename);
	    	}
	    	System.out.println("");
	    	for(File filename:file.listFiles()){
//	    		if(filename.isFile()){
//	    			System.out.println(filename.getName());
//	    		}
	    		if(filename.isDirectory()){
	    			System.out.println(filename.getName());
	    		}
	    	}
	    	
	    	//21
//	    	int[] intArr = {10,20,30,40,5,2,5,23,5};
//	    	for(int inta:intArr){
//	    		System.out.println(inta);
//	    	}
//	    	for(int i=0;i<intArr.length;i++){
//	    		for(int j=i;j<intArr.length;j++){
//	    			if(intArr[i] > intArr[j]){
//	    				intArr[i] = intArr[i] + intArr[j];
//	    				intArr[j] = intArr[i] - intArr[j];
//	    				intArr[i] = intArr[i] - intArr[j];
//	    			}
//	    		}
//	    	}
//	    	System.out.println();
//	    	for(int inta:intArr){
//	    		System.out.println(inta);
//	    	}
	    	
	    	int[] intArr = {10,20,30,40,5,2,5,23,5};
	    	for(int inta:intArr){
	    		System.out.println(inta);
	    	}
	    	for(int i=0;i<intArr.length;i++){
	    		for(int j=0;j<intArr.length-i-1;j++){
	    			if(intArr[j] > intArr[j+1]){
	    				intArr[j+1] = intArr[j+1] + intArr[j];
	    				intArr[j] = intArr[j+1] - intArr[j];
	    				intArr[j+1] = intArr[j+1] - intArr[j];
	    			}
	    		}
	    	}
	    	System.out.println();
	    	for(int inta:intArr){
	    		System.out.println(inta);
	    	}
	    	
	    	
	    	//HashMap
	    	Map<String,String> map = new ConcurrentHashMap<String,String>();
	    	map.put(null, null);
	    	
		}catch(Exception e){
			
		}
		
		//异常处理
		try {
	        try {
	            throw new Sneeze();
	        } 
	        catch ( Annoyance a ) {
	            System.out.println("Caught Annoyance");
	            throw a;
	        }
	    }
	    catch ( Sneeze s ) {
	        System.out.println("Caught Sneeze");
	        return ;
	    }
	    finally {
	        System.out.println("Hello World!");
	    }
	}
	
	public void countWordNum(String filepath, String string2) throws Exception {
		if(null == string2 || "".equals(string2)){
			throw new Exception();
		}
		InputStream file = null;
		try{
			file = new FileInputStream(new File(filepath));
			StringBuffer strBuff = new StringBuffer();
			byte[] by = new byte[2048];
			int byn;
			if((byn = file.read(by)) != -1){
//				strBuff.append(by);//这样添加的是对象内存地址，傻逼啊
				strBuff.append(new String(by));
			}
			System.out.println(strBuff.toString());
			int num = 0,index;
			while(true){
				if((index = strBuff.indexOf(string2)) > -1){
					num ++;
					strBuff = new StringBuffer(strBuff.substring(index+string2.length()));
				}else{
					break;
				}
			}
			System.out.println("存在次数：" + num);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	private void change(String str) {
		str = "change";
	}
	
	
}

class Singleton{
	private static Singleton singleton = null;
//	private static Singleton singleton = new Singleton();
	private Singleton(){}
	public static Singleton getInstance(){
		if(null == singleton){
			//线程安全
			synchronized(Singleton.class){
				singleton = new Singleton();
			}
		}
		
		return singleton;
	}
	
}

class Account{
	private int money;
	
	public synchronized void addMoney(int add){
		int newMoney = add + money;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		money = newMoney;
	}
	
	public int getMoney(){
		return money;
	}
}
class ThreadAddMoney implements Runnable{
	private Account account;
	private int money;
	public ThreadAddMoney(Account account,int money){
		this.account = account;
		this.money = money;
	}
	@Override
	public void run() {
		account.addMoney(money);
	}
}

class Compare implements Comparable<Compare>{
	private int age;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public int compareTo(Compare o) {
		return this.age - o.age;
	}
}

//序列化方式
class Clon2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4035817075897687284L;
	private Person person;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Clon2 myclone(){
		Clon2 clo = null;
		try{
			ByteArrayOutputStream byteStr = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(byteStr);
			os.writeObject(this);
			
			ByteArrayInputStream bytein = new ByteArrayInputStream(byteStr.toByteArray());
			ObjectInputStream obin = new ObjectInputStream(bytein);
			clo = (Clon2)obin.readObject();
		}catch(Exception e){
		}
		return clo;
	}
}

class Clon implements Cloneable{

	private User user;
	private String name;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Clon clon = null;
		try {
			clon = (Clon)super.clone();//浅克隆，这样子引用类型的属性复制不了，比如User中的值
			clon.user = (User)clon.getUser().clone();//加上这句，就是深度克隆
		} catch (Exception e) {
			
		}
		return clon;
	}
}