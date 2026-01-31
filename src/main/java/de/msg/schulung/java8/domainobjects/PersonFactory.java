package de.msg.schulung.java8.domainobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonFactory {

	public static final int ANZAHL_ERWACHSENE = 4;

	public static final int ANZAHL_WEIBLICH = 4;

	public static List<Person> createPersonCollection() {

		final List<Person> personCollection = new ArrayList<>();

		final String[] vornamen = new String[] {
				"Martina",  	// 0
				"Fritz",		// 1
				"Hans",			// 2
				"Carla",		// 3
				"Kalle",		// 4
				"Hans",			// 5
				"Martina",		// 6
				"Hans",			// 7
				"Peter",		// 8
				"Carla"			// 9
		};

		final String[] nachnamen = new String[] {
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
		};

		for (int i = 0; i < 10; i++) {
			final Person person = new Person();

			person.setAlter(16 + i % 4);
			person.setGeschlecht(i % 3 == 0 ? Gender.FEMALE : Gender.MALE);
			person.setGroesseInCm(165 + 2 * (i + 1));
			person.setNachname(nachnamen[i]);
			person.setVorname(vornamen[i]);
			personCollection.add(person);
		}

		return personCollection;
	}

	public static Map<String, List<Person>> getNamePartitions(List<Person> persons) {
		final Map<String, List<Person>> partitionned = new HashMap<>();

		partitionned.put("Müller", new ArrayList<>());
		partitionned.put("Peter", new ArrayList<>());
		partitionned.put("Meier", new ArrayList<>());
		partitionned.put("Thurn-und-Taxis", new ArrayList<>());
		partitionned.put("Dampf", new ArrayList<>());
		partitionned.put("Bruni", new ArrayList<>());

		partitionned.get("Müller").addAll(Arrays.asList(persons.get(0), persons.get(1), persons.get(8)));
		partitionned.get("Peter").addAll(Arrays.asList(persons.get(2), persons.get(6)));
		partitionned.get("Meier").addAll(Arrays.asList(persons.get(3), persons.get(7)));
		partitionned.get("Thurn-und-Taxis").addAll(Arrays.asList(persons.get(4)));
		partitionned.get("Dampf").addAll(Arrays.asList(persons.get(5)));
		partitionned.get("Bruni").addAll(Arrays.asList(persons.get(9)));

		return partitionned;

	}
}
