package com.example.trainingeco2.extensions

import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.model.User

fun LibraryData.checkAccount(user: User, password: String): Boolean {
    return accounts[user] == password
}

fun LibraryData.isIdExisted(id: String): User? {
    return users.find { it.userId == id }
}

fun LibraryData.showUserAccounts() {
    if (accounts.isEmpty()) {
        println("No user accounts found.")
        return
    }

    println("User Accounts:")
    accounts.keys.forEach { user ->
        println("User ID: ${user.userId}, Name: ${user.name}")
    }
}

fun LibraryData.showBorrowingRecord() {
    if (borrowedBooks.isEmpty()) {
        println("No books are currently borrowed.")
        return
    }

    println("Borrowing Records:")
    borrowedBooks.forEach { (user, books) ->
        println("User: ${user.name} (ID: ${user.userId})")
        books.forEach { book ->
            println(" - ${book.bookName} by ${book.author}")
        }
    }
}