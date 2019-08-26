package com.jdroid.javaweb.sample.config

import com.jdroid.java.firebase.database.PairFirebaseRepository
import com.jdroid.java.firebase.database.auth.CustomTokenFirebaseAuthenticationStrategy
import com.jdroid.java.firebase.database.auth.FirebaseAuthenticationStrategy
import com.jdroid.javaweb.sample.context.ServerApplication

class ConfigFirebaseRepository : PairFirebaseRepository() {

    override val firebaseUrl: String = ServerApplication.get().firebaseUrl

    override val path: String = "config"

    override fun createFirebaseAuthenticationStrategy(): FirebaseAuthenticationStrategy? {
        return object : CustomTokenFirebaseAuthenticationStrategy() {
            override val authToken: String
                get() = ServerApplication.get().firebaseAuthToken
        }
    }
}
