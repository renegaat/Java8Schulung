package de.msg.schulung.java8.aufloesungen;

import de.msg.schulung.java8.domainobjects.AmazonOrder;
import de.msg.schulung.java8.domainobjects.AmazonOrderFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
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

		// gruppiere alle Aufträge, die teuerer als 30 Euro sind, nach ihrerm Besteller

		Map<String, List<AmazonOrder>> auftraegeNachBesteller =
				orders.stream()
						.filter(order -> order.getPrice() >= 30)
						.collect(Collectors.groupingBy(AmazonOrder::getSender));

		assertGruppiereAmazonOrdersUeber30Ok(auftraegeNachBesteller);
	}

	// ==========================================================================
	// Aufgabenteil b)
	// ==========================================================================

	@Test
	public void sortAmazonOrdersNachAuftragsnummerOrdnePreisZu() {

		final List<AmazonOrder> orders = AmazonOrderFactory.createAmazonOrderList();

		// sortiere nach Auftragsnummer, ordne jeder Auftragsnummer ihren Preis zu

		final List<Pair<String,Integer>> preisZuAuftragsnummer =
			orders.stream()
				.sorted(Comparator.comparing(AmazonOrder::getOrderNumber))
				.map(order -> Pair.of(order.getOrderNumber(), order.getPrice()))
				.collect(Collectors.toList());

		assertSortiereNachAuftragsnummerOk(preisZuAuftragsnummer);
	}

	// ==========================================================================
	// Bonus: Aufgabenteil c)
	// ==========================================================================

	public void gruppiereAuftragsnummernTeurer100NachAnfangsbuchstabenBesteller() {

		final List<AmazonOrder> orders = new ArrayList<>();

		// gruppiere die Auftragsnummern von Aufträgen, die teuerer als 100 Euro sind, nach
		// dem Anfangsbuchstaben des Bestellers; betrachte nur Artikel, die mit D anfangen

		final Map<Character, List<String>> auftragsnummernZuAnfangsbuchstaben = new HashMap<>();
		orders.stream()
			.filter(order -> order.getPrice() > 100)
			.filter(order -> order.getArticleName().startsWith("D"))
			.collect(Collectors.groupingBy(order -> order.getSender().charAt(0)))
			.entrySet()
			.stream()  // Entry Set streamen und jetzt die einzelnen Entries mappen und eintragen
			.forEach((Map.Entry<Character, List<AmazonOrder>> entry) ->
					auftragsnummernZuAnfangsbuchstaben.put(  // hier haben wir einen Seiteneffekt
							entry.getKey(),
							entry.getValue().stream().map(order -> order.getOrderNumber()).collect(Collectors.toList())));

	}

	// ==========================================================================
	// Bonus: Aufgabenteil c)
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

	private void assertGruppiereAmazonOrdersUeber30Ok(Map<String, List<AmazonOrder>> map) {

		Assert.assertTrue(map.keySet().containsAll(Arrays.asList("Müller", "Schmidt")));
		Assert.assertTrue(Arrays.asList("Müller", "Schmidt").containsAll(map.keySet()));
		Assert.assertEquals(2, map.get("Müller").size());
		Assert.assertEquals(1, map.get("Schmidt").size());
		Assert.assertTrue(map.get("Müller").stream().map(AmazonOrder::getArticleName).collect(Collectors.toList()).containsAll(Arrays.asList("DELL Notebook", "Spring")));
		Assert.assertTrue(Arrays.asList("DELL Notebook", "Spring").containsAll(map.get("Müller").stream().map(AmazonOrder::getArticleName).collect(Collectors.toList())));
		Assert.assertEquals("Tapestry", map.get("Schmidt").stream().map(AmazonOrder::getArticleName).collect(Collectors.toList()).get(0));
	}

	/*
        orders.add(new AmazonOrder("DELL Notebook", 500.0, "Müller", "4711"));
        orders.add(new AmazonOrder("Klangwerk", 14.0, "Meier", "101"));
        orders.add(new AmazonOrder("101", 20.0, "Schulze", "42"));
        orders.add(new AmazonOrder("Tapestry", 40.0, "Schmidt", "5001"));
        orders.add(new AmazonOrder("Spring", 45.0, "Müller", "41003"));
*/

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
