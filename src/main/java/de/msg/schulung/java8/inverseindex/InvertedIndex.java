package de.msg.schulung.java8.inverseindex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Übung: Hier ist alles drin; Streams, Kombination, flatMap, peek, Grouping.
 *
 * Implementiere einen inversen Index: D.h. lese eine Datei ein und ordne jedem
 * vorkommenden Wort eine Liste seiner Positionen innerhalb der Datei zu. Definiere
 * Dir eine Liste von Stopwörtern, die nicht berücksichtigt werden sollen (z.B.
 * a oder and bei englischen Texten).
 * Tip: Teile die Dateizeilen mit split() und setze einen Stream mit Hilfe von flatMap auf.
 * 		Nutze die vorgegebene Klasse Word und den Mapper, um in den Wörtern die richtigen
 * 		Positionen einzutragen. Verwende dazu an der richtigen Stelle in der Stream-Pipeline
 * 		die Methode peek(). Terminiere den Stream mit dem groupingBy-Collector.
 *
 * Lernziel: Einsatz verschiedener Stream-Methoden zur kompakten Lösung eines Problems
 * Vorkenntnisse: Stream-Operationen, insbes. flatMap() und peek(), Arrays-API
 */
public class InvertedIndex {

	static public class Word {
		private String value = "";
		private int position;
		private String filename = "";

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public int hashCode() {
			return this.value.hashCode();
		}

		public boolean equals(Object that) {
			if (!(that instanceof Word)) {
				return false;
			}
			Word thatWord = (Word) that;
			return value.equals(thatWord.getValue())
					&& position == thatWord.getPosition()
					&& filename.equals(thatWord.getFilename());
		}
	}

	static public class Mapper {

		public static final Mapper MAPPER = new Mapper();

		private int counter = 1;

		public Word mapToWord(String str, String filename) {
			final Word word = new Word();

			word.setPosition(counter);
			word.setValue(str);
			word.setFilename(filename);

			return word;
		}

		public void incrementPosition(Word word) {
			word.setPosition(counter);
			counter++;
		}
	}

	private static final List<String> STOPWORDS = Arrays.asList("Hi", "a",
			"the", "in");

	public static Map<String, List<Word>> indexFile(File file)
			throws FileNotFoundException {

		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));

//		Stream<String> flatMap = reader.lines().flatMap(
//				line -> Arrays.stream(line.split(",|\\W+")));
//
//		Stream<String> mapToLowerCase = flatMap.map(str -> str.toLowerCase());
//
//		Stream<Word> mapToWord = mapToLowerCase.map(str -> Mapper.MAPPER
//				.mapToWord(str, file.getName()));
//
//		Stream<Word> incrementedPositions = mapToWord
//				.peek(Mapper.MAPPER::incrementPosition);
//
//		Stream<Word> removeStopwords = incrementedPositions
//				.filter(word -> !isStopword(word.getWord(), stopwords));
//
//		Map<String, List<Word>> invertedIndex = removeStopwords
//				.collect(Collectors.groupingBy(Word::getWord));

		 final Map<String, List<Word>> invertedIndex = reader.lines()
				 .filter(line -> !line.isEmpty())
				 .flatMap(line -> Arrays.stream(line.split("[\\s]")))  // ersetzt eine Zeile durch den gesplitteten Stream
				 .map(str -> str.toLowerCase())
				 .filter(str -> !STOPWORDS.contains(str))
				 .map(str -> Mapper.MAPPER.mapToWord(str, file.getName()))
				 .peek(Mapper.MAPPER::incrementPosition)  // aktuelles Word bekommt die nächste Position
				 .collect(Collectors.groupingBy(Word::getValue));  // Gruppierung entlang der Wörter

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return invertedIndex;
	}

	private static boolean isStopword(String word, List<String> stopwords) {
		final boolean isStopword = stopwords != null
				&& stopwords.stream().map(String::toLowerCase)
						.collect(Collectors.toList()).contains(word);
		return isStopword;
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out
					.println("Need exactly one parameter - filename expected");
			System.exit(-1);
		}

		final String filename = args[0];
		final File file = new File(filename);

		try {
			final Map<String, List<Word>> index = indexFile(file);

			index.keySet().stream().forEach(key -> printEntry(key, index));
			System.out.println();

		} catch (FileNotFoundException e) {
			System.out.println("IO error: " + e.getMessage());
			System.exit(-1);
		}

	}

	private static void printEntry(String key, Map<String, List<Word>> index) {
		final List<Word> wordList = index.get(key);
		System.out.print(key + ": ");
		wordList.stream().forEach(
				word -> System.out.print("(" + word.getFilename() + "/"
						+ word.getPosition() + ")"));

		System.out.println();
	}

}
