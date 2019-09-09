package com.jdroid.javaweb.guava.predicate;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Test {@link ContainsPropertyPredicate}
 * 
 */
public class ContainsPropertyPredicateTest {
	
	@Test
	public void apply() {
		apply(new TestObject(), "enums", null, false);
		apply(new TestObject(), "enums", TestEnum.TEST_1, false);
		apply(new TestObject(Lists.newArrayList(TestEnum.TEST_1)), "enums", null, false);
		apply(new TestObject(Lists.newArrayList(TestEnum.TEST_1)), "enums", TestEnum.TEST_2, false);
		apply(new TestObject(Lists.newArrayList(TestEnum.TEST_1)), "enums", TestEnum.TEST_1, true);
		apply(new TestObject(Lists.newArrayList(TestEnum.TEST_1, TestEnum.TEST_2)), "enums",
				TestEnum.TEST_1, true);
		apply(new TestObject(Lists.newArrayList(TestEnum.TEST_1, TestEnum.TEST_2, TestEnum.TEST_3)),
				"enums", TestEnum.TEST_1, true);
		apply(new TestObject(), "testEnum", null, true);
		apply(new TestObject(), "testEnum", Lists.newArrayList(TestEnum.TEST_1), false);
		apply(new TestObject(TestEnum.TEST_1), "testEnum", null, false);
		apply(new TestObject(TestEnum.TEST_1), "testEnum", Lists.newArrayList(TestEnum.TEST_2),
				false);
		apply(new TestObject(TestEnum.TEST_1), "testEnum", Lists.newArrayList(TestEnum.TEST_1), true);
		apply(new TestObject(TestEnum.TEST_1), "testEnum",
				Lists.newArrayList(TestEnum.TEST_1, TestEnum.TEST_2), true);
		apply(new TestObject(TestEnum.TEST_1), "testEnum",
				Lists.newArrayList(TestEnum.TEST_1, TestEnum.TEST_2, TestEnum.TEST_3), true);
	}
	
	/**
	 * Verifies {@link ContainsPropertyPredicate#apply(Object)} method
	 * 
	 * @param input The input
	 * @param propertyName The propertyName
	 * @param value The value
	 * @param expected The expected value
	 */
	private void apply(TestObject input, String propertyName, Object value, boolean expected) {
		ContainsPropertyPredicate<TestObject> predicate = new ContainsPropertyPredicate<>(propertyName, value);
		Assert.assertEquals(expected, predicate.apply(input));
	}
	
	private enum TestEnum {
		TEST_1,
		TEST_2,
		TEST_3
	}
	
	public class TestObject {
		
		private TestEnum testEnum;
		private Collection<TestEnum> enums = Lists.newArrayList();
		
		/**
		 * Default constructor
		 */
		public TestObject() {
			// nothing
		}
		
		/**
		 * @param enums The {@link TestEnum}s
		 */
		public TestObject(Collection<TestEnum> enums) {
			this.enums = enums;
		}
		
		/**
		 * @param testEnum The {@link TestEnum}
		 */
		public TestObject(TestEnum testEnum) {
			this.testEnum = testEnum;
		}
		
		/**
		 * @return The {@link TestEnum}
		 */
		public TestEnum getTestEnum() {
			return testEnum;
		}
		
		/**
		 * @return The enums
		 */
		public Collection<TestEnum> getEnums() {
			return enums;
		}
	}
}
