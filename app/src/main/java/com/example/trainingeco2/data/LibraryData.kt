package com.example.trainingeco2.data

import com.example.trainingeco2.extensions.initDefaultData
import com.example.trainingeco2.model.Book
import com.example.trainingeco2.model.EBook
import com.example.trainingeco2.model.PrintBook
import com.example.trainingeco2.model.User

class LibraryData {
    private val _books = mutableMapOf<Book, Int>()
    val books get() = _books

    private val _users = mutableListOf<User>()
    val users get() = _users

    private val _accounts = mutableMapOf<User, String>()
    val accounts get() = _accounts

    private val _borrowedBooks = mutableMapOf<User, MutableList<Book>>()
    val borrowedBooks get() = _borrowedBooks

    init {
        initDefaultData(books)
    }

    fun addNewBook(book: Book) {
        _books[book] = _books.getOrDefault(book, 0) + 1
        println("Book added: ${book.bookName}. Total copies: ${_books[book]}")
    }

    fun addNewUser(userName: String): String {
        val index = _users.lastOrNull()?.userId?.toIntOrNull() ?: -1
        val userIdInString = (index + 1).toString()
        _users.add(User(userIdInString, userName))
        return userIdInString
    }

    fun addNewAccount(user: User, password: String) {
        _accounts[user] = password
    }

    fun removeBook(id: String): Boolean {
        val removeBook = books.keys.find { it.id.toString() == id }
        return if (removeBook != null) {
            books.remove(removeBook)
            true
        } else {
            false
        }
    }

    fun updateBookQuantity(book: Book, quantity: Int): Boolean {
        if (books.containsKey(book)) {
            if (quantity >= 0) {
                books[book] = quantity
                return true
            } else return false
        }
        return false
    }

}