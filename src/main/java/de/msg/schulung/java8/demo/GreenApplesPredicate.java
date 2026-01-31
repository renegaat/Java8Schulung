package de.msg.schulung.java8.demo;

import java.util.function.Predicate;

import de.msg.schulung.java8.domainobjects.Apple;

/**
 * Übung: Konkretes Prädikat definieren.
 */
public class GreenApplesPredicate implements Predicate<Apple> {

	@Override
	public boolean test(Apple apple) {
		return apple != null && Apple.GREEN.equals(apple.getColor());
	}

}
