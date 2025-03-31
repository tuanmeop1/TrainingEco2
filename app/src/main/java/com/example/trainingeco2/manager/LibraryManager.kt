package com.example.trainingeco2.manager

import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.extensions.showBorrowingRecord
import com.example.trainingeco2.extensions.showUserAccounts
import com.example.trainingeco2.helper.InputHelper
import com.example.trainingeco2.model.Book
import com.example.trainingeco2.model.EBook
import com.example.trainingeco2.model.PrintBook
import com.example.trainingeco2.model.User
import com.example.trainingeco2.utils.CurrentUser
import com.example.trainingeco2.utils.Delays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LibraryManager(private val libraryData: LibraryData) : ILibraryManager {

    override suspend fun addNewBook() = withContext(Dispatchers.IO) {
        while (true) {
            println("Which kind of book would you like to add?")
            println("\t1 - PrintBook")
            println("\t2 - E-Book")
            val bookType = readlnOrNull()?.toIntOrNull()
            if (bookType == null || (bookType != 1 && bookType != 2)) {
                println("Invalid input! Please choose 1 for PrintBook or 2 for E-Book.")
                continue
            }
            println("Please enter all information for the book:")
            val bookName = InputHelper.enterBookName()
            val author = InputHelper.enterFullName("Author")
            val publishYear = InputHelper.enterPublishYear()
            val genre = InputHelper.enterGenre()
            val book: Book = when (bookType) {
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
                    println("Unexpected error during book creation type check")
                    return@withContext
                }
            }
            println("Delaying ${Delays.PRIMARY_DATA_OPERATION}ms...")
            delay(Delays.PRIMARY_DATA_OPERATION)
            libraryData.addNewBook(book)
            println("Successfully added book: ${book.bookName}")
            break
        }
    }

    override suspend fun removeBookById() {
        print("Please enter the id of the book you want to delete:")
        val id = InputHelper.enterID()
        val result = removeBook(id)
        if (result) { println("Book deleted successfully") }
        else { println("Error deleting book or book not found.") }
    }

    override suspend fun searchAndPrintBookByName() {
        val bookName = InputHelper.enterBookName()
        val booksWithSameName = searchBookByName(bookName)
        if (booksWithSameName.isNotEmpty()) {
            booksWithSameName.forEach { (book, quantity) -> book.printDetailInfo(quantity) }
        } else { println("There is no book with that name") }
    }

    override suspend fun borrowBook() = withContext(Dispatchers.IO) {
        println("Enter the ID of the book you want to borrow:")
        val bookId = InputHelper.enterID()
        println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
        delay(Delays.SECONDARY_DATA_OPERATION)
        val book = libraryData.books.keys.find { it.id.toString() == bookId }
        if (book != null) {
            val quantity = libraryData.books[book] ?: 0
            if (quantity > 0) {
                val user = CurrentUser.currentUser
                if (user != null) {
                    println("Delaying ${Delays.PRIMARY_DATA_OPERATION}ms...")
                    delay(Delays.PRIMARY_DATA_OPERATION)
                    libraryData.borrowedBooks.getOrPut(user) { mutableListOf() }.add(book)
                    libraryData.books[book] = quantity - 1
                    println("You have successfully borrowed '${book.bookName}'.")
                } else { println("Error: No user signed in.") }
            } else { println("Sorry, the book '${book.bookName}' is not available.") }
        } else { println("No book found with ID '$bookId'.") }
    }

    override suspend fun returnBook() = withContext(Dispatchers.IO) {
        println("Enter the id of the book you want to return:")
        val bookId = InputHelper.enterID()
        val user = CurrentUser.currentUser
        if (user == null) {
            println("Error: No user signed in.")
            return@withContext
        }
        println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
        delay(Delays.SECONDARY_DATA_OPERATION)
        val userBorrowedBooks = libraryData.borrowedBooks[user]
        if (userBorrowedBooks != null) {
            val book = userBorrowedBooks.find { it.id.toString() == bookId }
            if (book != null) {
                println("Delaying ${Delays.PRIMARY_DATA_OPERATION}ms...")
                delay(Delays.PRIMARY_DATA_OPERATION)
                userBorrowedBooks.remove(book)
                libraryData.books[book] = (libraryData.books[book] ?: 0) + 1
                println("You have successfully returned '${book.bookName}'.")
                if (userBorrowedBooks.isEmpty()) { libraryData.borrowedBooks.remove(user) }
            } else { println("You haven't borrowed the book with ID '$bookId'.") }
        } else { println("You haven't borrowed any books.") }
    }

    override suspend fun showBorrowedBooks(user: User) {
        val borrowedBooks = getBorrowedBooks(user)
        if (borrowedBooks.isEmpty()) { println("${user.name} has not borrowed any books.") }
        else {
            println("${user.name} has borrowed the following books:")
            borrowedBooks.forEach { book -> println("- ${book.bookName} by ${book.author}") }
        }
    }

    override suspend fun updateBookQuantityById() {
        println("Enter the ID of the book you want to update:")
        val bookId = InputHelper.enterID()
        val bookToUpdate = searchBookById(bookId)
        if (bookToUpdate == null) { println("No book found with ID '$bookId'."); return }
        println("Enter the new quantity for '${bookToUpdate.bookName}':")
        val newQuantity = InputHelper.enterQuantity()
        val updated = updateBookQuantity(bookToUpdate, newQuantity)
        if (updated) { println("Successfully updated '${bookToUpdate.bookName}' to $newQuantity copies.") }
        else { println("Failed to update the book quantity (maybe quantity was negative?).") }
    }

    //Higher order functions: We pass comparator as parameter
    override fun showSortedBookByName() {
        val sortedBooks = sortBooks(libraryData.books, BookSortSelectors.BY_NAME.getComparator())
        println("Books sorted in ascending order by name:")
        showAllBooks(sortedBooks)
    }

    //Higher order functions: We pass comparator as parameter
    override fun showSortedBookByYear() {
        val sortedBooks = sortBooks(libraryData.books, BookSortSelectors.BY_YEAR.getComparator())
        println("Books sorted in ascending order by publish year:")
        showAllBooks(sortedBooks)
    }

    //Higher-order function, lambda expression
    private fun filterBooksByYear(books: Map<Book, Int>, year: Int, filterType: FilterType): Map<Book, Int> {
        val filter: (Book) -> Boolean = when (filterType) {
            FilterType.EQUAL -> { book -> book.publishYear == year }
            FilterType.GREATER -> { book -> book.publishYear > year }
            FilterType.LESS -> { book -> book.publishYear < year }
        }
        return filterBooks(books, filter)
    }

    override fun showFilteredBookByYear(books: Map<Book, Int>, filterType: FilterType) {
        val year = InputHelper.enterPublishYear()
        val filteredBooks = filterBooksByYear(books, year, filterType)
        showAllBooks(filteredBooks)
    }

    override suspend fun getBorrowedBooks(user: User): List<Book> = withContext(Dispatchers.IO) {
        println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
        delay(Delays.SECONDARY_DATA_OPERATION)
        libraryData.borrowedBooks[user]?.toList() ?: emptyList()
    }

    //Higher order functions: We pass comparator as parameter
    override fun sortBooks(books: Map<Book, Int>, comparator: Comparator<Book>): Map<Book, Int> {
        return books.toSortedMap(comparator)
    }

    override fun filterBooks(books: Map<Book, Int>, predicate: (Book) -> Boolean): Map<Book, Int> {
        return books.filterKeys(predicate)
    }

    override suspend fun removeBook(id: String): Boolean = withContext(Dispatchers.IO) {
        println("Delaying ${Delays.PRIMARY_DATA_OPERATION}ms...")
        delay(Delays.PRIMARY_DATA_OPERATION)
        libraryData.removeBook(id)
    }

    override fun showAllBooks(books: Map<Book, Int>) {
        if (books.isEmpty()) { println("No books to display."); return }
        println("--- Library Books (${books.size} unique titles) ---")
        books.forEach { (book, quantity) ->
            book.printInfo()
            println("\tQuantity: $quantity")
        }
        println("------------------------------------")
    }

    override suspend fun searchBookByName(bookName: String): Map<Book, Int> = withContext(Dispatchers.IO) {
        println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
        delay(Delays.SECONDARY_DATA_OPERATION)
        libraryData.books.filter { it.key.bookName.equals(bookName, ignoreCase = true) }
    }

    override suspend fun searchBookById(id: String): Book? = withContext(Dispatchers.IO) {
        println("Delaying ${Delays.SECONDARY_DATA_OPERATION}ms...")
        delay(Delays.SECONDARY_DATA_OPERATION)
        libraryData.books.keys.find { it.id.toString() == id }
    }

    override suspend fun updateBookQuantity(book: Book, quantity: Int): Boolean = withContext(Dispatchers.IO) {
        println("Delaying ${Delays.PRIMARY_DATA_OPERATION}ms...")
        delay(Delays.PRIMARY_DATA_OPERATION)
        libraryData.updateBookQuantity(book, quantity)
    }

    override suspend fun showUserAccounts() {
        libraryData.showUserAccounts()
    }

    override suspend fun showBorrowingRecord() {
        libraryData.showBorrowingRecord()
    }

}