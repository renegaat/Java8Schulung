package de.msg.schulung.java8.domainobjects;

import java.time.LocalDate;
import java.util.List;

public class Rechnung {

	private String adressat;
	
	private String nummer;
	
	private LocalDate datum;
	
	private List<Rechnungsposition> positionen;

	public Rechnung() {
		super();
	}

	public Rechnung(String adressat, String nummer, LocalDate datum,
			List<Rechnungsposition> positionen) {
		super();
		this.adressat = adressat;
		this.nummer = nummer;
		this.datum = datum;
		this.positionen = positionen;
	}

	public String getAdressat() {
		return adressat;
	}

	public void setAdressat(String adressat) {
		this.adressat = adressat;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public List<Rechnungsposition> getPositionen() {
		return positionen;
	}

	public void setPositionen(List<Rechnungsposition> positionen) {
		this.positionen = positionen;
	}
	
	
}
