package com.jdroid.javaweb.domain

import com.jdroid.java.date.DateUtils
import org.junit.Assert
import org.junit.Test
import java.util.Calendar
import java.util.Date

/**
 * Test the different methods of [DateRange]
 *
 */
class DateRangeTest {

    @Test
    fun isBiggerThan() {
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                true
            )
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 9),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                true
            )
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 13),
                true
            )
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 9),
                DateUtils.getDate(2009, Calendar.JANUARY, 13),
                true
            )
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 11),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                false
            )
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 11),
                false
            )
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 11),
                DateUtils.getDate(2009, Calendar.JANUARY, 11),
                false
            )
        isBiggerThan(
                DateUtils.getDate(2009, Calendar.JANUARY, 10),
                DateUtils.getDate(2009, Calendar.JANUARY, 12),
                DateUtils.getDate(2009, Calendar.JANUARY, 8),
                DateUtils.getDate(2009, Calendar.JANUARY, 9),
                false
            )
    }

    /**
     * @param startDate The start date
     * @param endDate The end date
     * @param theStartDate The start date to compare
     * @param theEndDate The end date to compare
     * @param expectedResult The expected result
     */
    private fun isBiggerThan(startDate: Date, endDate: Date, theStartDate: Date, theEndDate: Date, expectedResult: Boolean) {
        val dateRange = DateRange(startDate, endDate)
        Assert.assertEquals(expectedResult, dateRange.isContainedInPeriod(theStartDate, theEndDate))
    }

    // /**
    //  * Data provider for the [DateRangeTest.overlapsTest] method.
    //  *
    //  * @return [Iterator] Contains the test cases.
    //  */
    // @DataProvider
    // fun overlapsDataProvider(): Iterator<Array<Any>> {
    //     val cases = Lists.newArrayList<Array<Any>>()
    //     val firstOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 1)
    //     val tenthOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 10)
    //     val twentiethOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 20)
    //     val lastOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 31)
    //
    //     /**
    //      * Case 1:<br></br>
    //      * |----|<br></br>
    //      * |----|
    //      */
    //     var dateRange = DateRange(firstOfJan, lastOfJan)
    //     var dateRangeToCompare = DateRange(firstOfJan, lastOfJan)
    //     var expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 2:<br></br>
    //      * |----|---<br></br>
    //      * ---|----|
    //      */
    //     dateRange = DateRange(firstOfJan, twentiethOfJan)
    //     dateRangeToCompare = DateRange(tenthOfJan, lastOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 3:<br></br>
    //      * ---|----|<br></br>
    //      * |----|---
    //      */
    //     dateRange = DateRange(tenthOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange(firstOfJan, twentiethOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 4:<br></br>
    //      * |----|<br></br>
    //      * ---|-|
    //      */
    //     dateRange = DateRange(firstOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange(twentiethOfJan, lastOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 5:<br></br>
    //      * |----|<br></br>
    //      * |-|---
    //      */
    //     dateRange = DateRange(firstOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange(firstOfJan, tenthOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 6:<br></br>
    //      * |----|<br></br>
    //      * -|--|-
    //      */
    //     dateRange = DateRange(firstOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange(tenthOfJan, twentiethOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 7:<br></br>
    //      * |----|--<br></br>
    //      * |------|
    //      */
    //     dateRange = DateRange(firstOfJan, twentiethOfJan)
    //     dateRangeToCompare = DateRange(firstOfJan, lastOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 8:<br></br>
    //      * --|----|<br></br>
    //      * |------|
    //      */
    //     dateRange = DateRange(tenthOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange(firstOfJan, lastOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 9:<br></br>
    //      * -|--|-<br></br>
    //      * |----|
    //      */
    //     dateRange = DateRange(tenthOfJan, twentiethOfJan)
    //     dateRangeToCompare = DateRange(firstOfJan, lastOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 10:<br></br>
    //      * |----|------<br></br>
    //      * ------|----|
    //      */
    //     dateRange = DateRange(firstOfJan, tenthOfJan)
    //     dateRangeToCompare = DateRange(twentiethOfJan, lastOfJan)
    //     expectedResult = false
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 11:<br></br>
    //      * ------|----|<br></br>
    //      * |----|------
    //      */
    //     dateRange = DateRange(twentiethOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange(firstOfJan, tenthOfJan)
    //     expectedResult = false
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 12:<br></br>
    //      * -----|----|<br></br>
    //      * |----|-----
    //      */
    //     dateRange = DateRange(tenthOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange(firstOfJan, tenthOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 13:<br></br>
    //      * |----|-----<br></br>
    //      * -----|----|
    //      */
    //     dateRange = DateRange(firstOfJan, tenthOfJan)
    //     dateRangeToCompare = DateRange(tenthOfJan, lastOfJan)
    //     expectedResult = true
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     /**
    //      * Case 14:<br></br>
    //      * ----|--><br></br>
    //      * <--|----
    //      */
    //     dateRange = DateRange(twentiethOfJan, null)
    //     dateRangeToCompare = DateRange(null, tenthOfJan)
    //     expectedResult = false
    //     cases.add(arrayOf(dateRange, dateRangeToCompare, expectedResult))
    //
    //     return cases.iterator()
    // }

    /**
     * Test method for the [DateRange.overlaps] method.<br></br>
     * Data provided by the [DateRangeTest.overlapsDataProvider] method.
     *
     * @param dateRange The [DateRange] to be compared.
     * @param dateRangeToCompare The [DateRange] with which the first one is compared.
     * @param expectedResult The expected result of the method.
     */
    // FIXME fix null cases
    // 	@Test(dataProvider = "overlapsDataProvider")
    // 	public void overlapsTest(DateRange dateRange, DateRange dateRangeToCompare, boolean expectedResult) {
    // 		boolean result = dateRange.overlaps(dateRangeToCompare);
    // 		Assert.assertEquals(result, expectedResult);
    // 	}

    // /**
    //  * Data provider for the [DateRangeTest.overlapsExceptionTest] method.
    //  *
    //  * @return [Iterator] Contains the test cases.
    //  */
    // @DataProvider
    // fun overlapsExceptionDataProvider(): Iterator<Array<Any>> {
    //     val cases = mutableListOf<Array<Any>>()
    //     val firstOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 1)
    //     val tenthOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 10)
    //     val twentiethOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 20)
    //     val lastOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 31)
    //
    //     /**
    //      * Case 2:<br></br>
    //      * <-------><br></br>
    //      * --|----|-
    //      */
    //     var dateRange = DateRange()
    //     var dateRangeToCompare = DateRange(tenthOfJan, twentiethOfJan)
    //     cases.add(arrayOf(dateRange, dateRangeToCompare))
    //
    //     /**
    //      * Case 5:<br></br>
    //      * --|----|-<br></br>
    //      * <------->
    //      */
    //     dateRange = DateRange(firstOfJan, lastOfJan)
    //     dateRangeToCompare = DateRange()
    //     cases.add(arrayOf(dateRange, dateRangeToCompare))
    //
    //     /**
    //      * Case 7:<br></br>
    //      * <------><br></br>
    //      * <------>
    //      */
    //     dateRange = DateRange()
    //     dateRangeToCompare = DateRange()
    //     cases.add(arrayOf(dateRange, dateRangeToCompare))
    //
    //     return cases.iterator()
    // }

    /**
     * Test method for the [DateRange.overlaps] method.<br></br>
     * Tests the exception cases.<br></br>
     * Data provided by the [DateRangeTest.overlapsExceptionDataProvider] method.
     *
     * @param dateRange The [DateRange] to be compared.
     * @param dateRangeToCompare The [DateRange] with which the first one is compared.
     */
    // FIXME Fix null cases
    // 	@Test(dataProvider = "overlapsExceptionDataProvider", expectedExceptions = NullPointerException.class)
    // 	public void overlapsExceptionTest(DateRange dateRange, DateRange dateRangeToCompare) {
    // 		dateRange.overlaps(dateRangeToCompare);
    // 	}

    @Test
    fun intersectionTest() {
        val firstOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 1)
        val tenthOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 10)
        val twentiethOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 10)
        val lastOfJan = DateUtils.getDate(2010, Calendar.JANUARY, 31)

        /**
         * Case 1:<br></br>
         * |--LIMIT--|<br></br>
         * --|RANGE|--
         */
        var limit = DateRange(firstOfJan, lastOfJan)
        var toTruncate = DateRange(tenthOfJan, twentiethOfJan)
        var expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 2:<br></br>
         * |-LIMIT-|<br></br>
         * --|RANGE|
         */
        limit = DateRange(firstOfJan, twentiethOfJan)
        toTruncate = DateRange(tenthOfJan, twentiethOfJan)
        expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 3:<br></br>
         * |--LIMIT--|<br></br>
         * |RANGE|----
         */
        limit = DateRange(tenthOfJan, lastOfJan)
        toTruncate = DateRange(tenthOfJan, twentiethOfJan)
        expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 4:<br></br>
         * |LIMIT|<br></br>
         * |RANGE|
         */
        limit = DateRange(firstOfJan, lastOfJan)
        toTruncate = DateRange(firstOfJan, lastOfJan)
        expectedResult = DateRange(firstOfJan, lastOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 5:<br></br>
         * --|LIMIT|--<br></br>
         * |--RANGE--|
         */
        limit = DateRange(tenthOfJan, twentiethOfJan)
        toTruncate = DateRange(firstOfJan, lastOfJan)
        expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 6:<br></br>
         * --|LIMIT|<br></br>
         * |--RANGE|
         */
        limit = DateRange(tenthOfJan, twentiethOfJan)
        toTruncate = DateRange(firstOfJan, twentiethOfJan)
        expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 7:<br></br>
         * |LIMIT|--<br></br>
         * |RANGE--|
         */
        limit = DateRange(tenthOfJan, twentiethOfJan)
        toTruncate = DateRange(tenthOfJan, lastOfJan)
        expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 8:<br></br>
         * |LIMIT|--<br></br>
         * --|RANGE|
         */
        limit = DateRange(firstOfJan, twentiethOfJan)
        toTruncate = DateRange(tenthOfJan, lastOfJan)
        expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 9:<br></br>
         * --|LIMIT|<br></br>
         * |RANGE|--
         */
        limit = DateRange(tenthOfJan, lastOfJan)
        toTruncate = DateRange(firstOfJan, twentiethOfJan)
        expectedResult = DateRange(tenthOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 10:<br></br>
         * ------|LIMIT|<br></br>
         * |RANGE|------
         */
        limit = DateRange(tenthOfJan, lastOfJan)
        toTruncate = DateRange(firstOfJan, tenthOfJan)
        expectedResult = DateRange(tenthOfJan, tenthOfJan)
        intersectionTest(toTruncate, limit, expectedResult)

        /**
         * Case 11:<br></br>
         * |LIMIT|------<br></br>
         * ------|RANGE|
         */
        limit = DateRange(firstOfJan, twentiethOfJan)
        toTruncate = DateRange(twentiethOfJan, lastOfJan)
        expectedResult = DateRange(twentiethOfJan, twentiethOfJan)
        intersectionTest(toTruncate, limit, expectedResult)
    }

    /**
     * Test method for the [DateRange.intersection] method.<br></br>
     * Data provided by the [DateRangeTest.intersectionDataProvider] method.
     *
     * @param toTruncate The [DateRange] to be truncated.
     * @param limit The [DateRange] that defines the boundary by which the [DateRange] to be truncated must
     * be truncated.
     * @param expectedResult The expected result.
     */
    private fun intersectionTest(toTruncate: DateRange, limit: DateRange, expectedResult: DateRange) {
        val result = toTruncate.intersection(limit)
        Assert.assertEquals(expectedResult, result)
        Assert.assertFalse(result === toTruncate)
        Assert.assertFalse(result === limit)
    }

    /**
     * Test method for the [DateRange.intersection] method.<br></br>
     * Tests the exception cases.
     */
    @Test(expected = IllegalArgumentException::class)
    fun intersectionExceptionTest() {

        // The only case that this happens is when the date ranges don't overlap.
        val toTruncate = DateRange(
            DateUtils.getDate(2010, Calendar.JANUARY, 1), DateUtils.getDate(
                2010,
                Calendar.JANUARY, 31
            )
        )
        val limit = DateRange(
            DateUtils.getDate(2010, Calendar.FEBRUARY, 1), DateUtils.getDate(
                2010,
                Calendar.FEBRUARY, 28
            )
        )
        toTruncate.intersection(limit)
    }
}
