package com.jdroid.javaweb.application

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class KotlinApplication {

    protected open fun initKoin() {
        startKoin {
            loadKoinModules(getKoinModules())
        }
    }

    protected open fun getKoinModules(): List<Module> = listOf()
}
