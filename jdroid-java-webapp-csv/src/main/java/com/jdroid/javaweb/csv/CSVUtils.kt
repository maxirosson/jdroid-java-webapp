package com.jdroid.javaweb.csv

import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVWriter
import com.jdroid.java.date.DateUtils
import com.jdroid.java.exception.UnexpectedException
import com.jdroid.java.utils.StringUtils
import com.jdroid.java.utils.TypeUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Reader
import java.io.StringReader
import java.io.StringWriter
import java.io.Writer

/**
 * Utilities for CSV
 */
object CSVUtils {

    interface ValueConverter<T> {

        fun fromString(value: String): T

        fun toArray(values: Collection<T>): Array<String>
    }

    class StringConverter : ValueConverter<String> {

        override fun fromString(value: String): String {
            return value
        }

        override fun toArray(values: Collection<String>): Array<String> {
            return values.toTypedArray()
        }

        companion object {

            private val INSTANCE = StringConverter()

            fun get(): StringConverter {
                return INSTANCE
            }
        }
    }

    class LongConverter : ValueConverter<Long> {

        override fun fromString(value: String): Long {
            return TypeUtils.getLong(value)
        }

        override fun toArray(values: Collection<Long>): Array<String> {
            val stringsValues = mutableListOf<String>()
            for (each in values) {
                stringsValues.add(each.toString())
            }
            return stringsValues.toTypedArray()
        }

        companion object {

            private val INSTANCE = LongConverter()

            fun get(): LongConverter {
                return INSTANCE
            }
        }
    }

    /**
     * Reads a csv file and returns a list of T with the values
     *
     * @param csv A comma separated value string
     * @param valueConverter A [ValueConverter]
     * @param <T> A [ValueConverter]
     * @return A list with all the values into the file
    </T> */
    fun <T> fromCSV(csv: String, valueConverter: ValueConverter<T>): List<T> {
        return if (StringUtils.isNotEmpty(csv))
            fromCSV(
                StringReader(csv),
                valueConverter
            )
        else listOf()
    }

    /**
     * Reads a csv file and returns a list of string with the values
     *
     * @param csv A comma separated value string
     * @return A list with all the values into the file
     */
    fun fromCSV(csv: String): List<String> {
        return fromCSV(csv, StringConverter.get())
    }

    /**
     * Reads a csv file and returns a list of string with the values
     *
     * @param csvFile The csv file
     * @return A list with all the values into the file
     */
    fun fromCSV(csvFile: File): List<String> {
        try {
            return fromCSV(FileInputStream(csvFile))
        } catch (exception: IOException) {
            throw UnexpectedException("Error reading the file: $csvFile", exception)
        }
    }

    /**
     * Reads a csv file and returns a list of string with the values
     *
     * @param inputStream The input stream with the csv values
     * @return A list with all the values into the file
     */
    fun fromCSV(inputStream: InputStream): List<String> {
        return fromCSV(InputStreamReader(inputStream))
    }

    /**
     * Reads a csv and returns a list of string with the values
     *
     * @param reader The csv reader
     * @return A list with all the values given by the reader
     */
    fun fromCSV(reader: Reader): List<String> {
        return fromCSV(reader, StringConverter.get())
    }

    fun <T> fromCSV(reader: Reader, valueConverter: ValueConverter<T>): List<T> {
        try {
            val result = mutableListOf<T>()
            val csvReader = CSVReader(reader)
            try {
                for (values in csvReader.readAll()) {
                    for (value in values) {
                        val trimmedValue = value.trim()
                        if (StringUtils.isNotEmpty(trimmedValue)!!) {
                            result.add(valueConverter.fromString(trimmedValue))
                        }
                    }
                }
                return result
            } finally {
                csvReader.close()
            }
        } catch (exception: IOException) {
            throw UnexpectedException("Error reading the csv", exception)
        }
    }

    fun toCSVFromToString(values: Collection<*>): String? {
        val stringValues = mutableListOf<String>()
        for (value in values) {
            stringValues.add(value.toString())
        }
        return toCSV(stringValues)
    }

    fun <T> toCSV(values: Collection<T>?, valueConverter: ValueConverter<T>, separator: Char): String? {
        if (values != null) {
            val writer = StringWriter()
            writeCSV(writer, values, valueConverter, separator)
            return writer.toString()
        } else {
            return null
        }
    }

    /**
     * @param <T>
     * @param values A collection with the values
     * @param valueConverter A [ValueConverter]
     * @return A string with the comma separated values
    </T> */
    fun <T> toCSV(values: Collection<T>, valueConverter: ValueConverter<T>): String? {
        return toCSV(values, valueConverter, CSVWriter.DEFAULT_SEPARATOR)
    }

    fun toCSV(values: Collection<String>): String? {
        return toCSV(values, StringConverter.get())
    }

    fun toCSV(values: Collection<String>, separator: Char): String? {
        return toCSV(values, StringConverter.get(), separator)
    }

    /**
     * @param values A collection with the values
     * @return csv file
     */
    fun toCSVFile(values: Collection<String>): File {
        try {
            val fileName = "csv_file" + DateUtils.now().time
            val file = File.createTempFile(fileName, ".csv")
            writeCSV(FileOutputStream(file), values)
            return file
        } catch (exception: IOException) {
            throw UnexpectedException("Error creating a temporal file to write the csv values", exception)
        }
    }

    /**
     * @param outputStream The output stream where the csv will be written
     * @param values A collection with the values
     */
    fun writeCSV(outputStream: OutputStream, values: Collection<String>) {
        writeCSV(OutputStreamWriter(outputStream), values)
    }

    fun <T> writeCSV(writer: Writer, values: Collection<T>, valueConverter: ValueConverter<T>) {
        writeCSV(writer, values, valueConverter, CSVWriter.DEFAULT_SEPARATOR)
    }

    /**
     * @param <T>
     * @param writer The writer where the csv will be written
     * @param values A collection with the values
     * @param valueConverter A [ValueConverter]
     * @param separator the delimiter to use for separating entries
    </T> */
    fun <T> writeCSV(
        writer: Writer,
        values: Collection<T>,
        valueConverter: ValueConverter<T>,
        separator: Char
    ) {
        try {
            val csvWriter = CSVWriter(writer, separator, CSVWriter.NO_QUOTE_CHARACTER, StringUtils.EMPTY)
            csvWriter.writeNext(valueConverter.toArray(values))
            csvWriter.close()
        } catch (exception: IOException) {
            throw UnexpectedException("Error writing the values", exception)
        }
    }

    fun writeCSV(writer: Writer, values: Collection<String>) {
        CSVUtils.writeCSV(writer, values, StringConverter.get())
    }

    /**
     * @param writer The writer where the csv will be written
     * @param values A collection with the values
     */
    fun writeCSVRow(writer: Writer, values: Collection<String>) {
        try {
            // all the values will be quoted
            val csvWriter = CSVWriter(writer)
            csvWriter.writeNext(values.toTypedArray())
            csvWriter.close()
        } catch (exception: IOException) {
            throw UnexpectedException("Error writing the values", exception)
        }
    }

    /**
     * @param outputStream The output stream where the csv will be written
     * @param values A List of String[], with each String[] representing a line of the file.
     */
    fun writeMultipleColumnCSV(outputStream: OutputStream, values: List<Array<String>>) {
        CSVUtils.writeMultipleColumnCSV(OutputStreamWriter(outputStream), values)
    }

    /**
     * @param values A List of String[], with each String[] representing a line of the file.
     * @return csv file
     */
    fun toMultipleColumnCSVFile(values: List<Array<String>>): File {
        try {
            val fileName = "csv_file" + DateUtils.now().time
            val file = File.createTempFile(fileName, ".csv")
            writeMultipleColumnCSV(FileOutputStream(file), values)
            return file
        } catch (exception: IOException) {
            throw UnexpectedException("Error creating a temporal file to write the csv values", exception)
        }
    }

    /**
     * @param writer The writer where the csv will be written
     * @param values A List of String[], with each String[] representing a line of the file.
     */
    fun writeMultipleColumnCSV(writer: Writer, values: List<Array<String>>) {
        try {
            val csvWriter = CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER)
            csvWriter.writeAll(values)
            csvWriter.close()
        } catch (exception: IOException) {
            throw UnexpectedException("Error writing the values", exception)
        }
    }
}
