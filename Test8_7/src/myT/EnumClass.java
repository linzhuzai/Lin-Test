package myT;

public enum EnumClass {
	
	RED("红色", "红色");
	
	private String name ;
	private String pass;

	private EnumClass(String name,String pass){
		this.name = name;
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
