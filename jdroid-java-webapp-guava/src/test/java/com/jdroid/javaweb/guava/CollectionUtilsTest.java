package com.jdroid.javaweb.guava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.jdroid.java.collections.Lists;
import com.jdroid.javaweb.guava.predicate.EqualsPropertyPredicate;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link CollectionUtils}
 */
public class CollectionUtilsTest {
	
	/**
	 * @return The different cases to test
	 */
	@Test
	public void join() {
		join(",", Lists.INSTANCE.newArrayList(1, 2, 3, 4, 5), Functions.toStringFunction(), "1,2,3,4,5");
		join(", ", Lists.INSTANCE.newArrayList(1, 2, 3, 4, 5), Functions.toStringFunction(), "1, 2, 3, 4, 5");
		join(", ", Lists.INSTANCE.newArrayList(1, 2, 3, 4, 5), new Function<Object, String>() {
			
			@Override
			public String apply(Object from) {
				return "?";
			}
		}, "?, ?, ?, ?, ?");
	}
	
	/**
	 * Tests {@link CollectionUtils#join(String, Collection, Function)} method
	 * 
	 * @param separator The string separator
	 * @param objects The objects
	 * @param function The transformer function
	 * @param expected The expected string
	 */
	private void join(String separator, Collection<?> objects, Function<? super Object, String> function, String expected) {
		assertEquals(expected, CollectionUtils.join(separator, objects, function));
	}
	
	/**
	 * Tests {@link CollectionUtils#join(Collection)} method
	 */
	@Test
	public void join2() {
		assertEquals("1, 2, 3, 4, 5", CollectionUtils.join(Lists.INSTANCE.newArrayList(1, 2, 3, 4, 5)));
	}
	
	@Test
	public void findFirstMatch() {
		TestObject expected = new TestObject("other");
		findFirstMatch(Lists.INSTANCE.newArrayList(new TestObject("some"), expected), "other", expected);
		findFirstMatch(Lists.INSTANCE.newArrayList(new TestObject("some")), "other", null);
		findFirstMatch(Lists.INSTANCE.newArrayList(new TestObject("some")), null, null);
		findFirstMatch(Lists.INSTANCE.newArrayList(), "other", null);
	}
	
	/**
	 * Verifies the {@link CollectionUtils#findFirstMatch(com.google.common.base.Predicate, Collection)} method
	 * 
	 * @param objects The object to test
	 * @param value The value to filter
	 * @param expected The expected value
	 */
	private void findFirstMatch(Collection<TestObject> objects, Object value, Object expected) {
		assertEquals(expected, CollectionUtils.findFirstMatch(new EqualsPropertyPredicate<TestObject>("value", value), objects));
	}
	
	public class TestObject {
		
		private Object value;
		
		/**
		 * @param value The value
		 */
		public TestObject(Object value) {
			this.value = value;
		}
		
		/**
		 * @return The value
		 */
		public Object getValue() {
			return value;
		}
	}
}
