package com.jdroid.javaweb.firebase.admin

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.jdroid.java.exception.UnexpectedException
import java.io.FileInputStream
import java.io.IOException

object FirebaseAdminSdkHelper {

    fun init(serviceAccountJsonPath: String) {
        try {
            val serviceAccountStream = FileInputStream(serviceAccountJsonPath)
            val builder = FirebaseOptions.Builder()
            builder.setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
            val options = builder.build()
            FirebaseApp.initializeApp(options)
        } catch (e: IOException) {
            throw UnexpectedException(e)
        }
    }

    fun initWithServiceAccount(serviceAccount: String) {
        try {
            val builder = FirebaseOptions.Builder()
            builder.setCredentials(GoogleCredentials.fromStream(serviceAccount.byteInputStream()))
            val options = builder.build()
            FirebaseApp.initializeApp(options)
        } catch (e: IOException) {
            throw UnexpectedException(e)
        }
    }
}
