package com.jdroid.javaweb

import com.jdroid.java.exception.UnexpectedException

import org.apache.commons.beanutils.NestedNullException
import org.apache.commons.beanutils.PropertyUtils
import org.hamcrest.Matcher
import org.mockito.ArgumentMatcher

/**
 * Matcher that verify that the nested collection and the expected collection have the same elements in no particular
 * order.
 *
 * @param <T> The type of argument
 * @param <S> The nested collection type
 */
class EqNestedCollectionMatcher<T, S>(
    /**
     * @param propertyName The nested property name to obtain the collection used to match
     * @param expected The list of arguments to match.
     */
    private val propertyName: String,
    expected: Collection<S>
) : ArgumentMatcher<T>() {

    private val matcher: Matcher<List<S>>

    init {
        this.matcher = EqCollectionMatcher(expected)
    }

    override fun matches(argument: Any): Boolean {
        return matcher.matches(getPropertyValue(argument, propertyName))
    }

    /**
     * @param <T> The type of the result object
     * @param from The object where invoke the property
     * @param propertyName The name of the property to invoke
     * @return The property value
    </T> */
    private fun <T> getPropertyValue(from: Any, propertyName: String): T? {
        try {
            return PropertyUtils.getNestedProperty(from, propertyName) as T
        } catch (exception: NestedNullException) {
            // if any the nested objects is null we return null as value
            return null
        } catch (ex: Exception) {
            throw UnexpectedException("Error getting the property: '$propertyName' of: $from")
        }
    }
}
