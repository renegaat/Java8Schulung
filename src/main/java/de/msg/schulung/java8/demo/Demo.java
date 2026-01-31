package de.msg.schulung.java8.demo;

import java.io.File;
import java.io.FileFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import de.msg.schulung.java8.domainobjects.AmazonOrder;
import de.msg.schulung.java8.domainobjects.Apple;
import de.msg.schulung.java8.domainobjects.Gender;
import de.msg.schulung.java8.domainobjects.OptionalUser;
import de.msg.schulung.java8.domainobjects.User;


public class Demo {

	private static final String CURRENT_DIR = ".";
	private static final String CSV_SUFFIX = ".csv";

	/**
	 * zu easy	 * Vorkenntnisse: keine
	 */
	private static void filterGeradeZahlen() {

		final List<Integer> zahlenliste = (List<Integer>) Arrays.asList(3, 4, 5, 6, 8, 13, 14, 17, 19, 20);

		// a) vor Java 8
		final List<Integer> geradeZahlenHerkoemmlich = new ArrayList<>();
		for (Integer zahl : zahlenliste) {
			if (zahl % 2 == 0) {
				geradeZahlenHerkoemmlich.add(zahl);
			}
		}
		printIntegers(geradeZahlenHerkoemmlich);

		// b) mit Java 8 Lambda
		final List<Integer> geradeZahlen =
				zahlenliste.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());

		printIntegers(geradeZahlen);

		// c) mit Java 8 Methodenreferenz (Hilfsmethode)
		final List<Integer> geradeZahlenMethodenreferenz =
				zahlenliste.stream().filter(Demo::isEvenNumber).collect(Collectors.toList());

		printIntegers(geradeZahlenMethodenreferenz);
	}

	private static boolean isEvenNumber(Integer number) {
		return number != null && (number % 2 == 0);
	}

	/**
	 * 1. Übung zu Lambdas
	 * Implementiere eine Methode, die alle Directories im aktuellen Verzeichnis findet.
	 * a)	herkömmlich
	 * b) 	mit Java 8 Lambdas
	 * c)	mit Java 8 Methodenreferenzen
	 *
	 * Vorkenntnisse: Lambdas und Methodenreferenzen
	 */
	private static void listDirectories() {
		// a) vor Java 8
		final File[] directories = new File(CURRENT_DIR).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname != null && pathname.isDirectory();
			}
		});
		// b) mit Lambda
		final File[] directoriesLambda = new File(CURRENT_DIR).listFiles(file -> file.isDirectory());
		// c) mit Java 8 Methodenreferenzen
		final File[] directoriesMethodenreferenzen = new File(CURRENT_DIR).listFiles(File::isDirectory);
	}

	/**
	 * Übung zu (1)
	 * Implementiere eine Methode, die alle CSV-Dateien im aktuellen Verzeichnis findet.
	 * a)	herkömmlich
	 * b) 	mit Java 8 Lambdas
	 * c)	mit Java 8 Methodenreferenzen (Du brauchst eine Hilfsmethode)
	 * Kürze den Lambda-Ausdruck aus b) mit der Hilfsmethode.
	 *
	 * Vorkenntnisse: Lambdas und Methodenreferenzen
	 */
	private static void listCsvFiles() {
		// a) mit anonymer Klasse
		final File[] csvFiles = new File(CURRENT_DIR).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname != null && pathname.getName().endsWith(CSV_SUFFIX);
			}
		});
		// b) mit Lambda
		final File[] csvFilesMitLambda1 = new File(CURRENT_DIR).listFiles(file -> file.getName().endsWith(CSV_SUFFIX));
		final File[] csvFilesMitLambda2 = new File(CURRENT_DIR).listFiles(file -> isCsvFile(file));
		// c) mit Methodenreferenz (Hinweis: Hilfsmethode definieren)
		final File[] csvFilesMitMethodenreferenz = new File(CURRENT_DIR).listFiles(Demo::isCsvFile);
	}

	private static boolean isCsvFile(File file) {
		return file != null && file.getName().endsWith(CSV_SUFFIX);
	}

	/**
	 * Übung zum funktionalen Template-Pattern
	 * a)	Erzeuge auf herkömmliche Art eine Katze und einen Hund und lass sie
	 * 		einen Laut von sich geben.
	 * b)	Implementiere AbstractAnimal durch eine Klasse Anmial, die anhand einer
	 * 		Function das Template-Verhalten abstrahiert.
	 * c) 	Welche anderen Patterns lassen sich so noch funktional umsetzen?
	 *
	 * Lernziel: Einsatz von Functional Interfaces
	 * Vorkenntnisse: Lambdas, Template/Strategy-Pattern
	 */
	private static void katzenUndHunde() {

		// a) zwei Klassen/Implementierungen
		final Cat katze = new Cat();
		final Dog hund = new Dog();

		gebtLaut(katze, hund);

		// b) eine Klasse/Implementierung
		final Animal katzeFunctional = new Animal(animal -> "Miau");
		final Animal hundFunctional = new Animal(animal -> "Wauwau");

		gebtLaut(katzeFunctional, hundFunctional);
	}

	/**
	 * Präsentation: Lambdas haben einen Java-Typ
	 *
	 * Lernziel: Java ist nicht funktional, es gibt nur Typen, die es erlauben,
	 * in einem funktionales Stil zu programmieren
	 */
	private static void einordnungInsTypsystem() {
		final List<Integer> alteListe = Arrays.asList(4, 9, 0, 22, 17, 11, 3);
		printIntegers(alteListe);

		// a) mit Lambda
		final List<Integer> neueListe = bearbeiteListe(alteListe, x -> 2 * x);
		printIntegers(neueListe);

		// b) explizit
		final List<Integer> neueListeMitUnaryOperator = bearbeiteListe(alteListe, new DoubleOperator());
		printIntegers(neueListeMitUnaryOperator);
//
//		final int faktor = 42;
//
//		final List<Integer> andereListe = bearbeiteListe(alteListe, x -> faktor * x);
//		printIntegers(andereListe);
	}

	private static List<Integer> bearbeiteListe(List<Integer> liste, UnaryOperator<Integer> regel) {
		final List<Integer> ergebnis = new ArrayList<Integer>();
		for (Integer elem : liste) {
			ergebnis.add(regel.apply(elem)); // Operator anwenden: Integer apply(Integer elem)
		}
		return ergebnis;
	}

	/**
	 * Übung: Prüfung von Streams
	 * Erzeuge eine Liste von 30 Usern. Prüfe - einmal auf herkömmliche Weise, einmal mit Java 8 -, ob
	 * a)	sich ein Mann darunter befindet
	 * b)	keine Frau darin enthalten ist.
	 *
	 * Vorkenntnisse: Streams und Terminal Operations
	 */
	private static void existenzPruefung() {

		final List<User> userListe = erzeugeUserListe(30);

		// a) Existiert ein Mann?
		boolean maleExistsResult = false;
		for (User user : userListe) {
			if (Gender.MALE.equals(user.getGender())) {
				maleExistsResult = true;
				break;  // Short-Circuit
			}
		}
		final boolean maleExistsJava8 = userListe.stream().anyMatch(user -> Gender.MALE.equals(user.getGender()));

		System.out.println(maleExistsResult);
		System.out.println(maleExistsJava8);

		// b) Existiert keine Frau?
		boolean noFemaleResult = true;
		for (User user : userListe) {
			if (Gender.FEMALE.equals(user.getGender())) {
				noFemaleResult = false;
				break;  // Short-Circuit
			}
		}
		final boolean noFemaleJava8 = userListe.stream().noneMatch(user -> Gender.FEMALE.equals(user.getGender()));

		System.out.println(noFemaleResult);
		System.out.println(noFemaleJava8);
	}

	/**
	 * Übung: Gruppierung von Listenelementen nach einem Kriterium.
	 * Erzeuge eine Liste von 15 User-Objekten.
	 * a) 	Gruppiere diese Liste in einer Map nach ihrem Alter.
	 * b)	Abstrahiere Deine Implementierung hin zu einer generischen Klasse
	 * 		Grouper, die eine Methode group() besitzt, die als Parameter die
	 * 		zu gruppierende Liste und ein "Gruppierungskriterium" erhält.
	 * 		Tip: Nutze das Functional Interface {@link Function}.
	 * c)	Durchsuche das Collectors-API nach einem geeigneten Collector
	 * 		und formuliere die Gruppierung mit einer Zeile Java 8.
	 *
	 * Vorkenntnisse: Generics, Streams, Collectors
	 */
	private static void gruppiereUserListe() {
		// Gruppierung von Usern nach Alter
		final List<User> userListe = erzeugeUserListe(15);
		// a) vor Java 8
		final Map<Integer, List<User>> userListeProAlter = new HashMap<>();
		for (User user : userListe) {
			final Integer age = user.getAge();  // Gruppierungskriterium
			if (!userListeProAlter.containsKey(age)) {
				userListeProAlter.put(age, new ArrayList<>());
			}
			userListeProAlter.get(age).add(user);
		}

		printUserGruppierung(userListeProAlter);

		// b) mit generischem Gruppierer
		final Grouper<User, Integer> userGrouperProAlter = new Grouper<>();
		userGrouperProAlter.group(userListe, User::getAge);

		// c) mit Java 8 grouping-Collector
		final Map<Integer, List<User>> userlisteProAlterMitStreams =
				userListe.stream().collect(Collectors.groupingBy(User::getAge));

		printUserGruppierung(userlisteProAlterMitStreams);
	}

	/**
	 * Übung: Äpfel filtern
	 * Erzeuge eine Liste von Apples und filtere jeweils grüne bzw. schwere Apples aus der Liste.
	 * a)	in herkömmlichem Java
	 * b) 	mit der Methode filterApples, die eine Apple-Liste und ein Predicate erwartet,
	 * 		mit Hilfe eines Lambdas oder einer Methodenreferenz
	 * c) 	mit der Methode filterApples mit Übergabe eines anonym definierten Predicates über Apples
	 * Definiere ein konkretes Predicate und übergebe dieses der Methode filterApples.
	 *
	 * Lernziel: Implementierung von Functional Interfaces
	 * Vorkenntnisse: Lambdas, Methodenreferenzen, List-Collector
	 */
	private static void filterApples() {

		final List<Apple> apples = createAppleList();

		// vor Java 8
		final List<Apple> greenApplesAlt = filterGreenApples(apples);
		final List<Apple> heavyApplesAlt = filterHeavyApples(apples);

		printApples(greenApplesAlt);
		printApples(heavyApplesAlt);

		// Java 8 mit Lambda
		final List<Apple> greenApplesLambda = filterApples(apples, apple ->
			apple != null && Apple.GREEN.equals(apple.getColor()));
		final List<Apple> heavyApplesLambda = filterApples(apples, apple ->
			apple != null && apple.getWeight() > 150);

		printApples(greenApplesLambda);
		printApples(heavyApplesLambda);

		// Java 8 mit Methodenreferenz
		final List<Apple> greenApplesMethodenreferenz = filterApples(apples, Demo::isGreenApple);
		final List<Apple> heavyApplesMethodenreferenz = filterApples(apples, Demo::isHeavyApple);

		printApples(greenApplesMethodenreferenz);
		printApples(heavyApplesMethodenreferenz);

		// Java 8 mit Predicate (anonym)
		final List<Apple> greenApplesAnonymeKlasse = filterApples(apples, new Predicate<Apple>() {
			@Override
			public boolean test(Apple apple) {
				return isGreenApple(apple);
			}
		});
		printApples(greenApplesAnonymeKlasse);

		// Java 8 mit konkretem Predicate
		final List<Apple> greenApplesKonkretesPredicate = filterApples(apples, new GreenApplesPredicate());
		printApples(greenApplesKonkretesPredicate);

		// Java 8 mit Streams
		final List<Apple> greenApplesStreamed =
				apples.stream().filter(Demo::isGreenApple).collect(Collectors.toList());
		final List<Apple> heavyApplesStreamed =
				apples.stream().filter(Demo::isHeavyApple).collect(Collectors.toList());

		printApples(greenApplesStreamed);
		printApples(heavyApplesStreamed);
	}

	private static void filterUsers() {

		final List<User> userliste = erzeugeUserListe(20);

		// vor Java 8
		final List<User> oldUsersAlt = filterOldUsers(userliste);
		final List<User> femaleUsersAlt = filterFemaleUsers(userliste);

		print(oldUsersAlt);
		print(femaleUsersAlt);

		// Java 8 mit Lambda
		final List<User> oldUsersLambda = filterUsers(userliste, user ->
			user != null && user.getAge() > 60);
		final List<User> femaleUsersLambda = filterUsers(userliste, user ->
			user != null && Gender.FEMALE.equals(user.getGender()));

		print(oldUsersLambda);
		print(femaleUsersLambda);

		// Java 8 mit Methodenreferenz
		final List<User> oldUsersMethodenreferenz = filterUsers(userliste, Demo::isOldUser);
		final List<User> femaleUsersMethodenreferenz = filterUsers(userliste, Demo::isFemaleUser);

		print(oldUsersMethodenreferenz);
		print(femaleUsersMethodenreferenz);

		// Java 8 mit Predicate (anonym)
		final List<User> oldUsersAnonymeKlasse = filterUsers(userliste, new Predicate<User>() {
			@Override
			public boolean test(User user) {
				return isOldUser(user);
			}
		});
		print(oldUsersAnonymeKlasse);

		// Java 8 mit konkretem Predicate
		final List<User> femaleUsersKonkretesPredicate = filterUsers(userliste, new FemaleUserPredicate());
		print(femaleUsersKonkretesPredicate);

	}

	private static void filterGeneric() {

		final List<User> userliste = erzeugeUserListe(20);
		final List<Apple> appleList = createAppleList();

		final List<User> oldUsersGeneric = filterList(userliste, Demo::isOldUser);
		final List<Apple> greenApplesGeneric = filterList(appleList, apple -> Apple.GREEN.equals(apple.getColor()));
	}

	/**
	 * Schleife für grüne Äpfel.
	 * @param apples Apfelliste
	 * @return Teilliste mit allen grünen Äpfeln daraus
	 */
	private static List<Apple> filterGreenApples(List<Apple> apples) {
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
	private static List<Apple> filterHeavyApples(List<Apple> apples) {
		final List<Apple> result = new ArrayList<>();
		for (Apple apple : apples) {
			if (apple.getWeight() > 150) {  // charakteristisches Merkmal
				result.add(apple);
			}
		}
		return result;
	}

	/**
	 * Schleife für zu testende Äpfel.
	 * @param apples Apfelliste
	 * @param charakteristik Testkriterium
	 * @return Teilliste mit allen Äpfeln daraus, die das Testkriterium erfüllen
	 */
	private static List<Apple> filterApples(List<Apple> apples, Predicate<Apple> charakteristik) {
		final List<Apple> result = new ArrayList<>();
		for (Apple apple : apples) {
			if (charakteristik.test(apple)) {  // charakteristisches Merkmal
				result.add(apple);
			}
		}
		return result;
	}

	private static boolean isGreenApple(Apple apple) {
		return apple != null && Apple.GREEN.equals(apple.getColor());
	}

	private static boolean isHeavyApple(Apple apple) {
		return apple != null && apple.getWeight() > 150;
	}

	private static List<Apple> createAppleList() {
		final List<Apple> apples = new ArrayList<>();

		for (int i = 0; i < 15; i++) {
			final Apple apple = new Apple();

			if (i % 3 == 0) {
				apple.setColor(Apple.GREEN);
			} else if (i % 7 == 0 && i > 0) {
				apple.setColor(Apple.RED);
			} else {
				apple.setColor(Apple.YELLOW);
			}
			apple.setWeight(160);

			apples.add(apple);
		}

		return apples;
	}

	/**
	 * Schleife für zu testende User.
	 * @param users Userliste
	 * @param charakteristik Testkriterium
	 * @return Teilliste mit allen Users daraus, die das Testkriterium erfüllen
	 */
	private static List<User> filterUsers(List<User> users, Predicate<User> charakteristik) {
		final List<User> result = new ArrayList<>();
		for (User user : users) {
			if (charakteristik.test(user)) {  // charakteristisches Merkmal
				result.add(user);
			}
		}
		return result;
	}

	/**
	 * Schleife für alte User.
	 * @param users User-Liste
	 * @return Teilliste mit allen alten Usern daraus
	 */
	private static List<User> filterOldUsers(List<User> users) {
		final List<User> result = new ArrayList<>();
		for (User user : users) {
			if (user.getAge() > 60) {  // charakteristisches Merkmal
				result.add(user);
			}
		}
		return result;
	}

	/**
	 * Schleife für alle weiblichen User.
	 * @param users User-Liste
	 * @return Teilliste mit allen weiblichen Usern daraus
	 */
	private static List<User> filterFemaleUsers(List<User> users) {
		final List<User> result = new ArrayList<>();
		for (User user : users) {
			if (Gender.FEMALE.equals(user.getGender())) {  // charakteristisches Merkmal
				result.add(user);
			}
		}
		return result;
	}

	private static boolean isOldUser(User user) {
		return user != null && user.getAge() > 60;
	}

	private static boolean isFemaleUser(User user) {
		return user != null && Gender.FEMALE.equals(user.getGender());
	}

	private static <T> List<T> filterList(List<T> list, Predicate<T> eigenschaft) {
		final List<T> result = new ArrayList<>();
		for (T t : list) {
			if (eigenschaft.test(t)) {
				result.add(t);
			}
		}
		return result;
	}

	/**
	 * Übung.
	 * Schreibe eine generische Methode, die eine Liste beliebiger Elemente hintereinander,
	 * komma-separiert auf System.out ausgibt
	 *
	 *  Lernziel: Einsatz von Lambdas
	 *  Vorkenntnisse: Generics, Syntax von Lambdas
	 */
	private static <ELEM> void printlnList(List<ELEM> list) {
		list.forEach(elem -> { System.out.print(elem + ","); System.out.println(); });
	}

	/**
	 * Vorhandene Hilfsmethode zum Ausgeben von Integers.
	 * @param zahlenliste Liste von Integers
	 */
	private static void printIntegers(List<Integer> zahlenliste) {
		zahlenliste.forEach(zahl -> System.out.print(zahl + ", "));
		System.out.println();
	}

	/**
	 * Vorhandene Hilfsmethode zum Ausgeben von Apples.
	 * @param apfelliste Liste von Apples
	 */
	private static void printApples(List<Apple> apfelliste) {
		apfelliste.forEach(apple -> System.out.print(apple + ", "));
		System.out.println();
	}

	private static <T> void print(List<T> liste) {
		liste.forEach(t -> System.out.print(String.valueOf(t) + ", "));
		System.out.println();
	}

	/**
	 * Vorhandene Hilfsmethode zum Ausgeben eines java.util.Dates und eines LocalDates.
	 * @param herkoemmlich ein java.util.Date
	 * @param localDate ein LocalDate
	 */
	private static void printDates(Date herkoemmlich, LocalDate localDate) {
		System.out.println(herkoemmlich + ",  " + localDate);
	}

	private static void gebtLaut(AbstractAnimal... animals) {
		if (animals != null) {
			Arrays.asList(animals).forEach(animal -> System.out.println(animal.gibLaut()));
		}
	}

	private static List<User> erzeugeUserListe(int anzahl) {
		final List<User> userListe = new ArrayList<>();

		for (int i = 0; i < anzahl; i++) {
			final User user = new User(
					"Hans Mustermann " + (i + 1),
					i % 3,
					i % 2 == 0 ? Gender.MALE : Gender.FEMALE);
			userListe.add(user);
		}

		return userListe;
	}

	private static void streameUserListe() {
		final List<User> userListe = erzeugeUserListe(50);

		// vor Java 8
		final List<Integer> altersliste = new ArrayList<>();
		for (User user : userListe) {
			if (user.getName().endsWith("5")) {
				final Integer alter = user.getAge();
				altersliste.add(alter);
			}
		}
		printIntegers(altersliste);

		// Java 8 Streams
		final List<Integer> alterslisteMitStreams = userListe.stream()
				.filter(user -> user.getName().endsWith("5"))
				.map(User::getAge)
				.collect(Collectors.toList());
		printIntegers(alterslisteMitStreams);
	}

	private static void printUserGruppierung(Map<Integer, List<User>> gruppierung) {

		for (Integer age : gruppierung.keySet()) {
			final List<User> userListe = gruppierung.get(age);
			System.out.print(age + ": ");
			userListe.forEach(System.out::print);
			System.out.println();
		}
	}

	/**
	 *
	 */
	private static void heiligAbend() {
		final Calendar calendar = new GregorianCalendar(2016, Calendar.DECEMBER, 25);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		final Date heiligAbendHerkoemmlich = calendar.getTime();

		final LocalDate heiligAbend = LocalDate.of(2016, 12, 25).minusDays(1);

		printDates(heiligAbendHerkoemmlich, heiligAbend);
	}

	private static void optionalUsers() {

		final User user = new User("Hans Mustermann", null, Gender.MALE);
		final OptionalUser optionalUser = new OptionalUser("Hans Mustermann", null, Gender.MALE);

		if (user.getAge() != null) {
			System.out.println(user.getName() + " ist " + user.getAge().intValue() + " Jahre alt.");
		}
		if (optionalUser.getAge().isPresent()) {  // explizites Programmiermodell
			System.out.println(optionalUser.getName() + " ist " + optionalUser.getAge().get().intValue() + " Jahre alt");
		}
	}

	private static enum WeightEnum {

		T("t"),
		KG("kg"),
		G("g"),
		MG("mg");

		private String label;

		private WeightEnum(String label) {
			this.label = label;
		}

		public Optional<WeightEnum> forLabel(String label) {
			return Stream.of(values()).filter(value -> value.label.equals(label)).findFirst();
		}
	}

	private static void enumForLabel() {

	}

	private static void streamDemos() {
		// intermediate

		List<String> wochentage = new ArrayList<>();

		Stream<String> wochentageMitR =
				wochentage.stream().filter(w -> w.toLowerCase().contains("r"));

		Stream<Integer> laengen =
				wochentage.stream().map(String::length);

		Stream<String> unterschiedlicheAnfangsbuchstaben =
				wochentage.stream().map(w -> w.substring(0, 1)).distinct();

		Stream<Integer> zehnBisFuenfzig =
				Stream.iterate(10, x -> x + 10).limit(5);

		Stream<String> peeked =
				wochentage.stream().peek(System.out::println).peek(System.out::println);

		List<User> users = new ArrayList<>();

		Stream<Integer> sortiertNachAlter =
				users.stream().map(User::getAge).sorted();

		List<Integer> zahlen = new ArrayList<>();

		// terminal

		boolean esGibtGeradeZahlen =
				zahlen.stream().anyMatch(x -> x % 2 == 0);

		List<Integer> listeSortierterAltersangaben =
				sortiertNachAlter.collect(Collectors.toList());
	}

	private static void amazon() {

		List<AmazonOrder> orders = new ArrayList<>();

		// gruppiere alle Aufträge, die teuerer als 30 Euro sind, nach ihrerm Besteller

		Map<String, List<AmazonOrder>> kostenlosProBesteller =
				orders.stream().filter(order -> order.getPrice() >= 30.0).collect(Collectors.groupingBy(AmazonOrder::getSender));

		// sortiere nach Auftragsnummer, ordne jeder Auftragsnummer ihren Preis zu

		List<Pair<String,Integer>> preisZuAuftragsnummer =
			orders.stream()
				.sorted(
						(order1, order2)
						-> order1.getOrderNumber().compareTo(order2.getOrderNumber()))
				.map(order -> Pair.of(order.getOrderNumber(), order.getPrice()))
				.collect(Collectors.toList());

		// gruppiere die Auftragsnummern von Aufträgen, die teuerer als 100 Euro sind, nach
		// dem Anfangsbuchstaben des Bestellers; betrachte nur Artikel, die mit D anfangen
		Map<Character, List<String>> auftragsnummernZuAnfangsbuchstaben = new HashMap<>();
		orders.stream()
			.filter(order -> order.getPrice() > 100.0)
			.filter(order -> order.getArticleName().startsWith("D"))
			.collect(Collectors.groupingBy(order -> order.getSender().charAt(0)))
			.entrySet()
			.stream()
			.forEach(
					(Map.Entry<Character, List<AmazonOrder>> entry) ->
					auftragsnummernZuAnfangsbuchstaben.put(entry.getKey(), entry.getValue().stream().map(order -> order.getOrderNumber()).collect(Collectors.toList())));
	}

	private static void gruppiereUserNachAnfangsbuchstabe() {

		final List<User> users = erzeugeUserListe(30);

		final Map<Character, List<User>> userGruppierung =
				users.stream().collect(Collectors.groupingBy(user -> user.getName().charAt(0)));
	}

	private static void mapToNewUsers() {
		final List<String> names = new ArrayList<>();

		final List<User> users = names.stream().map(User::new).collect(Collectors.toList());
	}

	public static void main(String[] args) {

		// Einleitung
		filterGeradeZahlen();

		// Lambdas als Typen
		einordnungInsTypsystem();

		// von der Schleife übers Prädikat zum Stream
		filterApples();

		// Schleifen, anonyme Klassen, Methodenreferenzen und Lambdas
		listDirectories();
		listCsvFiles();

		// kombinierte Streams
		streameUserListe();

		// Partitionierung von Listen konventionell und mit Java 8
		gruppiereUserListe();

		// Template Pattern funktional
		katzenUndHunde();
	}

}
