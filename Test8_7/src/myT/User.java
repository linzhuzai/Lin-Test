package myT;

public class User {
	public static void main(String[] args) {
//			Crea u = new Crea<User>().getClass().newInstance();
			new Crea<User>().create(new User());
	}
	
}

class Crea<T>{
	public T create(T t){
		
		return t;
	}
}
