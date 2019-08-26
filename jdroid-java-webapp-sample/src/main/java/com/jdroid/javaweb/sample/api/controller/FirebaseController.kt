package com.jdroid.javaweb.sample.api.controller

import com.firebase.security.token.TokenGenerator
import com.firebase.security.token.TokenOptions
import com.jdroid.java.http.MimeType
import com.jdroid.java.utils.RandomUtils
import com.jdroid.javaweb.api.AbstractController
import com.jdroid.javaweb.sample.firebase.SampleEntity
import com.jdroid.javaweb.sample.firebase.SampleFirebaseRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.HashMap

@Controller
@RequestMapping("/firebase")
class FirebaseController : AbstractController() {

    companion object {
        private var lastId: String? = null
    }

    private val repository = SampleFirebaseRepository()

    @RequestMapping(value = ["/getAll"], method = [RequestMethod.GET], produces = [MimeType.TEXT])
    @ResponseBody
    fun getAll(): String {
        return marshall(repository.getAll().toString())
    }

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET])
    fun add() {
        val entity = SampleEntity()
        lastId = RandomUtils.getLong().toString()
        entity.setId(lastId!!)
        entity.stringField = RandomUtils.getLong().toString()
        repository.add(entity)
    }

    @RequestMapping(value = ["/update"], method = [RequestMethod.GET])
    fun update() {
        val entity = SampleEntity()
        entity.setId(lastId!!)
        entity.stringField = RandomUtils.getLong().toString()
        repository.update(entity)
    }

    @RequestMapping(value = ["/remove"], method = [RequestMethod.GET])
    fun remove() {
        repository.remove(lastId!!)
        lastId = null
    }

    @RequestMapping(value = ["/removeAll"], method = [RequestMethod.GET])
    fun removeAll() {
        repository.removeAll()
        lastId = null
    }

    @RequestMapping(value = ["/generateToken"], method = [RequestMethod.GET], produces = [MimeType.TEXT])
    @ResponseBody
    fun generateToken(@RequestParam firebaseSecret: String): String {
        val authPayload = HashMap<String, Any>()

        val tokenOptions = TokenOptions()
        tokenOptions.isAdmin = true

        val tokenGenerator = TokenGenerator(firebaseSecret)
        return tokenGenerator.createToken(authPayload, tokenOptions)
    }
}