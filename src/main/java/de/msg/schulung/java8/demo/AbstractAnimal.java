package de.msg.schulung.java8.demo;

public abstract class AbstractAnimal {

	private String farbe;

	private Integer groesse;

	private Integer alter;

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

	/**
	 * Liefert den typischen Laut des Tiers (z.B. Wau für Hunde)
	 *
	 * @return der Laut
	 */
	abstract public String gibLaut();
}
