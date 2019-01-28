package spring;

public class Advice implements IAdvice{
	@Override
	public void beforeMethod() {
		System.out.println("方法执行之前");
	}
	@Override
	public void afterMethod() {
		System.out.println("方法执行之后");
	}

}
