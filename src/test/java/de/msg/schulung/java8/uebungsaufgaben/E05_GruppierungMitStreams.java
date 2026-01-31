package de.msg.schulung.java8.uebungsaufgaben;

import de.msg.schulung.java8.domainobjects.Person;
import de.msg.schulung.java8.domainobjects.PersonFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Gruppiere eine Liste von {@link Person}s nach dem Anfangsbuchstaben ihres Namens. Ergebnis soll eine Map sein,
 * die jedem vorkommenden Anfangsbuchstaben die Liste der {@link Person}s zuordnet, deren Name mit diesem Buchstaben
 * beginnt.
 * a)	Implementiere die Gruppierung mit Hilfe von konventionellen Java-Mitteln. (bereits implementiert)
 * b)	Gruppiere die Liste mit Hilfe von Streams. Tipp: Nutze den Collector groupingBy().
*/
public class E05_GruppierungMitStreams {

	// ======================================================================================
	// Aufgabenteil a)
	// ======================================================================================

	private Map<Character, List<Person>>  gruppierePersonenNachAnfangsbuchstabenKonventionell(List<Person> personenliste) {

		final Map<Character, List<Person>> result = new HashMap<>();

		for (Person elem : personenliste) {
			// Ordnungskriterium von elem ermitteln
			final Character key = elem.getNachname().charAt(0);

			// elem seinem Ordnungskriterium zuordnen
			if (!result.containsKey(key)) {
				result.put(key, new ArrayList<>());
			}
			result.get(key).add(elem);
		}

		return result;
	}

	// ======================================================================================
	// Aufgabenteil b)
	// ======================================================================================

	@Test
	public void testGruppierePersonenNachAnfangsbuchstabeMitStreams() {
		final List<Person> personenliste = PersonFactory.createPersonCollection();

		// TODO ersetze null durch einen Ausruck, der mit Hilfe des Stream APIs die übergebene personenliste
		// nach den Anfangsbuchstaben ihres Nachnamens gruppiert.
		final Map<Character, List<Person>> gruppierung = personenliste
				.stream()
				.collect(Collectors.groupingBy(person -> person.getNachname().charAt(0)));

		assertGruppierePersonenNachAnfangsbuchstabeMitStreamsOk(personenliste, gruppierung);
	}

	// interne Hilfsmethode

	private void assertGruppierePersonenNachAnfangsbuchstabeMitStreamsOk(
			List<Person> personenliste, Map<Character, List<Person>> gruppierung) {
		final Map<Character, List<Person>> gruppierungKonventionell =
				gruppierePersonenNachAnfangsbuchstabenKonventionell(personenliste);

		final Set<Character> keySetKonventionell = gruppierungKonventionell.keySet();
		final Set<Character> keySet = gruppierung.keySet();

		Assert.assertTrue(keySetKonventionell.containsAll(keySet));
		Assert.assertTrue(keySet.containsAll(keySetKonventionell));
		Assert.assertEquals(5, keySetKonventionell.size());
		Assert.assertEquals(5, keySet.size());

		// Key Sets sind gleich

		for (Character key : keySetKonventionell) {  // keySet ginge auch

			final List<Person> personlistKonventionell = gruppierungKonventionell.get(key);
			final List<Person> personlistCollector = gruppierung.get(key);

			Assert.assertTrue(personlistKonventionell.containsAll(personlistCollector));
			Assert.assertTrue(personlistCollector.containsAll(personlistKonventionell));
			Assert.assertTrue(personlistKonventionell.size() == personlistCollector.size());
		}

	}
}
