package myT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
interface IDemo{
//	public static final String name;
	void print();
	public void getInfo();
}

interface TestFx<T>{
	public void sys();
}

interface Person{
	String str = "Student";
	public void sys();
}
abstract class Tes{
//	public abstract void print(){};
}
class Student implements Person,Serializable{
	String str = "Student";
	@Override
	public void sys() {
		System.out.println(str);
		System.out.println("xueshegn");
	}
	public static void syss() {
		System.out.println("ss");
	}
	public void sys(Student i) {
		System.out.println(str);
		System.out.println("xueshegn");
	}
	
}
class Teacher extends Student{
	@Override
	public void sys(){
		System.out.println("laoshi");
	}
//	@Override
	public static void syss() {
		System.out.println("sss");
	}
	@Override
	public void sys(Student i) {
		System.out.println(str);
		System.out.println("xueshegn");
	}
}


public class TestInterface {
	public static void main(String[] args) {
//		List list = new ArrayList();
//		for(int i=0;i<20;i++){
//			list.add(1);
//			list.remove(i-1);
//		}
		Teacher te = new Teacher();
		Student stuu = (Student)te;
		stuu.sys();
//		Person tercher = new Teacher();
//		new TestInterface().factory(tercher);
//		String[] first = {"32","14","14","12","12"};
		int[] first = {12,32,14,12,45,22,56};
		int[] number = {12,32,14,12,45,22,56};
		for(int i=0;i<first.length;i++){
			System.out.print(first[i] + "  ");
		}
		
		//冒泡，定出最大放最后面，就不要动了
//		for(int i=0;i<first.length-1;i++){
//			for(int j=0;j<first.length-1-i;j++){
//				if(first[j] > first[j+1]){
//					first[j] = first[j] + first[j+1];
//					first[j+1] = first[j] - first[j+1];
//					first[j] = first[j] - first[j+1];
//				}
//			}
//		}
		
		//选择，定出最小放最前面，不要动
//		for(int i=0;i<first.length;i++){
//			int tem = i;
//			for(int k=i+1;k<first.length;k++){
//				if(first[k] < first[tem]){
//					tem = k;
//				}
//			}
//			int temp = first[tem];
//			first[tem] = first[i];
//			first[i] = temp;
//		}
		
		//插入，看前面的数，比我大的到后面
//		for(int i=1;i<first.length;i++){
//			int temp = first[i];
//			int j;
//			for(j=i;j>0 && first[j-1] > temp;j--){
//				first[j] = first[j-1];
//			}
//			first[j] = temp;
//		}
		
		System.out.println("");
		for(int i=0;i<first.length;i++){
			System.out.print(first[i] + "  ");
		}
		
	}
	
	public void factory(Person person){
		
	}

}
