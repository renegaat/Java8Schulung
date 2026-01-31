package de.msg.schulung.java8.aufloesungen;

import de.msg.schulung.java8.domainobjects.Rechnung;
import de.msg.schulung.java8.domainobjects.RechnungFactory;
import de.msg.schulung.java8.domainobjects.Rechnungsposition;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Das Übungsprojekt enthält zwei Klassen {@link Rechnung} und {@link Rechnungsposition}, die in einer
 * 1:n-Beziehung zueinander stehen.
 *
 * Ermittle die Summe aller {@link Rechnungsposition}en über eine Liste von {@link Rechnung}en
 * a)	ohne Nutzung von Java-8-Sprachmitteln
 * b)	mit Lambdas und Streams.
 *
 * Berücksichtige nur Rechnungen, die mehr als eine Rechnungsposition haben.
 * Tipp 1: Du benötigst hier die Stream-Operation flatMap(). Für die Summierung gibt es zwei Möglichkeiten.
 * Probiere aus, wie man die reduce()-Operation einsetzen kann, um das gleiche Ergebnis wie bei der Operation sum()
 * zu erhalten.
 * Bonus: zähle in beiden Varianten, wie viele Rechnungen in die Berechnung mit einfließen.
 * Tipp 2: Hier benötigst Du für die Stream-Version noch eine Counter-Klasse sowie die Stream-Methode peek(),
 * die mit einem Seiteneffekt und unter Verwendung der Counter-Klasse die zählung erledigt.
 *
 * @author thomannc
 *
 */
public class  E07_FlatMapUndReduce {

	// ======================================================================================
	// Aufgabenteil a)
	// ======================================================================================

	@Test
	public void ermittleSummeVonRechnungspositionen() {

		final List<Rechnung> rechnungsliste = RechnungFactory.createRechnungen();

		final double rechnungssummeStreamed = rechnungsliste.stream()
			.filter(r -> r.getAdressat().startsWith("M"))
			.filter(r -> r.getPositionen().size() > 1)
			.map(Rechnung::getPositionen)
			.flatMap(positionsliste -> positionsliste.stream())
			.map(Rechnungsposition::getBetrag)
			.reduce(0.0, (summe, betrag) -> summe + betrag);  // alternativ und leichter: sum()

		Assert.assertTrue(RechnungFactory.getMatchingSumme() == rechnungssummeStreamed);
	}

	// ======================================================================================
	// Aufgabenteil b)
	// ======================================================================================

	@Test
	public void ermittleSummeVonRechnungspositionenKonventionell() {

		final List<Rechnung> rechnungsliste = RechnungFactory.createRechnungen();

		double rechnungssumme = 0.0;
		int countMatches = 0;  // für Add-On

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

		final double rechnungssummeStreamed = rechnungsliste.stream()
				.filter(r -> r.getAdressat().startsWith("M"))
				.filter(r -> r.getPositionen().size() > 1)
				.map(Rechnung::getPositionen)
				.flatMap(positionsliste -> positionsliste.stream())
				.map(Rechnungsposition::getBetrag)
				.reduce(0.0, (summe, betrag) -> summe + betrag);

		Assert.assertTrue(RechnungFactory.getMatchingSumme() == rechnungssummeStreamed);
	}

}
