package de.msg.schulung.java8.domainobjects;

import java.util.Optional;

public class OptionalUser {

	private String name;
	
	private Optional<Integer> age = Optional.empty();
	
	private Gender gender;

	public OptionalUser() {
		
	}
	
	public OptionalUser(String name, Integer age, Gender gender) {
		this.name = name;
		this.age = Optional.of(age);
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Optional<Integer> getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = Optional.of(age);
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
