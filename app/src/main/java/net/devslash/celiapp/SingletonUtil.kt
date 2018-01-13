package net.devslash.celiapp

import javax.inject.Inject
import javax.inject.Singleton

@Singleton class SingletonUtil @Inject constructor() {

    fun doSomething(): String {
        return "SingletonUtil: " + hashCode()
    }
}