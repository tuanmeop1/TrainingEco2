package com.example.trainingeco2.manager

import com.example.trainingeco2.model.Book
import com.example.trainingeco2.model.User

interface ILibraryManager {
    fun addNewBook()
    fun removeBook(id: String): Boolean
    fun showAllBooks(books: Map<Book, Int>)
    fun searchBookByName(bookName: String): Map<Book, Int>
    fun searchBookById(id: String): Book?
    fun updateBookQuantity(book: Book, quantity: Int): Boolean
    fun getBorrowedBooks(user: User): List<Book>
    fun sortBooks(books: Map<Book, Int>, comparator: Comparator<Book>): Map<Book, Int>
    fun filterBooks(books: Map<Book, Int>, predicate: (Book) -> Boolean): Map<Book, Int>
}