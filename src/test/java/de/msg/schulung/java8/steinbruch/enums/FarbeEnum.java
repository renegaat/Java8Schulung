package de.msg.schulung.java8.steinbruch.enums;

import java.util.Optional;
import java.util.stream.Stream;

public enum FarbeEnum {

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

	public static Optional<FarbeEnum> forLabel(String label) {
		final Optional<FarbeEnum> farbe =
				Stream.of(FarbeEnum.values()).filter(l -> l.label.equals(label)).findFirst();

		return farbe;
	}

	public static boolean exists(String label) {
		final boolean exists =
				Stream.of(values()).anyMatch(l -> l.label.equals(label));

		return exists;
	}
}
