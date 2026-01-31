package de.msg.schulung.java8.domainobjects;

import java.util.ArrayList;
import java.util.List;

public class Rechnungsposition {

	private String artikel;
	
	private String artikelnummer;
	
	private double betrag;

	public Rechnungsposition() {
		super();
	}

	public Rechnungsposition(String artikel, String artikelnummer, double betrag) {
		super();
		this.artikel = artikel;
		this.artikelnummer = artikelnummer;
		this.betrag = betrag;
	}

	public String getArtikel() {
		return artikel;
	}

	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}

	public String getArtikelnummer() {
		return artikelnummer;
	}

	public void setArtikelnummer(String artikelnummer) {
		this.artikelnummer = artikelnummer;
	}

	public double getBetrag() {
		return betrag;
	}

	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}
	
	public static List<Rechnungsposition> createRechnungspositionen() {
		
		final List<Rechnungsposition> rechnungspositionen = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			final Rechnungsposition rechnungsposition = new Rechnungsposition("DVD", "4711", 9.90 + i);
			rechnungspositionen.add(rechnungsposition);
		}
		
		return rechnungspositionen;
	}
	
}
