package de.msg.schulung.java8.demo;

import java.util.function.Predicate;

import de.msg.schulung.java8.domainobjects.Gender;
import de.msg.schulung.java8.domainobjects.User;


public class FemaleUserPredicate implements Predicate<User> {

	@Override
	public boolean test(User user) {
		return user != null && Gender.FEMALE.equals(user.getGender());
	}
}
