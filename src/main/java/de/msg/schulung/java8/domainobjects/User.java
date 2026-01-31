package de.msg.schulung.java8.domainobjects;

public class User {

	private String name;
	
	private Integer age = 0;
	
	private Gender gender;

	public User() {
		
	}
	
	public User(String name) {
		this();
		
		this.name = name;
	}
	
	public User(String name, Integer age, Gender gender) {
		this(name);
		
		this.age = age;
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return "[" + name + "/" + age + "/" + gender + "]";
	}


}
