package com.example.trainingeco2.app

import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.manager.FilterType
import com.example.trainingeco2.manager.LibraryManager
import com.example.trainingeco2.manager.LoginManager
import com.example.trainingeco2.menu.MainMenu
import com.example.trainingeco2.utils.CurrentUser

class LibraryApp(
    private val loginManager: LoginManager,
    private val libraryManager: LibraryManager,
    private val libraryData: LibraryData
) {
    fun run() {
        println("This is Library Management System")
        println("Who are you???")
        println("Please Enter: 1 - If you are Manager, 0 - If you are User")

        while (CurrentUser.appMode == -1) {
            val modeChoice = readln().toIntOrNull()

            when (modeChoice) {
                null -> println("Invalid input! Please enter a NUMBER")
                0 -> CurrentUser.appMode = 0
                1 -> CurrentUser.appMode = 1
                else -> println("Please enter the right number!")
            }
        }

        when (CurrentUser.appMode) {
            0 -> handleUserSignMenu()
            1 -> handleManagerMenuChoice()
        }
    }

    private fun handleUserSignMenu() {
        MainMenu.showGuidance()

        while (true) {
            MainMenu.showSignMenu()
            println("\nEnter your choice (0-3):")

            when (readln().toIntOrNull()) {
                null -> println("Invalid input! Please enter a NUMBER")
                0 -> {
                    println("Exiting...")
                    CurrentUser.appMode = -1
                    run()
                }

                1 -> MainMenu.showGuidance()
                2 -> {
                    loginManager.handleUserSignIn()
                    handleUserMenuChoice()
                }

                3 -> loginManager.addNewAccount()
                else -> println("Invalid choice! Please enter a number between 0 and 3")
            }
        }
    }

    private fun handleUserMenuChoice() {
        while (true) {
            MainMenu.showUserMenu()
            println("\nEnter your choice (0-7):")

            val choice = readln().toIntOrNull()
            when (choice) {
                null -> println("Invalid input! Please enter a NUMBER")
                0 -> {
                    println("Signing out...")
                    loginManager.logout()
                    handleUserSignMenu()
                }

                1 -> libraryManager.showAllBooks(libraryData.books)
                2 -> libraryManager.showSortedBookByName()
                3 -> libraryManager.showSortedBookByYear()
                4 -> libraryManager.showFilteredBookByYear(libraryData.books, FilterType.EQUAL)
                5 -> libraryManager.showFilteredBookByYear(libraryData.books, FilterType.GREATER)
                6 -> libraryManager.searchAndPrintBookByName()
                7 -> libraryManager.borrowBook()
                8 -> libraryManager.returnBook()
                9 -> libraryManager.showBorrowedBooks(CurrentUser.currentUser!!)
                else -> println("Invalid choice! Please enter a number between 0 and 9")
            }
        }
    }

    private fun handleManagerMenuChoice() {
        while (true) {
            MainMenu.showManagerMenu()
            println("\nEnter your choice (0-6):")
            val choice = readln().toIntOrNull()

            when (choice) {
                null -> println("Invalid input! Please enter a NUMBER")
                0 -> {
                    println("Exit program...")
                    return
                }

                1 -> libraryManager.addNewBook()
                2 -> libraryManager.removeBookById()
                3 -> libraryManager.updateBookQuantityById()
                4 -> libraryManager.showAllBooks(libraryData.books)
                5 -> libraryManager.showBorrowingRecord()
                6 -> libraryManager.showUserAccounts()
                else -> println("Invalid choice! Please enter a number between 0 and 6")
            }
        }
    }
}
