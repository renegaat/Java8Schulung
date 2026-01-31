package de.msg.schulung.java8.domainobjects;

public class Person {

	private String nachname;
	
	private String vorname;
	
	private Integer alter;
	
	private Gender geschlecht;
	
	private Integer groesseInCm;

	public static final Integer ERWACHSENEN_ALTER = 18;
	
	public Person() {
		// leer
	}
	
	public Person(String nachname) {
		this.nachname = nachname;
	}
	
	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Integer getAlter() {
		return alter;
	}

	public void setAlter(Integer alter) {
		this.alter = alter;
	}

	public Gender getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(Gender geschlecht) {
		this.geschlecht = geschlecht;
	}

	public Integer getGroesseInCm() {
		return groesseInCm;
	}

	public void setGroesseInCm(Integer groesseInCm) {
		this.groesseInCm = groesseInCm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alter == null) ? 0 : alter.hashCode());
		result = prime * result
				+ ((geschlecht == null) ? 0 : geschlecht.hashCode());
		result = prime * result
				+ ((groesseInCm == null) ? 0 : groesseInCm.hashCode());
		result = prime * result
				+ ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (alter == null) {
			if (other.alter != null)
				return false;
		} else if (!alter.equals(other.alter))
			return false;
		if (geschlecht == null) {
			if (other.geschlecht != null)
				return false;
		} else if (!geschlecht.equals(other.geschlecht))
			return false;
		if (groesseInCm == null) {
			if (other.groesseInCm != null)
				return false;
		} else if (!groesseInCm.equals(other.groesseInCm))
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} else if (!nachname.equals(other.nachname))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [nachname=" + nachname + ", vorname=" + vorname
				+ ", alter=" + alter + ", geschlecht=" + geschlecht
				+ ", groesseInCm=" + groesseInCm + "]";
	}
	
	
}
