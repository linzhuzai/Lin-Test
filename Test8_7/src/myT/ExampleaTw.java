package myT;

import java.io.FileNotFoundException;
import java.io.IOException;

class A{  
	A(){
		System.out.println("父类");
	}
	A(A a){
		
	}
//	A(int i){
//		System.out.println("父类");
//	}
	public String bo;
    public String show(D obj){  
           return ("A and D");  
    }   
    public String show(A obj){  
           return ("A and A");  
    }
    public String show(){
		return ("AA");
    }
    public String noshow(){
		return ("AA");
    }
}   
class B extends A{ 
	B(){
		this(1);
		System.out.println("子类");
	}
	B(B obj){
	}
	B(int ie){
//		super();
		System.out.println("子类" + ie);
	}
    public String show(B obj){  
           return ("B and B");  
    }  
    public String show(A obj) {  
           return ("B and A");  
    }
   public String show(){
		return ("BB");
    }
}
class C extends B{
	public C(B obj){
		super(obj);
	}
	 public String show(){
		 return super.noshow();
	 }
}   
class D extends B{}


class AA {
	public static void main(String[] args) {
		new B();
		new C(new B()).show();
//		A a1 = new A();  
//        A a2 = new B();  
//        B b = new B();  
//        C c = new C();   
//        D d = new D();
////        B d = new D();
//        System.out.println(a2.show(b));// B and A  ��ΪB��д��A��show(A obj)����
//        System.out.println(a2.show(c));// B and A
//        System.out.println(a2.show(a1));//B and A
//        System.out.println(a2.show(d));//A and D
//        System.out.println(a2.show());//BB
//        System.out.println(a1.show(b));///A and A
//        System.out.println(a1.show(c));//A and A
//        System.out.println(a1.show(d));//A and D
//        
//        System.out.println(b.show(b));//B and B
//        System.out.println(b.show(c));//B and B
//        System.out.println(b.show(d));//B and B   A and D
        
	}
	
}


class Book{
	protected int getPrice(){
		return 30;
	}
}
class ComputerBook extends Book{
//	protected int getPrice(int page){}
}






public class ExampleaTw{ 
	String str=new String("good");
	char[]ch={'a','b','c'};
	public static void main(String args[]){ 
		ExampleaTw ex=new ExampleaTw(); 
		ex.change(ex.str,ex.ch); 
		System.out.print(ex.str+"and"); 
		System.out.print(ex.ch);
	}
	public void change(String str,char[] ch){ 
		str="test ok"; 
		ch[0] = 'g';
	}
}
