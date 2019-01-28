package myT;

public class Person2{
	public String name="Person";
	int age=0;
}
class Child extends Person2{
	public String grade;
	public static void main(String[] args){
		Person2 p = new Child();
		Person2 p1 = new Person2();
		Child p2 = new Child();
//		System.out.println(p.name);
		System.out.println(p1.name);
	}
}
