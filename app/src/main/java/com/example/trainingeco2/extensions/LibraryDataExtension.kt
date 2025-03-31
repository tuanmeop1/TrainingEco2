package com.example.trainingeco2.extensions

import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.model.User
import com.example.trainingeco2.utils.Delays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun LibraryData.checkAccount(user: User, password: String): Boolean = withContext(
    Dispatchers.IO
) {
    println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
    delay(Delays.SECONDARY_DATA_OPERATION)
    accounts[user] == password
}

suspend fun LibraryData.isIdExisted(id: String): User? = withContext(Dispatchers.IO) {
    println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
    delay(Delays.SECONDARY_DATA_OPERATION)
    users.find { it.userId == id }
}

suspend fun LibraryData.showUserAccounts() = withContext(Dispatchers.IO) {
    println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
    delay(Delays.SECONDARY_DATA_OPERATION)
    if (accounts.isEmpty()) {
        println("No user accounts found.")
        return@withContext
    }
    println("User Accounts (${accounts.size}):")
    accounts.keys.forEach { user ->
        println("User ID: ${user.userId}, Name: ${user.name}")
    }
}

suspend fun LibraryData.showBorrowingRecord() = withContext(Dispatchers.IO) {
    println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
    delay(Delays.SECONDARY_DATA_OPERATION)
    if (borrowedBooks.isEmpty()) {
        println("No books are currently borrowed.")
        return@withContext
    }
    println("Borrowing Records (${borrowedBooks.size} users borrowing):")
    borrowedBooks.forEach { (user, books) ->
        println("User: ${user.name} (ID: ${user.userId}) has ${books.size} book(s):")
        books.forEach { book ->
            println(" - ${book.bookName} by ${book.author}")
        }
    }
}
