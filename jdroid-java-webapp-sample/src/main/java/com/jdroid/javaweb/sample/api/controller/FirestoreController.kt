package com.jdroid.javaweb.sample.api.controller

import com.google.cloud.firestore.GeoPoint
import com.jdroid.java.collections.Lists
import com.jdroid.java.collections.Maps
import com.jdroid.java.date.DateUtils
import com.jdroid.java.http.MimeType
import com.jdroid.java.utils.RandomUtils
import com.jdroid.java.utils.TypeUtils
import com.jdroid.javaweb.api.AbstractController
import com.jdroid.javaweb.sample.firebase.SampleEntity
import com.jdroid.javaweb.sample.firebase.SampleFirestoreRepository
import com.jdroid.javaweb.sample.firebase.SampleInnerEntity

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/firestore")
class FirestoreController : AbstractController() {

    companion object {

        private var lastId: String? = null
    }

    private val repository = SampleFirestoreRepository()

    @RequestMapping(value = ["/getAll"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun getAll(@RequestParam(required = false) orderByField: String, @RequestParam(required = false) limit: String): String {
        return autoMarshall(object : SampleFirestoreRepository() {
            override fun getOrderByField(): String {
                return orderByField
            }

            override fun getLimit(): Int? {
                return TypeUtils.getInteger(limit)
            }
        }.all)
    }

    @RequestMapping(value = ["/get"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    operator fun get(@RequestParam id: String): String {
        return autoMarshall(repository.get(id))
    }

    @RequestMapping(value = ["/getByField"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun getByField(@RequestParam field: String, @RequestParam value: String): String {
        return autoMarshall(repository.getByField(field, value))
    }

    @RequestMapping(value = ["/getByFieldMultipleValues"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun getByField(@RequestParam field: String, @RequestParam value1: String, @RequestParam value2: String): String {
        return autoMarshall(repository.getByField(field, value1, value2))
    }

    @RequestMapping(value = ["/getByIds"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun getByIds(@RequestParam value1: String, @RequestParam value2: String): String {
        return autoMarshall(repository.getByIds(Lists.newArrayList(value1, value2)))
    }

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun add(@RequestParam(required = false, defaultValue = "false") autoId: Boolean, @RequestParam(required = false, defaultValue = "false") withSubcollection: Boolean): String {
        val entity = SampleEntity()
        if (!autoId) {
            entity.id = RandomUtils.getLong().toString()
        }
        entity.stringField = RandomUtils.getLong().toString()
        entity.longField = RandomUtils.getLong()
        entity.floatField = 1.12345f
        entity.timestamp = DateUtils.now()
        val stringMap = Maps.newHashMap<String, String>()
        stringMap["key1"] = "value1"
        stringMap["key2"] = "value2"
        entity.stringMap = stringMap
        val objectMap = Maps.newHashMap<String, Any>()
        objectMap["key1"] = "value1"
        objectMap["key2"] = 2
        objectMap["key3"] = GeoPoint(-12.323434, 34.34534543)
        entity.objectMap = objectMap
        entity.geoPoint = GeoPoint(-12.323434, 34.34534543)
        entity.stringArray = Lists.newArrayList("a", "b")
        if (withSubcollection) {
            entity.subCollection = Lists.newArrayList(SampleEntity(), SampleEntity())
        }

        val sampleInnerEntity = SampleInnerEntity()
        sampleInnerEntity.stringField = RandomUtils.getLong().toString()
        sampleInnerEntity.longField = RandomUtils.getLong()

        entity.composite = sampleInnerEntity

        repository.add(entity)
        lastId = entity.id

        return autoMarshall(entity)
    }

    @RequestMapping(value = ["/addAll"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun addAll(@RequestParam(required = false, defaultValue = "false") autoId: Boolean): String {

        val items = Lists.newArrayList<SampleEntity>()
        var entity = SampleEntity()
        if (!autoId) {
            entity.id = RandomUtils.getLong().toString()
        }
        entity.stringField = RandomUtils.getLong().toString()
        items.add(entity)
        entity = SampleEntity()
        if (!autoId) {
            entity.id = RandomUtils.getLong().toString()
        }
        entity.stringField = RandomUtils.getLong().toString()
        items.add(entity)

        repository.addAll(items)

        return autoMarshall(items)
    }

    @RequestMapping(value = ["/update"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun update(): String {
        val entity = SampleEntity()
        entity.id = lastId
        entity.stringField = RandomUtils.getLong().toString()
        repository.update(entity)
        return autoMarshall(entity)
    }

    @RequestMapping(value = ["/remove"], method = [RequestMethod.GET])
    fun remove(@RequestParam id: String) {
        repository.remove(id)
    }

    @RequestMapping(value = ["/removeAll"], method = [RequestMethod.GET])
    fun removeAll() {
        repository.removeAll()
        lastId = null
    }
}