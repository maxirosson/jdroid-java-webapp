package com.jdroid.javaweb

import org.hamcrest.Description
import org.mockito.ArgumentMatcher

import java.util.Arrays

/**
 * Matcher that verify that two arrays have the same elements in no particular order.
 *
 * @param <T> The collection type
 */
class EqArrayMatcher<T>(expected: Array<T>) : ArgumentMatcher<Array<T>>() {

    private val eqCollectionMatcher: EqCollectionMatcher<T> = EqCollectionMatcher(Arrays.asList(*expected))

    override fun describeTo(description: Description) {
        eqCollectionMatcher.describeTo(description)
    }

    override fun matches(actual: Any): Boolean {
        return eqCollectionMatcher.matches(Arrays.asList(*actual as Array<T>))
    }
}
