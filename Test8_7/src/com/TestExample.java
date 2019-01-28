package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
public class TestExample {

	//这样是没用的，不是真的多线程
	/*@Test*/
	public void test(){
//		ThreadR threadR = new ThreadR();
//		new Thread(threadR).start();
//		new Thread(threadR).start();
		
//		Num num = new Num();
//		new Thread(new NumThread1(num)).start();
//		new Thread(new NumThread2(num)).start();
		
//		AccountT accountT = new AccountT(100);
//		new DrawThread("取",accountT,10).start();
//		new DespositThread("存",accountT,5).start();
	}
	
	public static void main(String[] args) {
		
		
//		ThreadR threadR = new ThreadR();
//		new Thread(threadR).start();
//		new Thread(threadR).start();
		
		Num num = new Num();
		new Thread(new NumThread1(num)).start();
		new Thread(new NumThread2(num)).start();
		
//		AccountT accountT = new AccountT(100);
//		new DrawThread("取",accountT,10).start();
//		new DespositThread("存",accountT,5).start();
		
//		Flag flag = new Flag();
//		new Thread(new NumThread3(flag)).start();
//		new Thread(new NumThread4(flag)).start();
		
		/**
		 * 生产者消费者当都只有一个的时候，则会出现生产一个消费一个的情况，只有双方都是多个才会根据资源的抢占出现不同的结果
		 */
//		Storage storage = new Storage();
//		new ProductThread(storage).start();
//		new CustomerThread(storage).start();
		
		Clerk clerk = new Clerk();
		Producer p1 = new Producer(clerk);
		Consumer c1 = new Consumer(clerk);
		Thread t1 = new Thread(p1);//一个生产者的线程
//		Thread t3 = new Thread(p1);
		Thread t2 = new Thread(c1);//一个消费者的线程
		
		t1.setName("生产者1");
		t2.setName("消费者1");
//		t3.setName("生产者2");
		
//		t1.start();
//		t2.start();
//		t3.start();
		List<String> list = new ArrayList<>();
		
		//二叉树
//		TreeNode trzro = new TreeNode(6,null,null);
//		TreeNode trfir = new TreeNode(5,null,null);
//		TreeNode trsec = new TreeNode(4,null,trfir);
//		TreeNode trthi = new TreeNode(3,trsec,trzro);
//		TreeNode trfor = new TreeNode(2,null,null);
//		TreeNode trfif = new TreeNode(1,trfor,trthi);
//		showTree(trfif);
//		System.out.println(showTreeNum(trfif));
		
		
	}
	
	public void lock(){
		Lock lock = new ReentrantLock();
		lock.lock();
		//..
		lock.unlock();
	}
	
	private static void showTree(TreeNode node) {
		if(null != node){
			//先序
//			System.out.println(node.getVal());
//			showTree(node.getLeft());
//			showTree(node.getRight());
			
			//中序
//			showTree(node.getLeft());
//			System.out.println(node.getVal());
//			showTree(node.getRight());
			
			//后序
//			showTree(node.getLeft());
//			showTree(node.getRight());
//			System.out.println(node.getVal());
		}
		
	}
	
	private static int showTreeNum(TreeNode node){
		//深度
		if(null == node){
			return 0;
		}else{
			int left = showTreeNum(node.getLeft());
			int right = showTreeNum(node.getRight());
			int result = left>right?left+1:right+1;
			return result;
		}
	}
}

class TreeNode{
	private int val;
	private TreeNode left;
	private TreeNode right;
	public TreeNode(int val, TreeNode left,TreeNode right){
		this.val = val;
		this.left = left;
		this.right = right;
	}
	public int getVal(){
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public TreeNode getLeft() {
		return left;
	}
	public void setLeft(TreeNode left) {
		this.left = left;
	}
	public TreeNode getRight() {
		return right;
	}
	public void setRight(TreeNode right) {
		this.right = right;
	}
	
}

class ProductThread extends Thread{
	private Storage storage;
	public ProductThread(Storage storage){
		this.storage = storage;
	}
	
	@Override
	public void run(){
		storage.product();
	}
}
class CustomerThread extends Thread{
	private Storage storage;
	public CustomerThread(Storage storage){
		this.storage = storage;
	}
	
	@Override
	public void run(){
		storage.customer();
	}
}

class Storage{
	private final static int MAX_NUM = 10;
	private final static int MIN_NUM = 5;
	private int product;
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public synchronized void product(){
		while(true){
			if(product > MAX_NUM){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			product ++;
			System.out.println("生产了多少商品：" + product);
			notifyAll();
		}
	}
	public synchronized void customer(){
		while(true){
			if(product < MIN_NUM){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("消费了低几个商品：" + product);
			product --;
			notifyAll();
		}
	}
	
}

class AccountT{
	public int in = 0;
	private double balance;
	private boolean flag = false;
	public AccountT(double balance){
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public synchronized void draw(double getAccount){
		while(true){
		System.out.println(Thread.currentThread().getName() + " " + balance + "执行" + flag);
		if(balance < 0){
			break;
		}
		if(!flag){
			try {
				in++;
//				Thread.currentThread().setName("取钱者修改" + in);
				System.out.println("取钱者修改" + in);
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("空手没有" + flag);
		}else{
			balance = balance - getAccount;
			System.out.println(Thread.currentThread().getName() + "取钱,当前余额：" + balance);
			flag = false;
			notify();//唤醒后其他线程并不会马上拿到资源，而是等到这个等待才会拿到
		}
		}
	}
	public synchronized void desposit(double setAccount){
		while(true){
		System.out.println(Thread.currentThread().getName() + " " + balance + "执行" + flag);
		if(balance < 0){
			break;
		}
		if(flag){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			balance = balance + setAccount;
			System.out.println("存钱,当前余额：" + balance);
			flag = true;
			notify();
		}
		}
	}
}

class DrawThread extends Thread{
	private AccountT accountT;
	private double get;
	public DrawThread(String name,AccountT accountT,double get){
		this.accountT = accountT;
		this.get = get;
	}
	@Override
	public void run(){
//		for(int i=0;i<20;i++){
			accountT.draw(get);
//		}
	}
}

class DespositThread extends Thread{
	private AccountT accountT;
	private double add;
	public DespositThread(String name,AccountT accountT,double add){
		this.accountT = accountT;
		this.add = add;
	}
	@Override
	public void run(){
//		for(int i=0;i<20;i++){
			accountT.desposit(add);
//		}
	}
}
class Flag{
	private boolean flag;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
class NumThread4 implements Runnable{
	private Flag flag;
	
	public NumThread4(Flag flag){
		this.flag = flag;
	}
	
	@Override
	public void run() {
		synchronized (flag) {
			for(int i=2;i<100;i=i+2){
				System.out.println(i);
				flag.notify();
				try {
					flag.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}

class NumThread3 implements Runnable{
	
	private Flag flag;
	
	public NumThread3(Flag flag){
		this.flag = flag;
	}
	@Override
	public void run() {
		synchronized (flag) {
			for(int i=1;i<100;i=i+2){
				System.out.println(i);
				flag.notify();
				try {
					flag.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
	
class Num{
	int num = 1;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}

class NumThread1 implements Runnable{
	private Num num;
	public NumThread1(Num num){
		this.num = num;
	}
	@Override
	public void run() {
		while(true){
			if(num.getNum() >= 100){
				break;
			}
			synchronized (num) {
				if(num.getNum() % 2 == 0){
					try {
						num.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				System.out.println(num.getNum());
				num.setNum(num.getNum()+1);
				num.notify();
			}
		}
	}
}
class NumThread2 implements Runnable{
	private Num num;
	public NumThread2(Num num){
		this.num = num;
	}
	@Override
	public void run() {
		while(true){
			if(num.getNum() >= 100){
				break;
			}
			synchronized (num) {
				if(num.getNum() % 2 != 0){
					try {
						num.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				System.out.println(num.getNum());
				num.setNum(num.getNum()+1);
				num.notify();
			}
		}
	}
}

class ThreadR implements Runnable{
	int[] arr = {10,20,30,12,32,52,25};
	boolean[] fl = new boolean[arr.length];
	int sum = arr.length;
	@Override
	public void run() {
		while(true){
			synchronized(this){
				if(sum > 0){
					int num = (int)(Math.random() * arr.length);
//					System.out.println(fl[num]);
					if(!fl[num]){
						fl[num] = true;
						System.out.println(arr[num]);
						sum --;
					}
				}else{
					break;
				}
			}
		}
		
	}

}











class Clerk{//店员
	int product = 10;
	
	public synchronized void addProduct(){//生产产品
		if(product >= 20){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			product++;
			System.out.println(Thread.currentThread().getName() + ":生产了第" + product + "个产品");
			notifyAll();							//唤醒
		}
	}
	public synchronized void consumeProduct(){//消费产品
		if(product <= 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println(Thread.currentThread().getName() + ":消费了第" + product + "个产品");
			product--;
			notifyAll();
		}
	}
}

class Producer implements Runnable{//生产者
	Clerk clerk;
	
	public Producer(Clerk clerk){
		this.clerk = clerk;
	}
	public void run(){
		System.out.println("生产者开始生产产品");
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clerk.addProduct();
			
		}
	}
}
class Consumer implements Runnable{//消费者
	Clerk clerk;
	public Consumer(Clerk clerk){
		this.clerk = clerk;
	}
	public void run(){
		System.out.println("消费者消费产品");
		while(true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clerk.consumeProduct();
		}
	}
}
