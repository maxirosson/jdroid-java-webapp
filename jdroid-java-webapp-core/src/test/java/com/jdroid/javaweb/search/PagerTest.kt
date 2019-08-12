package com.jdroid.javaweb.search

import com.jdroid.java.exception.UnexpectedException
import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class PagerTest {

    @DataProvider(name = "wrongPageScenarios")
    protected fun wrongPageScenarios(): Iterator<Array<Any>> {
        val scenarios = mutableListOf<Array<Any>>()
        scenarios.add(arrayOf(-100))
        scenarios.add(arrayOf(0))
        return scenarios.iterator()
    }

    @DataProvider(name = "wrongPageSizeScenarios")
    protected fun wrongPageSizeScenarios(): Iterator<Array<Any>> {
        val scenarios = mutableListOf<Array<Any>>()
        scenarios.add(arrayOf(-100))
        scenarios.add(arrayOf(0))
        return scenarios.iterator()
    }

    @DataProvider(name = "totalPagesScenarios")
    protected fun totalPagesScenarios(): Iterator<Array<Any>> {
        val scenarios = mutableListOf<Array<Any>>()
        scenarios.add(arrayOf(20, 20L, 1))
        scenarios.add(arrayOf(20, 21L, 2))
        scenarios.add(arrayOf(20, 19L, 1))
        return scenarios.iterator()
    }

    @DataProvider(name = "offsetScenarios")
    protected fun offsetScenarios(): Iterator<Array<Any>> {
        val scenarios = mutableListOf<Array<Any>>()
        scenarios.add(arrayOf(1, 20, 0))
        scenarios.add(arrayOf(2, 20, 20))
        scenarios.add(arrayOf(3, 20, 40))
        return scenarios.iterator()
    }

    @Test(dataProvider = "wrongPageScenarios", expectedExceptions = [UnexpectedException::class])
    fun wrongPage(page: Int?) {
        Pager(page, 10)
    }

    @Test(dataProvider = "wrongPageSizeScenarios", expectedExceptions = [UnexpectedException::class])
    fun wrongPageSize(size: Int?) {
        Pager(1, size)
    }

    @Test(dataProvider = "totalPagesScenarios")
    fun totalPages(size: Int?, total: Long?, pages: Int?) {
        val pager = Pager(1, size)
        Assert.assertEquals(pager.getMaxPages(total), pages)
    }

    @Test(dataProvider = "offsetScenarios")
    fun offset(page: Int?, size: Int?, offset: Int?) {
        val pager = Pager(page, size)
        Assert.assertEquals(pager.offset, offset)
        Assert.assertNotNull(pager)
    }
}
