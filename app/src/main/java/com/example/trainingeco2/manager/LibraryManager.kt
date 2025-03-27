package com.example.trainingeco2.manager

import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.extensions.showBorrowingRecord
import com.example.trainingeco2.extensions.showUserAccounts
import com.example.trainingeco2.helper.ILibraryManager
import com.example.trainingeco2.helper.InputHelper
import com.example.trainingeco2.model.Book
import com.example.trainingeco2.model.EBook
import com.example.trainingeco2.model.PrintBook
import com.example.trainingeco2.model.User
import com.example.trainingeco2.utils.CurrentUser

class LibraryManager(private val libraryData: LibraryData) : ILibraryManager {

    override fun addNewBook() {
        while (true) {
            println("Which kind of book would you like to add?")
            println("\t1 - PrintBook")
            println("\t2 - E-Book")
            val bookType = readln().toIntOrNull()

            if (bookType == null || (bookType != 1 && bookType != 2)) {
                println("Invalid input! Please choose 1 for PrintBook or 2 for E-Book.")
                continue
            }

            println("Please enter all information for the book:")

            val bookName = InputHelper.enterBookName()
            val author = InputHelper.enterFullName("Author")
            val publishYear = InputHelper.enterPublishYear()
            val genre = InputHelper.enterGenre()

            val book = when (bookType) {
                1 -> {
                    val publisher = InputHelper.enterPublisher()
                    val bindingType = InputHelper.enterBindingType()
                    PrintBook(bookName, author, publishYear, genre, publisher, bindingType)
                }

                2 -> {
                    val format = InputHelper.enterFormat()
                    val sizeInMB = InputHelper.enterSizeInMB()
                    EBook(bookName, author, publishYear, genre, format, sizeInMB)
                }

                else -> {
                    println("Unexpected error")
                    return
                }
            }
            libraryData.addNewBook(book)
            println("Successfully added book: ${book.bookName}")
            break
        }
    }

    fun removeBookById() {
        print("Please enter the id of the book you want to delete:")
        val id = InputHelper.enterID()
        val result = removeBook(id)

        if (result) {
            println("Book deleted successfully")
        } else {
            println("There's some error")
        }
    }

    fun searchAndPrintBookByName() {
        val bookName = InputHelper.enterBookName()
        val booksWithSameName = searchBookByName(bookName)
        if (booksWithSameName.isNotEmpty()) {
            booksWithSameName.forEach {
                it.key.printDetailInfo(it.value)
            }
        } else {
            println("There is no book with that name")
        }
    }

    fun borrowBook() {
        println("Enter the ID of the book you want to borrow:")
        val bookId = InputHelper.enterID()

        libraryData.books.keys.find { it.id.toString() == bookId }?.let { book ->
            val quantity = libraryData.books[book] ?: 0
            if (quantity > 0) {
                val user = CurrentUser.currentUser
                if (user != null) {
                    libraryData.borrowedBooks.getOrPut(user) { mutableListOf() }.add(book)
                    libraryData.books[book] = quantity - 1
                    println("You have successfully borrowed '${book.bookName}'.")
                } else {
                    println("Error: No user signed in.")
                }
            } else {
                println("Sorry, the book is not available.")
            }
        } ?: println("No book found with ID '$bookId'.")
    }

    fun returnBook() {
        println("Enter the id of the book you want to return:")
        val bookId = InputHelper.enterID()
        val user = CurrentUser.currentUser

        if (user == null) {
            println("Error: No user signed in.")
            return
        }

        libraryData.borrowedBooks[user]?.let { borrowedBooks ->
            borrowedBooks.find { it.id.toString() == bookId }?.let { book ->
                borrowedBooks.remove(book)
                libraryData.books[book] = (libraryData.books[book] ?: 0) + 1
                println("You have successfully returned '${book.bookName}'.")
            } ?: println("You haven't borrowed this book.")
        } ?: println("You haven't borrowed any books.")
    }

    fun showBorrowedBooks(user: User) {
        val borrowedBooks = getBorrowedBooks(user)

        if (borrowedBooks.isEmpty()) {
            println("${user.name} has not borrowed any books.")
        } else {
            println("${user.name} has borrowed the following books:")
            borrowedBooks.forEach { book ->
                println("- ${book.bookName} by ${book.author}")
            }
        }
    }

    fun updateBookQuantityById() {
        println("Enter the ID of the book you want to update:")
        val bookId = InputHelper.enterID()

        val bookToUpdate = searchBookById(bookId)

        if (bookToUpdate == null) {
            println("No book found with ID '$bookId'.")
            return
        }

        println("Enter the new quantity for '${bookToUpdate.bookName}':")
        val newQuantity = InputHelper.enterQuantity()

        val updated = updateBookQuantity(bookToUpdate, newQuantity)
        if (updated) {
            println("Successfully updated '${bookToUpdate.bookName}' to $newQuantity copies.")
        } else {
            println("Failed to update the book quantity.")
        }
    }

    fun showUserAccounts() {
        libraryData.showUserAccounts()
    }

    fun showBorrowingRecord() {
        libraryData.showBorrowingRecord()
    }

    override fun getBorrowedBooks(user: User): List<Book> {
        return libraryData.borrowedBooks[user] ?: emptyList()
    }

    override fun removeBook(id: String): Boolean {
        return libraryData.removeBook(id)
    }

    override fun showAllBooks() {
        println("All Books in Library (${libraryData.books.size} total):")
        for (book in libraryData.books) {
            book.key.printInfo()
            println("\tQuantity: ${book.value}")
        }
    }

    override fun isBookAvailable(book: Book): Boolean {
        return libraryData.books.containsKey(book)
    }

    override fun searchBookByName(bookName: String): Map<Book, Int> {
        return libraryData.books.filter { it.key.bookName == bookName }
    }

    override fun searchBookById(id: String): Book? {
        return libraryData.books.keys.find { it.id.toString() == id }
    }

    override fun updateBookQuantity(book: Book, quantity: Int): Boolean {
        return libraryData.updateBookQuantity(book, quantity)
    }

}