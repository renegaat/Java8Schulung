package de.msg.schulung.java8.uebungsaufgaben;

import de.msg.schulung.java8.domainobjects.Rechnung;
import de.msg.schulung.java8.domainobjects.RechnungFactory;
import de.msg.schulung.java8.domainobjects.Rechnungsposition;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Das Übungsprojekt enthält zwei Klassen {@link Rechnung} und {@link Rechnungsposition}, die in einer
 * 1:n-Beziehung zueinander stehen.
 *
 * Ermittle die Summe aller {@link Rechnungsposition}en über eine Liste von {@link Rechnung}en
 * a)	ohne Nutzung von Java-8-Sprachmitteln
 * b)	mit Lambdas und Streams.
 *
 * Berücksichtige nur Rechnungen, die mehr als eine Rechnungsposition haben.
 * Tipp: Du benötigst hier die Stream-Operation flatMap(). Für die Summierung gibt es zwei Möglichkeiten.
 * Probiere aus, wie man die reduce()-Operation einsetzen kann, um das gleiche Ergebnis wie bei der Operation sum()
 * zu erhalten.
 *
 * Am Ende der Klasse findest Du auch eine Version von Übung a) mit mehr Hilfestellung.
 *
 * @author thomannc
 *
 */
public class E07_FlatMapUndReduce {

	// ======================================================================================
	// Aufgabenteil a)
	// ======================================================================================

	@Test
	public void ermittleSummeVonRechnungspositionen() {

		final List<Rechnung> rechnungsliste = RechnungFactory.createRechnungen();

		// TODO ersetze 0.0 durch einen Ausdruck, der mittels des Stream-APIs die Summe aller Rechnungspositionen
		// aus der o.g. Rechnungsliste berechnet, wobei der Adressat der Rechnung mit "M" anfangen soll und die
		// Rechnung mehr als eine Position haben muss.
		//
		// Erstelle dazu aus rechnungsliste einen Stream und
		// - filtere alle Rechnungen heraus, deren Adressat mit "M" anfängt
		// - und filtere dann alle Rechnungen heraus, die mehr als 1 Position haben
		// - und mappe die Rechnung auf die Liste ihrer Positionen.
		// - Mappe anschließend diese Liste auf einen Stream von Rechnungspositionen (Tipp: flatMap)
		// - und mappe dann die Rechnungspositionen auf ihren Betrag (Tipp: map).
		// - Bilde abschließend die Summe über den nur noch aus Beträgen bestehenden Stream.
		//   Tipp: Entweder mittels sum oder reduce.
		final double rechnungssummeStreamed = rechnungsliste
				.stream()
				.filter(rechnung -> rechnung.getAdressat().startsWith("M"))
				.filter(rechnung -> rechnungsliste.size() > 1)
				.map(rechnung -> rechnung.getPositionen())
				.flatMap(rechnungspositions -> rechnungspositions.stream())
				.map(rechnungsposition -> rechnungsposition.getBetrag())
				.reduce(0.0, Double::sum);

		Assert.assertTrue(RechnungFactory.getMatchingSumme() == rechnungssummeStreamed);
	}

	// ======================================================================================
	// Aufgabenteil b)
	// ======================================================================================

	@Test
	public void ermittleSummeVonRechnungspositionenKonventionell() {

		final List<Rechnung> rechnungsliste = RechnungFactory.createRechnungen();

		double rechnungssumme = 0.0;

		final List<Rechnungsposition> positionsliste = new ArrayList<>();
		for (Rechnung r : rechnungsliste) {
			if (r.getAdressat().startsWith("M")) {
				if (r.getPositionen().size() > 1) {
					positionsliste.addAll(r.getPositionen());
				}
			}
		}

		for (Rechnungsposition position : positionsliste) {
			rechnungssumme += position.getBetrag();
		}

		Assert.assertTrue(RechnungFactory.getMatchingSumme() == rechnungssumme);
	}

	// ======================================================================================
	// Aufgabenteil a) mit Hilfestellung
	// ======================================================================================

	@Test
	public void ermittleSummeVonRechnungspositionenStreamedMitHilfe() {

		final List<Rechnung> rechnungsliste = RechnungFactory.createRechnungen();

		// Ermittle die Rechnungssumme der o.g. Rechnungen mit Hilfe des Stream-APIs und durch Ausfüllen
		// des u.g. Gerüsts.
		//
		// TODO Ersetze 0.0 nach Ausfüllen der u.g. TODOs durch den Stream-API-Ausdruck ab "rechnungsliste".
		final double rechnungssummeStreamed = 0.0;
		rechnungsliste.stream()
				.filter(null
						// TODO ersetze null durch einen Lambda-Ausdruck, der prüft, ob der Adressat einer Rechnung mit "M" anfängt
				)
				.filter(null
						// TODO ersetze null durch einen Lambda-Ausdruck, der prüft, ob eine Rechnung mehr als 1 Positon hat
				)
				.map(null
						// TODO ersetze null auch einen Lambda-Ausdruck oder eine Methodenreferenz zur Ermittlung der Positionen
				)
				.flatMap(null
						// TODO ersetze null durch einen Lambda-Ausdruck, der eine Positionsliste auf einen Stream abbildet
				)
				.map(null
						// TODO ersetze null durch einen Lambda-Ausdruck oder eine Methodenreferenz zur Ermittlung des Betrags
				)
				.reduce(0.0, null
						// TODO ersetze null durch einen Lambda-Ausdruck zur Aufsummierung der Beträge; nutze alternativ sum (leichter)
				);

		Assert.assertTrue(RechnungFactory.getMatchingSumme() == rechnungssummeStreamed);
	}
}
