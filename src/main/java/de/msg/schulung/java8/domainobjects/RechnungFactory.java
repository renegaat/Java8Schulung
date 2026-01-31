package de.msg.schulung.java8.domainobjects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RechnungFactory {

	public static final int MATCHING_RECHNUNGEN = 3;

	public static List<Rechnung> createRechnungen() {

		final List<Rechnung> rechnungsliste = new ArrayList<>();

		final Rechnung[] rechnungen = new Rechnung[] {
				new Rechnung("Meier", "0011", LocalDate.now(), new ArrayList<>()),  // 0
				new Rechnung("Müller", "0012", LocalDate.now(), new ArrayList<>()), // 1
				new Rechnung("Hahn", "0013", LocalDate.now(), new ArrayList<>()), // 2
				new Rechnung("Bauer", "0014", LocalDate.now(), new ArrayList<>()), // 3
				new Rechnung("Hensen", "0015", LocalDate.now(), new ArrayList<>()), // 4
				new Rechnung("Mommsen", "0016", LocalDate.now(), new ArrayList<>()), // 5
				new Rechnung("Müller-Wohlfarth", "0017", LocalDate.now(), new ArrayList<>()),	// 6
				new Rechnung("Alaba", "0018", LocalDate.now(), new ArrayList<>()), // 7
				new Rechnung("Robben", "0019", LocalDate.now(), new ArrayList<>()), // 8
				new Rechnung("Hunt", "0020", LocalDate.now(), new ArrayList<>()), // 9
				new Rechnung("Meyer", "0021", LocalDate.now(), new ArrayList<>()) // 10
		};

		rechnungen[0].getPositionen().addAll(Arrays.asList(		// ja
				new Rechnungsposition("Schuhe", "0001", 100.00),
				new Rechnungsposition("Socken", "0002", 24.99),
				new Rechnungsposition("Mütze", "0003", 15.89)
		));
		rechnungen[1].getPositionen().addAll(Arrays.asList(  // nein
				new Rechnungsposition("CD", "4711", 29.99)
		));
		rechnungen[2].getPositionen().addAll(Arrays.asList(  // nein
				new Rechnungsposition("Butter", "1000", 3.49),
				new Rechnungsposition("Käse", "1001", 1.99),
				new Rechnungsposition("Wurst", "1002", 2.59),
				new Rechnungsposition("Brot", "1003", 1.55)
		));
		rechnungen[3].getPositionen().addAll(Arrays.asList(		// nein
				new Rechnungsposition("CD-Player", "1111", 299.0),
				new Rechnungsposition("TV", "2222", 999.99),
				new Rechnungsposition("DVD-Player", "3333", 300.99)
		));
		rechnungen[4].getPositionen().addAll(Arrays.asList(		// nein
				new Rechnungsposition("Levis Jeans", "9000", 69.99)
		));
		rechnungen[5].getPositionen().addAll(Arrays.asList(		// ja
				new Rechnungsposition("CD", "5551", 9.99),
				new Rechnungsposition("LP", "5552", 25.99)
		));
		rechnungen[6].getPositionen().addAll(Arrays.asList(  // ja
				new Rechnungsposition("Sesambrötchen", "1000", 0.69),
				new Rechnungsposition("Berliner", "1001", 1.20),
				new Rechnungsposition("Käsekuchen", "1002", 2.66),
				new Rechnungsposition("Donauwelle", "1003", 1.09)
		));
		rechnungen[7].getPositionen().addAll(Arrays.asList(		// nein
				new Rechnungsposition("Krimi", "1001", 12.99),
				new Rechnungsposition("Sachbuch", "1002", 25.99),
				new Rechnungsposition("Atlas", "1003", 34.99)
		));
		rechnungen[8].getPositionen().addAll(Arrays.asList(  // nein
				new Rechnungsposition("Wohnzimmerlampe", "4711", 59.99)
		));
		rechnungen[9].getPositionen().addAll(Arrays.asList(		// nein
				new Rechnungsposition("CD", "5551", 9.99),
				new Rechnungsposition("LP", "5552", 25.99)
		));
		rechnungen[10].getPositionen().addAll(Arrays.asList(  // nein
				new Rechnungsposition("Bürotisch", "8888", 120.00)
		));

		rechnungsliste.addAll(Arrays.asList(rechnungen));

		return rechnungsliste;
	}

	public static final double getMatchingSumme() {

		return
				100.00 + 24.99 + 15.89 +
				9.99 + 25.99 +
				0.69 + 1.20 + 2.66 + 1.09;
	}
}
