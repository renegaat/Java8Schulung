package de.msg.schulung.java8.domainobjects;

public class Apple {

	public static final String RED = "red";
	
	public static final String GREEN = "green";
	
	public static final String YELLOW = "yellow";
	
	public static final Integer HEAVY = 150;
	
	private String color;
	
	private Integer weight;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return "[color: " + this.getColor() + ", weight: " + this.getWeight() + "]";
	}
}
