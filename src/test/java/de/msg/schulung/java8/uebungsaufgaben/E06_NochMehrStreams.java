package de.msg.schulung.java8.uebungsaufgaben;

import de.msg.schulung.java8.domainobjects.AmazonOrder;
import de.msg.schulung.java8.domainobjects.AmazonOrderFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Das Übungsprojekt enthält eine Klasse {@link AmazonOrder}, die Bestellungen bei amazon repräsentieren soll
 * (zugegebenermaßen nur ein bescheidenes Abbild).
 * a)	Gruppiere aus einer Liste von {@link AmazonOrder}s, alle Elemente, die teurer als 30 Euro sind nach
 * 		ihrem Besteller.
 * b)	Sortiere eine Liste von {@link AmazonOrder}s nach ihrer Auftragsnummer und ordne jeder Auftragsnummer
 * 		ihren Preis zu. Tipp: Nutze die Klasse Pair. Ergebnis ist dann eine Liste von Paaren, von denen
 * 		jedes eine Auftragsnummer mit ihrem zugehörigen Preis enthält.
 * c)	Bonus: Gruppiere die Auftragsnummern aus einer Liste von {@link AmazonOrder}s, die teurer als 100 Euro sind,
 * 		nach dem Anfangsbuchstaben des Bestellers. Betrachte dabei nur Artikel, die mit D anfangen.
 *
 * Tipps zur letzten Aufgabe:
 * <ul>
 * <li> Bilde mittels des groupingBy-Collectors eine Zwischengruppierung der {@link AmazonOrder}s und bilde
 * 		aus dem Entry Set der so entstandenen Map einen neuen Stream. Befülle aus diesem per Seiteneffekt
 * 		in einer forEach-Operation die Ergebnisgruppierung.
 * <li>	Skizziere die Lösung für c) ohne Streams. Was ist leichter?
 * </ul>
 *
 * @author thomannc
 *
 */
public class E06_NochMehrStreams {

	// ==========================================================================
	// Aufgabenteil a)
	// ==========================================================================

	@Test
	public void gruppiereAmazonOrdersUeber30() {

		final List<AmazonOrder> orders = AmazonOrderFactory.createAmazonOrderList();

		// Die folgende Map soll alle Aufträge, die teurer als 30 Euro sind, nach ihrem
		// Besteller (Sender) gruppieren.

		final Map<String, List<AmazonOrder>> auftraegeNachBesteller =
				orders.stream()
						.filter(amazonOrder -> amazonOrder.getPrice() >= 30)
						.collect(Collectors.groupingBy(AmazonOrder::getSender));


		assertGruppiereAmazonOrdersUeber30Ok(auftraegeNachBesteller);
	}

	// ==========================================================================
	// Aufgabenteil b)
	// ==========================================================================

	@Test
	public void sortAmazonOrdersNachAuftragsnummerOrdnePreisZu() {

		final List<AmazonOrder> orders = AmazonOrderFactory.createAmazonOrderList();

		// TODO Ersetze null durch einen Ausdruck, der mittels des Stream-APIs
		// - die Liste orders nach ihrer Auftragsnummer sortiert und
		// - jeder Auftragsnummer ihren Preis zuordnet.
		// Tipp 1: Nutze die Klasse Pair. Ergebnis soll eine Liste von Paaren sein, von denen jedes
		// eine Auftragsnummer (links) mit ihrem zugehörigen Preis (rechts) enthält.
		// Tipp 2: Verwende zum Sortieren entweder einen Lambda-Ausdruck oder
		// die Methode comparing von Comparator und übergebe dieser eine Methodenreferenz
		// auf AmazonOrder.getOrderNumber.
		final List<Pair<String,Integer>> preisZuAuftragsnummer = orders.stream()
						.sorted(Comparator.comparing(AmazonOrder::getOrderNumber))
				.map(amazonOrder -> Pair.of(amazonOrder.getOrderNumber(), amazonOrder.getPrice()))
					.collect(Collectors.toList());

		assertSortiereNachAuftragsnummerOk(preisZuAuftragsnummer);
	}

	// ==========================================================================x
	// Bonus: Aufgabenteil c)
	// ==========================================================================

	public void gruppiereAuftragsnummernTeurer100NachAnfangsbuchstabenBesteller() {

		final List<AmazonOrder> orders = new ArrayList<>();

		// gruppiere die Auftragsnummern von Aufträgen, die teuerer als 100 Euro sind, nach
		// dem Anfangsbuchstaben des Bestellers; betrachte nur Artikel, die mit D anfangen

		final Map<Character, List<String>> auftragsnummernZuAnfangsbuchstaben = new HashMap<>();

		orders.stream()
				.filter(amazonOrder -> amazonOrder.getPrice() >= 100)
				.filter(amazonOrder -> amazonOrder.getArticleName().startsWith("D"))
				.collect(Collectors.groupingBy(amazonOrder -> amazonOrder.getArticleName().charAt(0)))
				.entrySet()
				.stream()  // Entry Set streamen und jetzt die einzelnen Entries mappen und eintragen
				.forEach(characterListEntry -> auftragsnummernZuAnfangsbuchstaben
						.put(characterListEntry.getKey(), characterListEntry.getValue()
								.stream()
								.map(amazonOrder -> amazonOrder.getOrderNumber())
								.collect(Collectors.toList()))
				);
	}

	// ==========================================================================
	// Bonus: Aufgabenteil c) konventionell
	// ==========================================================================

	public void gruppiereAuftragsnummernTeurer100NachAnfangsbuchstabenBestellerKonventionell() {

		final List<AmazonOrder> orders = new ArrayList<>();

		// gruppiere die Auftragsnummern von Aufträgen, die teuerer als 100 Euro sind, nach
		// dem Anfangsbuchstaben des Bestellers; betrachte nur Artikel, die mit D anfangen

		final Map<Character, List<String>> auftragsnummernZuAnfangsbuchstaben = new HashMap<>();

		for (AmazonOrder order : orders) {
			if (!(order.getPrice() > 100) || !order.getArticleName().startsWith("D")) {
				continue;
			}
			final Character anfangsbuchstabe = order.getSender().charAt(0);
			if (!auftragsnummernZuAnfangsbuchstaben.containsKey(anfangsbuchstabe)) {
				auftragsnummernZuAnfangsbuchstaben.put(anfangsbuchstabe, new ArrayList<>());
			}
			auftragsnummernZuAnfangsbuchstaben.get(anfangsbuchstabe).add(order.getOrderNumber());
		}
	}

	// interne Hilfsmethoden

	private void assertGruppiereAmazonOrdersUeber30Ok(Map<String, List<AmazonOrder>> map) {

		Assert.assertTrue(map.keySet().containsAll(Arrays.asList("Müller", "Schmidt")));
		Assert.assertTrue(Arrays.asList("Müller", "Schmidt").containsAll(map.keySet()));
		Assert.assertEquals(2, map.get("Müller").size());
		Assert.assertEquals(1, map.get("Schmidt").size());
		Assert.assertTrue(map.get("Müller").stream().map(AmazonOrder::getArticleName).collect(Collectors.toList()).containsAll(Arrays.asList("DELL Notebook", "Spring")));
		Assert.assertTrue(Arrays.asList("DELL Notebook", "Spring").containsAll(map.get("Müller").stream().map(AmazonOrder::getArticleName).collect(Collectors.toList())));
		Assert.assertEquals("Tapestry", map.get("Schmidt").stream().map(AmazonOrder::getArticleName).collect(Collectors.toList()).get(0));
	}

	private void assertSortiereNachAuftragsnummerOk(List<Pair<String,Integer>> preisZuAuftragsnummer) {

		Assert.assertEquals(5, preisZuAuftragsnummer.size());
		Assert.assertEquals("101", preisZuAuftragsnummer.get(0).getLeft());
		Assert.assertEquals("41003", preisZuAuftragsnummer.get(1).getLeft());
		Assert.assertEquals("42", preisZuAuftragsnummer.get(2).getLeft());
		Assert.assertEquals("4711", preisZuAuftragsnummer.get(3).getLeft());
		Assert.assertEquals("5001", preisZuAuftragsnummer.get(4).getLeft());
		Assert.assertEquals(14, preisZuAuftragsnummer.get(0).getRight().intValue());
		Assert.assertEquals(45, preisZuAuftragsnummer.get(1).getRight().intValue());
		Assert.assertEquals(20, preisZuAuftragsnummer.get(2).getRight().intValue());
		Assert.assertEquals(500, preisZuAuftragsnummer.get(3).getRight().intValue());
		Assert.assertEquals(40, preisZuAuftragsnummer.get(4).getRight().intValue());
	}
}
