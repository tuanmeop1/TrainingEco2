package com.example.trainingeco2.manager

import com.example.trainingeco2.model.Book
import com.example.trainingeco2.model.User

interface ILibraryManager {
    suspend fun addNewBook()
    suspend fun removeBook(id: String): Boolean
    suspend fun searchBookByName(bookName: String): Map<Book, Int>
    suspend fun searchBookById(id: String): Book?
    suspend fun borrowBook()
    suspend fun returnBook()
    suspend fun showBorrowedBooks(user: User)
    suspend fun updateBookQuantity(book: Book, quantity: Int): Boolean
    suspend fun updateBookQuantityById()
    suspend fun getBorrowedBooks(user: User): List<Book>
    fun sortBooks(books: Map<Book, Int>, comparator: Comparator<Book>): Map<Book, Int>
    fun filterBooks(books: Map<Book, Int>, predicate: (Book) -> Boolean): Map<Book, Int>
    fun showAllBooks(books: Map<Book, Int>)
    fun showSortedBookByName()
    fun showSortedBookByYear()
    fun showFilteredBookByYear(books: Map<Book, Int>, filterType: FilterType)
    suspend fun showUserAccounts()
    suspend fun showBorrowingRecord()
    suspend fun removeBookById()
    suspend fun searchAndPrintBookByName()
}
