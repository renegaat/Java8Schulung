package de.msg.schulung.java8.demo;

import java.util.function.Function;

public class Animal extends AbstractAnimal {

	private String farbe;
	
	private Integer groesse;
	
	private Integer alter;

	private Function<Animal, String> gibLautFunction;

	public Animal(Function<Animal, String> gibLaut) {
		this.gibLautFunction = gibLaut;
	}

	public String getFarbe() {
		return farbe;
	}

	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}

	public Integer getGroesse() {
		return groesse;
	}

	public void setGroesse(Integer groesse) {
		this.groesse = groesse;
	}

	public Integer getAlter() {
		return alter;
	}

	public void setAlter(Integer alter) {
		this.alter = alter;
	}
	
	@Override
	public String gibLaut() {
		return gibLautFunction.apply(this);
	}
}
