package com.jdroid.javaweb

import com.jdroid.java.collections.Iterables
import com.jdroid.java.collections.Lists

import org.hamcrest.Description
import org.mockito.ArgumentMatcher

/**
 * Matcher that verify that two collections have the same elements in no particular order.
 *
 * @param <T> The collection type
 */
class EqCollectionMatcher<T>(private val expected: Collection<T>) : ArgumentMatcher<List<T>>() {

    override fun describeTo(description: Description) {
        description.appendText(expected.toString())
    }

    override fun matches(actual: Any): Boolean {

        val actualList = actual as List<T>
        if (Iterables.size(expected) == Iterables.size(actualList)) {
            // Create a list based on the expected results.
            val expectedList = Lists.newArrayList(expected)

            // Iterate over the obtained results and check if the expected list
            // contains the item. If the item is contained within the expected list
            // then it is removed from it so that repeated items can be checked.
            for (o in actualList) {
                if (expectedList.contains(o)) {
                    expectedList.remove(o)
                } else {
                    return false
                }
            }
            return true
        }
        return false
    }
}
