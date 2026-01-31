package de.msg.schulung.java8.uebungsaufgaben;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Füge dem Aufzählungstyp {@link FarbeEnum} eine Methode hinzu, die mit Hilfe von Java 8 prüft,
 * ob ein gegebener String als Label eines der enthaltenen Elemente vorkommt.
 * Tipp: Die Methode values() eines Aufzählungstyps liefert ein Array der Werte, die den
 * Elementen des Aufzählungstyps zugeordnet sind. Nutze diese, um daraus einen Stream zu
 * bilden und verwende diesen zur Prüfung der o.g. Bedingung.
 *
 * @author thomannc
 *
 */
public class E08_Optional {

	public static enum FarbeEnum {

		ROT("Rot"),
		GRUEN("Grün"),
		GELB("Gelb"),
		BLAU("Blau");

		private String label;

		private FarbeEnum(String label) {
			this.label = label;
		}

		public String toString() {
			return this.label;
		}

		public static boolean exists(String label) {
			final boolean exists =
					Stream.of(FarbeEnum.values()
					).anyMatch(value -> value.toString().equals(label));
			return exists;
		}

		// Anwendungsbeispiel für Optional
		public static Optional<FarbeEnum> forLabel(String label) {

			final Stream<FarbeEnum> farbEnumValueStream = Stream.of(FarbeEnum.values());

			// TODO ersetze null durch einen Ausdruck, der aus dem farbEnumValueStream optional den ersten
			// FarbeEnum-Wert liefert, der das Label label hat. Tipp: filter und findFirst
			final Optional<FarbeEnum> farbe = farbEnumValueStream
					.filter(value -> value.toString().equals(label)).findFirst();

			return farbe;
		}

	}

	@Test
	public void testForLabel() {

		final Optional<FarbeEnum> optionalRot = FarbeEnum.forLabel("Rot");
		Assert.assertTrue(optionalRot.isPresent() && FarbeEnum.ROT.equals(optionalRot.get()));

		final Optional<FarbeEnum> optionalrot = FarbeEnum.forLabel("rot");
		Assert.assertFalse(optionalrot.isPresent());

		final Optional<FarbeEnum> optionalGruen = FarbeEnum.forLabel("Grün");
		Assert.assertTrue(optionalGruen.isPresent() && FarbeEnum.GRUEN.equals(optionalGruen.get()));

		final Optional<FarbeEnum> optionalgruen = FarbeEnum.forLabel("grün");
		Assert.assertFalse(optionalgruen.isPresent());

		final Optional<FarbeEnum> optionalGelb = FarbeEnum.forLabel("Gelb");
		Assert.assertTrue(optionalGelb.isPresent() && FarbeEnum.GELB.equals(optionalGelb.get()));

		final Optional<FarbeEnum> optionalgelb = FarbeEnum.forLabel("gelb");
		Assert.assertFalse(optionalgelb.isPresent());

		final Optional<FarbeEnum> optionalBlau = FarbeEnum.forLabel("Blau");
		Assert.assertTrue(optionalBlau.isPresent() && FarbeEnum.BLAU.equals(optionalBlau.get()));

		final Optional<FarbeEnum> optionalblau = FarbeEnum.forLabel("blau");
		Assert.assertFalse(optionalblau.isPresent());

	}
}
