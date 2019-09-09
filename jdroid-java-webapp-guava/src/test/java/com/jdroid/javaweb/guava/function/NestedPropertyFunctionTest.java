package com.jdroid.javaweb.guava.function;

import com.jdroid.java.exception.UnexpectedException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link PropertyFunction} class
 * 
 */
public class NestedPropertyFunctionTest {
	
	@Test
	public void apply() {
		TestObject object1 = new TestObject();
		TestObject parentObject1 = new TestObject(object1);
		apply(parentObject1, "nested", object1);
		apply(parentObject1, "value", null);
		apply(parentObject1, "nested.value", null);
		apply(parentObject1, "nested.nested", null);
		apply(parentObject1, "nested.nested.value", null);
		
		TestObject object2 = new TestObject("SomeValue");
		TestObject parentObject2 = new TestObject(object2);
		apply(parentObject2, "nested", object2);
		apply(parentObject2, "value", null);
		apply(parentObject2, "nested.value", "SomeValue");
		apply(parentObject2, "nested.nested", null);
		apply(parentObject2, "nested.nested.value", null);
	}
	
	/**
	 * Test the {@link PropertyFunction#apply(Object)} method
	 * 
	 * @param testObject The object to test
	 * @param property The property to look for
	 * @param expectedResponse The expected response
	 */
	private void apply(TestObject testObject, String property, Object expectedResponse) {
		NestedPropertyFunction<TestObject, Object> function = new NestedPropertyFunction<>(property);
		Assert.assertEquals(expectedResponse, function.apply(testObject));
	}
	
	@Test(expected = UnexpectedException.class)
	public void applyNegative() {
		TestObject object = new TestObject();
		TestObject parentObject = new TestObject(object);
		applyNegative(parentObject, "nested.anInexistentProperty");
		applyNegative(parentObject, "anInexistentProperty");
	}
	
	/**
	 * Test the negative {@link PropertyFunction#apply(Object)} method
	 * 
	 * @param testObject The object to test
	 * @param property The property to look for
	 */
	private void applyNegative(TestObject testObject, String property) {
		NestedPropertyFunction<TestObject, Object> function = new NestedPropertyFunction<>(property);
		function.apply(testObject);
	}
	
	/**
	 * Test object
	 * 
	 */
	public class TestObject {
		
		private TestObject nested;
		private Object value;
		
		/**
		 * Default constructor
		 */
		public TestObject() {
			this(null);
		}
		
		/**
		 * @param nested The nested value
		 */
		public TestObject(TestObject nested) {
			this.nested = nested;
		}
		
		/**
		 * @param value The value
		 */
		public TestObject(Object value) {
			this.value = value;
		}
		
		/**
		 * @return The nested value
		 */
		public TestObject getNested() {
			return nested;
		}
		
		/**
		 * @return The value
		 */
		public Object getValue() {
			return value;
		}
	}
}
