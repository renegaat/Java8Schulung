package de.msg.schulung.java8.demo;

import java.util.function.UnaryOperator;

public class DoubleOperator implements UnaryOperator<Integer> {
	@Override
	public Integer apply(Integer t) {
		return t == null ? null : t * 2;
	}
}
