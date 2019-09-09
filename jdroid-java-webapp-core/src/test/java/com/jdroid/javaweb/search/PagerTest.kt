package com.jdroid.javaweb.search

import com.jdroid.java.exception.UnexpectedException
import org.junit.Assert
import org.junit.Test

class PagerTest {

    @Test(expected = UnexpectedException::class)
    fun wrongPage() {
        wrongPage(-100)
        wrongPage(0)
    }

    @Test(expected = UnexpectedException::class)
    fun wrongPageSize() {
        wrongPageSize(-100)
        wrongPageSize(0)
    }

    @Test
    fun totalPages() {
        totalPages(20, 20L, 1)
        totalPages(20, 21L, 2)
        totalPages(20, 19L, 1)
    }

    @Test
    fun offset() {
        offset(1, 20, 0)
        offset(2, 20, 20)
        offset(3, 20, 40)
    }

    private fun wrongPage(page: Int?) {
        Pager(page, 10)
    }

    private fun wrongPageSize(size: Int?) {
        Pager(1, size)
    }

    private fun totalPages(size: Int, total: Long, pages: Int) {
        val pager = Pager(1, size)
        Assert.assertEquals(pages, pager.getMaxPages(total))
    }

    private fun offset(page: Int, size: Int, offset: Int) {
        val pager = Pager(page, size)
        Assert.assertEquals(offset, pager.offset)
        Assert.assertNotNull(pager)
    }
}
