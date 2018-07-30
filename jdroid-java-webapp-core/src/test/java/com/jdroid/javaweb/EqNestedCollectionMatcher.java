package com.jdroid.javaweb;

import com.jdroid.java.exception.UnexpectedException;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;

import java.util.Collection;
import java.util.List;

/**
 * Matcher that verify that the nested collection and the expected collection have the same elements in no particular
 * order.
 * 
 * @param <T> The type of argument
 * @param <S> The nested collection type
 */
public class EqNestedCollectionMatcher<T, S> extends ArgumentMatcher<T> {
	
	private Matcher<List<S>> matcher;
	private String propertyName;
	
	/**
	 * @param propertyName The nested property name to obtain the collection used to match
	 * @param expected The list of arguments to match.
	 */
	public EqNestedCollectionMatcher(String propertyName, Collection<S> expected) {
		this.matcher = new EqCollectionMatcher<>(expected);
		this.propertyName = propertyName;
	}
	
	/**
	 * @see org.mockito.ArgumentMatcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object argument) {
		return matcher.matches(getPropertyValue(argument, propertyName));
	}
	
	/**
	 * @param <T> The type of the result object
	 * @param from The object where invoke the property
	 * @param propertyName The name of the property to invoke
	 * @return The property value
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getPropertyValue(Object from, String propertyName) {
		try {
			return (T)PropertyUtils.getNestedProperty(from, propertyName);
		} catch (NestedNullException exception) {
			// if any the nested objects is null we return null as value
			return null;
		} catch (Exception ex) {
			throw new UnexpectedException("Error getting the property: '" + propertyName + "' of: " + from);
		}
	}
}
