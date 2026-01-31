package de.msg.schulung.java8.domainobjects;

import java.util.ArrayList;
import java.util.List;

public class AppleFactory {

	public static final int ANZAHL_HEAVY_APPLES = 10;
	
	public static final int ANZAHL_GREEN_APPLES = 7;
			
	public static List<Apple> createAppleList() {
	
		final List<Apple> appleList = new ArrayList<>();
		
		for (int i = 0; i < 20; i++) {
		
			final Apple apple = new Apple();
			
			if (i % 3 == 0) {
				apple.setColor(Apple.GREEN);
			} else if (i % 3 == 1) {
				apple.setColor(Apple.YELLOW);
			} else {
				apple.setColor(Apple.RED);
			}
			
			if (i % 2 == 0) {
				apple.setWeight(Apple.HEAVY - i);
			} else {
				apple.setWeight(Apple.HEAVY + i);
			}
			
			appleList.add(apple);
		}
		
		return appleList;
	}
}
