package de.msg.schulung.java8.steinbruch.enums;

import org.junit.Assert;
import org.junit.Test;

public class FarbeTest {

	@Test
	public void testFarbenExistieren() {

		Assert.assertTrue(FarbeEnum.forLabel("Blau").isPresent());
		Assert.assertTrue(FarbeEnum.forLabel("Rot").isPresent());
		Assert.assertTrue(FarbeEnum.forLabel("Gelb").isPresent());
		Assert.assertTrue(FarbeEnum.forLabel("Grün").isPresent());
	}

	@Test
	public void testFarbenExistierenNicht() {

		Assert.assertFalse(FarbeEnum.forLabel("Grau").isPresent());
		Assert.assertFalse(FarbeEnum.forLabel("Schwarz").isPresent());
		Assert.assertFalse(FarbeEnum.forLabel("Orange").isPresent());
		Assert.assertFalse(FarbeEnum.forLabel("Weiü").isPresent());
	}
}
