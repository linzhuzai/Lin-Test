package org.mybatis.example;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {

	private String id;
	private String classname;
	private List<PropertyDefinition> propertys = new ArrayList<PropertyDefinition>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public List<PropertyDefinition> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<PropertyDefinition> propertys) {
		this.propertys = propertys;
	}
	
	
}
