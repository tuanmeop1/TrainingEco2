package com.example.trainingeco2

import com.example.trainingeco2.app.LibraryApp
import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.manager.LibraryManager
import com.example.trainingeco2.manager.LoginManager
import kotlinx.coroutines.runBlocking

fun main() {
    val libraryData = LibraryData()
    val libraryManager = LibraryManager(libraryData)
    val loginManager = LoginManager(libraryData)

    val app = LibraryApp(loginManager, libraryManager, libraryData)

    runBlocking {
        app.run()
    }
}
