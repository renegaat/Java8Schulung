package de.msg.schulung.java8.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Übung: Generischer Gruppierer/Verwendung Function.
 */
public class Grouper <ELEM, KRIT> {

	public Map<KRIT, List<ELEM>> group(List<ELEM> elementList, Function<ELEM, KRIT> partitionner) {
		final Map<KRIT, List<ELEM>> groupingMap = new HashMap<>();

		for (ELEM elem : elementList) {
			final KRIT elemKrit = partitionner.apply(elem);
			if (!groupingMap.containsKey(elemKrit)) {
				groupingMap.put(elemKrit, new ArrayList<>());
			}
			groupingMap.get(elemKrit).add(elem);
		}

		return groupingMap;
	}
}
