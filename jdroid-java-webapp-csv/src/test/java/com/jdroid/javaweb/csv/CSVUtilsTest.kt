package com.jdroid.javaweb.csv

import com.jdroid.java.exception.UnexpectedException
import com.jdroid.javaweb.Assert
import org.testng.annotations.Test
import java.io.File
import java.io.IOException
import java.io.StringReader

/**
 * Tests the [CSVUtils] class
 *
 */
class CSVUtilsTest {

    /**
     * Tests the [CSVUtils.fromCSV] method
     */
    @Test
    fun fromCSV() {
        val csv = "1,2,3,4\n,5,\n6,\t7\t,8"
        val expected = listOf("1", "2", "3", "4", "5", "6", "7", "8")
        Assert.assertEqualsNoOrder(CSVUtils.fromCSV(csv), expected)
    }

    /**
     * Tests the [CSVUtils.fromCSV] method
     */
    @Test
    fun fromCSVInputStream() {
        val inputStream = this.javaClass.classLoader.getResourceAsStream("files/csv_file.csv")
        val expected = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
        Assert.assertEqualsNoOrder(CSVUtils.fromCSV(inputStream), expected)
    }

    /**
     * Tests the negative case of [CSVUtils.fromCSV] method
     */
    @Test(expectedExceptions = [UnexpectedException::class])
    fun fromCSVFileNegative() {
        val file = File("someFile.csv")
        CSVUtils.fromCSV(file)
    }

    /**
     * Tests the negative case of [CSVUtils.fromCSV] method
     *
     * @throws IOException Config exception
     */
    @Test(expectedExceptions = [UnexpectedException::class])
    @Throws(IOException::class)
    fun fromCSVReaderNegative() {
        val reader = StringReader("")
        reader.close()
        CSVUtils.fromCSV(reader)
    }

    /**
     * Tests the [CSVUtils.toCSV] method
     */
    @Test
    fun toCSV() {
        val values = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
        val expected = "1,2,3,4,5,6,7,8,9,10,11"
        org.testng.Assert.assertEquals(CSVUtils.toCSV(values), expected)
    }

    /**
     * Tests the [CSVUtils.toCSVFile] method
     */
    @Test
    fun toCSVFile() {
        val values = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
        val file = CSVUtils.toCSVFile(values)
        org.testng.Assert.assertEquals(CSVUtils.fromCSV(file), values)
    }

    /**
     * Tests the [CSVUtils.toMultipleColumnCSVFile] method
     */
    @Test
    fun toMultipleColumnCSVFile() {

        val values = listOf(arrayOf("1", "a"), arrayOf("2", "b"), arrayOf("3", "c"))
        val csvFile = CSVUtils.toMultipleColumnCSVFile(values)
        org.testng.Assert.assertEquals(CSVUtils.fromCSV(csvFile), listOf("1", "a", "2", "b", "3", "c"))
    }
}
