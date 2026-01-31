package de.msg.schulung.java8.aufloesungen;

import de.msg.schulung.java8.domainobjects.Person;
import de.msg.schulung.java8.domainobjects.PersonFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Benutze die Klasse {@link Person}, um das Stream-API auszuprobieren, z.B.:
 * <ul>
 * <li>	Bilde aus einer Liste von {@link Person}s die zugehörige (positionsgetreue) Liste der zugehörigen Namen.
 * <li>	Filtere alle {@link Person}s, die älter als 18 Jahre sind, sortiert nach ihrem Namen.
 * <li>	Prüfe, ob in einer Liste von {@link Person}s jemand älter als 60 ist.
 * </ul>
 * Überlege Dir dabei jeweils auch, wie eine Lösung ohne Streams aussehen würde.
 *
 * @author thomannc
 *
 */
public class E03_StreamGrundlagen {

	@Test
	public void bildePersonNameList() {

		final List<Person> personListe = PersonFactory.createPersonCollection();

		final List<String> personenNamen = personListe.stream()
				.map(person -> person.getNachname())
				.collect(Collectors.toList());

		assertBildePersonNameListOk(personListe, personenNamen);
	}

	@Test
	public void filterPersonsOlderThan18Sort() {

		final List<Person> personListe = PersonFactory.createPersonCollection();

		final List<Person> gefilterteSortierteListe = personListe.stream()
				.filter(person -> person.getAlter() > 18)
				.sorted(Comparator.comparing(Person::getNachname))
				.collect(Collectors.toList());

		assertFilterPersonsOlderThan18SortOk(gefilterteSortierteListe);
	}

	@Test
	public void pruefeOlder60() {

		final List<Person> personListe = PersonFactory.createPersonCollection();

		final boolean existsOlder60 = personListe.stream().anyMatch(person -> person.getAlter() > 60);

		assertPruefeOlder60Ok(existsOlder60);
	}

	private static void assertBildePersonNameListOk(
			List<Person> personListe, List<String> personenNamen) {

		Assert.assertNotNull(personenNamen);
		Assert.assertEquals(personListe.size(), personenNamen.size());

		assertAnzahl(personenNamen, "Müller", 3);
		assertAnzahl(personenNamen, "Peter", 2);
		assertAnzahl(personenNamen, "Meier", 2);
		assertAnzahl(personenNamen, "Thurn-und-Taxis", 1);
		assertAnzahl(personenNamen, "Dampf", 1);
		assertAnzahl(personenNamen, "Bruni", 1);
	}

	private static void assertFilterPersonsOlderThan18SortOk(
			List<Person> gefilterteSortierteListe) {

		Assert.assertNotNull(gefilterteSortierteListe);

		boolean istSortiert = true;
		final int listSize = gefilterteSortierteListe.size();

		if (listSize > 1) {
			for (int i = 0; i < listSize -1; i++) {
				if (gefilterteSortierteListe.get(i).getNachname()
						.compareTo(gefilterteSortierteListe.get(i + 1).getNachname()) < 0) {
					istSortiert = false;
					break;
				}
			}
		}
		Assert.assertTrue(istSortiert);
		Assert.assertEquals(2, gefilterteSortierteListe.stream().count());

		final List<String> nachnamen =
				gefilterteSortierteListe.stream().map(Person::getNachname).collect(Collectors.toList());

		Assert.assertNotNull(nachnamen);
		Assert.assertEquals(2, nachnamen.stream().count());

		assertAnzahl(nachnamen, "Meier", 2);

	}

	private static void assertPruefeOlder60Ok(boolean existsOlder60) {

		Assert.assertFalse(existsOlder60);
	}

	private static void assertErzeugePersonenAusNamenOk(List<Person> personListe) {

		Assert.assertNotNull(personListe);
		Assert.assertEquals(4, personListe.size());

		final List<String> personenNamen =
				personListe.stream().map(Person::getNachname).collect(Collectors.toList());
		Assert.assertTrue(personenNamen.containsAll(
				Arrays.asList(new String[] { "Müller", "Ribery", "Robben", "Lahm" } )));
	}

	private static void assertAnzahl(List<String> personenNamen, String gesuchterNachname, int erwarteteAnzahl) {

		Assert.assertEquals(erwarteteAnzahl,
				personenNamen.stream()
				.filter(nachname -> nachname.equals(gesuchterNachname)).count());
	}


}
