package de.msg.schulung.java8.aufloesungen;

import de.msg.schulung.java8.domainobjects.Person;
import de.msg.schulung.java8.domainobjects.PersonFactory;
import de.msg.schulung.java8.domainobjects.Rechnungsposition;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Ausprobieren der verschiedenen Stream-Operationen.
 * <p/>
 * siehe Übungsaufgaben
 *
 * @author thomannc
 *
 */
public class E04_MehrStreams {

	private static final String[] WOCHENTAGE = new String[] {
			"Montag",
			"Dienstag",
			"Mittwoch",
			"Donnerstag",
			"Freitag",
			"Samstag",
			"Sonntag"
	};

	@Test
	public void testStreamOf() {

		final Stream<String> streamAusWoertern = Stream.of("Dies", "ist", "ein", "Stream");

		assertStreamOfOk(streamAusWoertern);
	}

	@Test
	public void testArraysStream() {

		final String[] stringArray = new String[] { "Dies", "ist", "ein", "Stream" };

		final Stream<String> streamAusArray = Arrays.stream(stringArray);

		assertArraysStreamOk(stringArray, streamAusArray);
	}

	@Test
	public void testDoubleStreamSum() {

		final List<Rechnungsposition> rechnungspositionsliste = Rechnungsposition.createRechnungspositionen();

		final double rechnungssumme = rechnungspositionsliste.stream()
				.mapToDouble(Rechnungsposition::getBetrag)
				.sum();

		Assert.assertTrue(144 == rechnungssumme);
	}

	@Test
	public void testFilter() {

		final Stream<String> wochentageStream = Arrays.stream(WOCHENTAGE);

		final Stream<String> wochentageMitRStream = wochentageStream.filter(wochentag -> wochentag.toLowerCase().contains("r"));

		assertFilterOk(wochentageMitRStream);
	}

	@Test
	public void testDistinct() {

		final Stream<Integer> unterschiedlicheWochentagLaengenStream = Arrays.stream(WOCHENTAGE)
				.map(String::length)
				.distinct();

		assertDistinctGefundeneLaengenOk(unterschiedlicheWochentagLaengenStream);

		final Stream<Character> unterschiedlicheAnfangsbuchstabenStream = Arrays.stream(WOCHENTAGE)
				.map(wochentag -> wochentag.charAt(0))
				.distinct();

		assertDistinctGefundeneAnfangsbuchstabenOk(unterschiedlicheAnfangsbuchstabenStream);
	}

	@Test
	public void testIterateLimit() {

		final Stream<Integer> erste5VielfacheVon10Stream = Stream.iterate(10, x -> 10 + x).limit(5);

		assertIterateLimitOk(erste5VielfacheVon10Stream);
	}

	@Test
	public void testMapAndSort() {
		final List<Person> personListe = PersonFactory.createPersonCollection();

		final List<Integer> altersliste = new ArrayList<>();

		final Stream<String> nachnamenStream = personListe.stream()
				.peek(person -> altersliste.add(person.getAlter()))
				.map(Person::getNachname);

		assertMapAndSortNachnamenlisteOk(nachnamenStream);
		assertMapAndSortAlterslisteOk(altersliste);

		final Stream<Integer> altersangabenAufsteigendSortiertStream = personListe.stream()
				.map(Person::getAlter)
				.sorted();

		assertMapAndSortAltersangabenlisteAufsteigendSortiertOk(altersangabenAufsteigendSortiertStream);

		final Stream<Integer> altersangabenAbsteigenSortiertStream = personListe.stream()
				.map(Person::getAlter)
				.sorted((alter1, alter2) -> alter2.compareTo(alter1));

		assertMapAndSortAltersangabenAbsteigendSortiertOk(altersangabenAbsteigenSortiertStream);
	}

	@Test
	public void testAnyMatch() {

		final Stream<Integer> integerStream = Stream.iterate(10, x -> 10 + x).limit(5);

		assertAnyMatchOk(integerStream);
	}

	// ======================================================================
	// interne Hilfsmethoden
	// ======================================================================

	private void assertStreamOfOk(Stream<String> streamAusWoertern) {
		final List<String> woerterliste = streamAusWoertern.collect(Collectors.toList());
		Assert.assertNotNull(woerterliste);
		Assert.assertEquals(4,  woerterliste.size());
		Assert.assertEquals("Dies", woerterliste.get(0));
		Assert.assertEquals("ist", woerterliste.get(1));
		Assert.assertEquals("ein", woerterliste.get(2));
		Assert.assertEquals("Stream", woerterliste.get(3));
	}

	private void assertArraysStreamOk(String[] stringArray, Stream<String> streamAusArray) {
		final List<String> arrayAlsListe = streamAusArray.collect(Collectors.toList());
		Assert.assertNotNull(arrayAlsListe);
		Assert.assertEquals(4,  arrayAlsListe.size());
		Stream.iterate(0, i -> i + 1).limit(3).forEach( index -> {
			Assert.assertEquals(stringArray[index], arrayAlsListe.get(index));
		});
	}

	private void assertFilterOk(Stream<String> wochentageMitRStream) {
		final List<String> wochentageMitR = wochentageMitRStream.collect(Collectors.toList());
		Assert.assertNotNull(wochentageMitR);
		Assert.assertEquals(2,  wochentageMitR.size());
		Assert.assertTrue(wochentageMitR.contains("Donnerstag"));
		Assert.assertTrue(wochentageMitR.contains("Freitag"));
	}

	private void assertDistinctGefundeneLaengenOk(Stream<Integer> unterschiedlicheWochentagLaengenStream) {
		final List<Integer> gefundeneLaengen = unterschiedlicheWochentagLaengenStream.collect(Collectors.toList());
		Assert.assertNotNull(gefundeneLaengen);
		Assert.assertEquals(4, gefundeneLaengen.size());
		Assert.assertTrue(gefundeneLaengen.containsAll(
				Arrays.asList(new Integer[] { 6, 8, 10, 7 })));
	}

	private void assertDistinctGefundeneAnfangsbuchstabenOk(Stream<Character> unterschiedlicheAnfangsbuchstabenStream) {
		final List<Character> gefundeneAnfangsbuchstaben = unterschiedlicheAnfangsbuchstabenStream.collect(Collectors.toList());
		Assert.assertNotNull(gefundeneAnfangsbuchstaben);
		Assert.assertEquals(4, gefundeneAnfangsbuchstaben.size());
		Assert.assertTrue(gefundeneAnfangsbuchstaben.containsAll(
				Arrays.asList(new Character[] { 'M', 'D', 'F', 'S' })));
	}

	private void assertIterateLimitOk(Stream<Integer> erste5VielfacheVon10Stream) {
		final List<Integer> erste5VielfacheVon10 = erste5VielfacheVon10Stream.collect(Collectors.toList());
		Assert.assertNotNull(erste5VielfacheVon10);
		Assert.assertEquals(5, erste5VielfacheVon10.size());
		Assert.assertTrue(erste5VielfacheVon10.containsAll(
				Arrays.asList(new Integer[]{10, 20, 30, 40, 50})));
	}

	private void assertMapAndSortNachnamenlisteOk(Stream<String> nachnamenStream) {
		final List<String> nachnamenListe = nachnamenStream.collect(Collectors.toList());

		Assert.assertNotNull(nachnamenListe);
		Assert.assertEquals(10, nachnamenListe.size());
		Assert.assertTrue(nachnamenListe.containsAll(
				Arrays.asList(new String[] {
						"Müller",  				// 0
						"Müller",				// 1
						"Peter",				// 2
						"Meier",				// 3
						"Thurn-und-Taxis",		// 4
						"Dampf",				// 5
						"Peter",				// 6
						"Meier",				// 7
						"Müller",				// 8
						"Bruni"					// 9
				})));
	}

	private void assertMapAndSortAlterslisteOk(List<Integer> altersliste) {
		Assert.assertEquals(10, altersliste.size());
		Assert.assertTrue(altersliste.containsAll(
				Arrays.asList(new Integer[] {
						16, 17, 18, 19,
						16, 17, 18, 19,
						16, 17
				})));
	}

	private void assertMapAndSortAltersangabenlisteAufsteigendSortiertOk(Stream<Integer> altersangabenAufsteigenSortiertStream) {
		final List<Integer> altersangabenAufsteigendSortiert =
				altersangabenAufsteigenSortiertStream.collect(Collectors.toList());
		assertIntegerListeIstSortiert(altersangabenAufsteigendSortiert, true);
	}

	private void assertMapAndSortAltersangabenAbsteigendSortiertOk(Stream<Integer> altersangabenAbsteigenSortiertStream) {
		final List<Integer> altersangabenAbsteigendSortiert =
				altersangabenAbsteigenSortiertStream.collect(Collectors.toList());
		assertIntegerListeIstSortiert(altersangabenAbsteigendSortiert, false);
	}

	private void assertAnyMatchOk(Stream<Integer> integerStream) {
		final boolean vielfachesVon3ExistiertInErste5VielfacheVon10Stream =
				integerStream.anyMatch(x -> x % 3 == 0);

		Assert.assertTrue(vielfachesVon3ExistiertInErste5VielfacheVon10Stream);
	}

	private static void assertIntegerListeIstSortiert(
			List<Integer> integerListe, boolean aufsteigend) {

		final boolean absteigend = !aufsteigend;

		boolean istSortiert = true;
		final int listSize = integerListe.size();

		if (listSize > 1) {
			for (int i = 0; i < listSize -1; i++) {
				final Integer wert = integerListe.get(i);
				final Integer naechsterWert = integerListe.get(i + 1);

				Assert.assertNotNull(wert);
				Assert.assertNotNull(naechsterWert);

				if (aufsteigend && naechsterWert < wert ||
						absteigend && naechsterWert > wert) {
					istSortiert = false;
					break;
				}
			}
		}

		Assert.assertTrue(istSortiert);
	}

}
