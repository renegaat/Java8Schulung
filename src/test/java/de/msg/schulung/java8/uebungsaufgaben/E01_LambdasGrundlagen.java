package de.msg.schulung.java8.uebungsaufgaben;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Schreibe eine Methode, die eine Liste aller CSV-Dateien im aktuellen Verzeichnis ermittelt. Nutze hierbei
 * a)	keine Java-8-Sprachkonstrukte
 * b)	ein Lambda
 * c)	eine Methodenreferenz.
 *
 * Was musst Du bei der Verwendung einer Methodenreferenz sicherstellen?
 *
 * @author thomannc
 *
 */
public class E01_LambdasGrundlagen {

	private static final String PROJECT_BASEDIR = System.getProperty("user.dir");

	private static final File FILES_DIR = new File(PROJECT_BASEDIR, "src/test/resources/files");

	private static final String CSV_SUFFIX = ".csv";

	private static final String[] EXPECTED_FILE_NAMES = new String[] { "datei1.csv", "datei2.csv", "datei5.csv" };

	@Test
	public void ermittleCsvDateienAnonymeKlasse() {

		// a) mit anonymer Klasse

		final File[] csvFiles = FILES_DIR.listFiles(
				new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						return pathname.getName().endsWith(CSV_SUFFIX);
					}
				}
		);

		assertSuccess(csvFiles);
	}

	@Test
	public void ermittleCsvDateienLambda() {

		// b) mit Lambda

		final File[] csvFilesMitLambda1 = FILES_DIR.listFiles(pathname ->  pathname.getName().endsWith(CSV_SUFFIX));

		assertSuccess(csvFilesMitLambda1);
	}

	@Test
	public void ermittleCsvDateienMitMethodenreferenz() {

		FileFilter fileFilter;

		// c) mit Methodenreferenz (Hinweis: Hilfsmethode definieren)

		final File[] csvFilesMitMethodenreferenz = FILES_DIR.listFiles(E01_LambdasGrundlagen::isCsvFile
		);

		assertSuccess(csvFilesMitMethodenreferenz);
	}

    private static boolean isCsvFile(File file) {
		return (file != null && file.getName().endsWith(CSV_SUFFIX));
	}

	private static void assertSuccess(File[] csvFiles) {

		Assert.assertNotNull(csvFiles);
		Assert.assertEquals(3, csvFiles.length);

		final List<String> fileNameList = Arrays.stream(csvFiles).map(File::getName).collect(Collectors.toList());

		Assert.assertTrue(fileNameList.containsAll(Arrays.asList(EXPECTED_FILE_NAMES)));
	}
}
