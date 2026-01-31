package de.msg.schulung.java8.aufloesungen;

import de.msg.schulung.java8.domainobjects.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Das Übungsprojekt enthält zwei Klassen {@link Person} und {@link Apple}.
 *
 * Es gibt vier Methoden, die aus einer Liste von Personen alle "alten" (z.B. älter als 60 Jahre) bzw.
 * alle weiblichen und aus einer Liste von Apples alle "schweren" (z.B. schwerer als 150 g) bzw. alle
 * grünen herausfiltern.
 * a)	Nutze das Functional Interface Predicate<T> und schreibe jeweils eine Methode, die aus einer Liste
 * 		von Personen bzw. Apples alle mit einer bestimmten Eigenschaft herausfiltert. Nutze diese Methode
 * 		zum einen unter Verwendung von Lambdas, zum anderen mit Methodenreferenzen.
 * b)	Schreibe eine Methode, die aus einer Liste von Objekten alle mit einer bestimmten Eigenschaft herausfiltert.
 * 		Formuliere die Filterungen aus a) mit Hilfe dieser Methode und Lambdas.
 * 		Tipp: Generics.

 * @author thomannc
 *
 */
public class E02_Predicates {

	// ======================================================================
	// 0) vier einzelne Methoden ohne Java 8
	//
	// Nutze die Hilfsmethoden für Methoden ohne Java 8.
	// ======================================================================

	@Test
	public void testExpoliziteMethodenPersons() {

		final List<Person> persons = PersonFactory.createPersonCollection();

		final List<Person> erwachsenePersonen = filterAdultPersons(persons);

		assertErwachsenePersonenOk(persons, erwachsenePersonen);

		final List<Person> weiblichePersonen = filterFemalePersons(persons);

		assertWeiblichePersonenOk(persons, weiblichePersonen);
	}

	@Test
	public void testExpoliziteMethodenApples() {

		final List<Apple> apples = AppleFactory.createAppleList();

		final List<Apple> greenApples = filterGreenApples(apples);

		assertGreenApplesOk(apples, greenApples);

		final List<Apple> heavyApples = filterHeavyApples(apples);

		assertHeavyApplesOk(apples, heavyApples);
	}

	// Hilfsmethoden für Methoden ohne Java 8

	/**
	 * Schleife für alte {@link Person}s.
	 * @param persons {@link Person}-Liste
	 * @return Teilliste mit allen alten {@link Person}s daraus
	 */
	private List<Person> filterAdultPersons(List<Person> persons) {
		final List<Person> result = new ArrayList<>();
		for (Person person : persons) {
			if (person.getAlter() >= Person.ERWACHSENEN_ALTER) {  // charakteristisches Merkmal
				result.add(person);
			}
		}
		return result;
	}

	/**
	 * Schleife für alle weiblichen Personen.
	 * @param persons Personen-Liste
	 * @return Teilliste mit allen weiblichen Personnen daraus
	 */
	private List<Person> filterFemalePersons(List<Person> persons) {
		final List<Person> result = new ArrayList<>();
		for (Person person : persons) {
			if (Gender.FEMALE.equals(person.getGeschlecht())) {  // charakteristisches Merkmal
				result.add(person);
			}
		}
		return result;
	}

	/**
	 * Schleife für grüne Äpfel.
	 * @param apples Apfelliste
	 * @return Teilliste mit allen grünen Äpfeln daraus
	 */
	private List<Apple> filterGreenApples(List<Apple> apples) {
		final List<Apple> result = new ArrayList<>();
		for (Apple apple : apples) {
			if (Apple.GREEN.equals(apple.getColor())) {  // charakteristisches Merkmal
				result.add(apple);
			}
		}
		return result;
	}

	/**
	 * Schleife für schwere Äpfel.
	 * @param apples Apfelliste
	 * @return Teilliste mit allen schwern Äpfeln daraus
	 */
	private List<Apple> filterHeavyApples(List<Apple> apples) {
		final List<Apple> result = new ArrayList<>();
		for (Apple apple : apples) {
			if (apple.getWeight() > Apple.HEAVY) {  // charakteristisches Merkmal
				result.add(apple);
			}
		}
		return result;
	}

	// ======================================================================
	// Aufgabenteil a)
	//
	// Nutze die Hilfsmethoden für a).
	// ======================================================================

	@Test
	public void testPredicatePerson() {

		final List<Person> persons = PersonFactory.createPersonCollection();

		final List<Person> erwachsenePersonen = filterPersons(persons, person -> person.getAlter() >= Person.ERWACHSENEN_ALTER);

		assertErwachsenePersonenOk(persons, erwachsenePersonen);

		final List<Person> weiblichePersonen = filterPersons(persons, person -> Gender.FEMALE.equals(person.getGeschlecht()));

		assertWeiblichePersonenOk(persons, weiblichePersonen);
	}

	@Test
	public void testMethodenreferenzPerson() {

		final List<Person> persons = PersonFactory.createPersonCollection();

		final List<Person> erwachsenePersonen = filterPersons(persons, this::isErwachsenePerson);

		assertErwachsenePersonenOk(persons, erwachsenePersonen);

		final List<Person> weiblichePersonen = filterPersons(persons, this::isFemalePerson);

		assertWeiblichePersonenOk(persons, weiblichePersonen);
	}

	@Test
	public void testPredicateApple() {

		final List<Apple> apples = AppleFactory.createAppleList();

		final List<Apple> greenApples = filterApples(apples, apple -> Apple.GREEN.equals(apple.getColor()));

		assertGreenApplesOk(apples, greenApples);

		final List<Apple> heavyApples = filterApples(apples, this::isHeavyApple);

		assertHeavyApplesOk(apples, heavyApples);
	}

	@Test
	public void testMethodenreferenzApple() {

		final List<Apple> apples = AppleFactory.createAppleList();

		final List<Apple> greenApples = filterApples(apples, this::isGreenApple);

		assertGreenApplesOk(apples, greenApples);

		final List<Apple> heavyApples = filterApples(apples, this::isHeavyApple);

		assertHeavyApplesOk(apples, heavyApples);
	}

	// Hilfsmethoden für a)

	/**
	 * Schleife für zu testende Äpfel.
	 * @param apples Apfelliste
	 * @param charakteristik Testkriterium
	 * @return Teilliste mit allen Äpfeln daraus, die das Testkriterium erfüllen
	 */
	private List<Apple> filterApples(List<Apple> apples, Predicate<Apple> charakteristik) {
		final List<Apple> result = new ArrayList<>();
		for (Apple apple : apples) {
			if (charakteristik.test(apple)) {  // charakteristisches Merkmal
				result.add(apple);
			}
		}
		return result;
	}

	/**
	 * Schleife für zu testende Personen.
	 * @param persons Personenliste
	 * @param charakteristik Testkriterium
	 * @return Teilliste mit allen Persons daraus, die das Testkriterium erfüllen
	 */
	private List<Person> filterPersons(List<Person> persons, Predicate<Person> charakteristik) {
		final List<Person> result = new ArrayList<>();
		for (Person person : persons) {
			if (charakteristik.test(person)) {  // charakteristisches Merkmal
				result.add(person);
			}
		}
		return result;
	}

	// ======================================================================
	// Aufgabenteil b)
	//
	// Nutze die Hilfsmethode für b).
	// ======================================================================

	@Test
	public void testGenericPredicate() {

		final List<Person> persons = PersonFactory.createPersonCollection();

		final List<Person> erwachsenePersonen = filterList(persons, person -> person.getAlter() >= Person.ERWACHSENEN_ALTER);

		assertErwachsenePersonenOk(persons, erwachsenePersonen);

		final List<Person> weiblichePersonen = filterList(persons, person -> Gender.FEMALE.equals(person.getGeschlecht()));

		assertWeiblichePersonenOk(persons, weiblichePersonen);
	}

	// Hilfsmethode für b)

	private <T> List<T> filterList(List<T> list, Predicate<T> eigenschaft) {
		final List<T> result = new ArrayList<>();
		for (T t : list) {
			if (eigenschaft.test(t)) {
				result.add(t);
			}
		}
		return result;
	}

	// ======================================================================
	// für Methodenreferenzen
	// ======================================================================

	private boolean isErwachsenePerson(Person person) {
		return person != null && person.getAlter() >= Person.ERWACHSENEN_ALTER;
	}

	private boolean isFemalePerson(Person person) {
		return person != null && Gender.FEMALE.equals(person.getGeschlecht());
	}

	private boolean isGreenApple(Apple apple) {
		return apple != null && Apple.GREEN.equals(apple.getColor());
	}

	private boolean isHeavyApple(Apple apple) {
		return apple != null && apple.getWeight() > Apple.HEAVY;
	}

	// ======================================================================
	// interne Hilfsmethoden
	// ======================================================================

	private List<Person> getErwachsenePersonen(List<Person> persons) {
		return persons.stream()
				.filter(person -> isErwachsenePerson(person)).collect(Collectors.toList());
	}

	private List<Person> getFemalePersonen(List<Person> persons) {
		return persons.stream()
				.filter(person -> isFemalePerson(person)).collect(Collectors.toList());
	}

	private List<Apple> getHeavyApples(List<Apple> apples) {
		return apples.stream()
				.filter(apple -> isHeavyApple(apple)).collect(Collectors.toList());
	}

	private List<Apple> getGreenApples(List<Apple> apples) {
		return apples.stream()
				.filter(this::isGreenApple).collect(Collectors.toList());
	}

	private void assertErwachsenePersonenOk(List<Person> persons, List<Person> erwachsenePersonen) {
		Assert.assertNotNull(erwachsenePersonen);
		Assert.assertEquals(PersonFactory.ANZAHL_ERWACHSENE, erwachsenePersonen.size());
		Assert.assertTrue(erwachsenePersonen.containsAll(getErwachsenePersonen(persons)));
	}

	private void assertWeiblichePersonenOk(List<Person> persons, List<Person> weiblichePersonen) {
		Assert.assertNotNull(weiblichePersonen);
		Assert.assertEquals(PersonFactory.ANZAHL_WEIBLICH, weiblichePersonen.size());
		Assert.assertTrue(weiblichePersonen.containsAll(getFemalePersonen(persons)));
	}

	private void assertGreenApplesOk(List<Apple> apples, List<Apple> greenApples) {
		Assert.assertNotNull(greenApples);
		Assert.assertEquals(AppleFactory.ANZAHL_GREEN_APPLES, greenApples.size());
		Assert.assertTrue(greenApples.containsAll(getGreenApples(apples)));
	}

	private void assertHeavyApplesOk(List<Apple> apples, List<Apple> heavyApples) {
		Assert.assertNotNull(heavyApples);
		Assert.assertEquals(AppleFactory.ANZAHL_HEAVY_APPLES, heavyApples.size());
		Assert.assertTrue(heavyApples.containsAll(getHeavyApples(apples)));
	}

}
