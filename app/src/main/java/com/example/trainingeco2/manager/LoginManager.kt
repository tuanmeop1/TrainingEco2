package com.example.trainingeco2.manager

import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.extensions.checkAccount
import com.example.trainingeco2.extensions.isIdExisted
import com.example.trainingeco2.helper.InputHelper
import com.example.trainingeco2.model.User
import com.example.trainingeco2.utils.CurrentUser
import com.example.trainingeco2.utils.Delays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginManager(private val libraryData: LibraryData) : ILoginManager {

    override suspend fun addNewAccount() = withContext(Dispatchers.IO) {
        println("Please enter your name")
        val userName = InputHelper.enterFullName("Full name")
        val userID = libraryData.addNewUser(userName)
        println("Sign up successfully, your ID is: $userID\n")
        val user = User(userID, userName)
        println("Please enter your password: ")
        val password = InputHelper.enterPassword()
        println("Delaying ${Delays.PRIMARY_DATA_OPERATION}ms...")
        delay(Delays.PRIMARY_DATA_OPERATION)
        libraryData.addNewAccount(user, password)
        println("Account created successfully for ${user.name}.")
    }

    override fun logout() {
        println("User ${CurrentUser.currentUser?.name} signed out.")
        CurrentUser.currentUser = null
    }

    override suspend fun handleUserSignIn() {
        println("\nEnter your ID to sign in to the library:")

        while (true) {
            val id = readln().toIntOrNull()

            if (id == null) {
                println("Invalid input! Please enter a NUMBER")
                continue
            }

            val user = libraryData.isIdExisted(id.toString())
            if (user == null) {
                println("There is no user with this ID, please enter another one:")
                continue
            }

            while (true) {
                print("Enter your password: ")
                val password = InputHelper.enterPassword()

                if (libraryData.checkAccount(user, password)) {
                    println("Sign in successful! Welcome, ${user.name}.")
                    CurrentUser.currentUser = user
                    return
                } else {
                    println("Wrong password, please enter again.")
                }
            }
        }
    }
}