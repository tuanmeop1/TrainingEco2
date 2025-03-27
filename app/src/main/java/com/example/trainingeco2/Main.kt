package com.example.trainingeco2


import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.manager.LibraryManager
import com.example.trainingeco2.manager.LoginManager
import com.example.trainingeco2.menu.MainMenu
import com.example.trainingeco2.utils.CurrentUser

const val USER_MODE = 0
const val MANAGER_MODE = 1

fun main() {
    val libraryData = LibraryData()

    val libraryManager = LibraryManager(libraryData)
    val loginManager = LoginManager(libraryData)

    handleAppMode(loginManager, libraryManager)
}

fun handleAppMode(loginManager: LoginManager, libraryManager: LibraryManager) {

    println("This is Library Management System")
    println("Who are you???")
    println("Please Enter: 1 - If you are Manager, 0 - If you are User")

    while (CurrentUser.appMode == -1) {
        val modeChoice = readln().toIntOrNull()
        if (modeChoice == null) {
            println("Invalid input! Please enter a NUMBER")
            continue
        }

        when (modeChoice) {
            0 -> CurrentUser.appMode = USER_MODE
            1 -> CurrentUser.appMode = MANAGER_MODE
            else -> println("Please enter the right number!")
        }
    }

    when (CurrentUser.appMode) {
        USER_MODE -> handleUserSignMenu(loginManager, libraryManager)
        MANAGER_MODE -> handleManagerMenuChoice(libraryManager)
    }
}

fun handleUserMenuChoice(loginManager: LoginManager, libraryManager: LibraryManager) {
    while (true) {
        MainMenu.showUserMenu()
        println("\nEnter your choice (0-5):")
        val choice = readln().toIntOrNull()

        if (choice == null) {
            println("Invalid input! Please enter a NUMBER")
            continue
        }

        libraryManager.apply {
            when (choice) {
                0 -> {
                    println("Signing out...")
                    CurrentUser.currentUser = null
                    handleUserSignMenu(loginManager, libraryManager)
                }

                1 -> showAllBooks()
                2 -> searchAndPrintBookByName()
                3 -> borrowBook()
                4 -> returnBook()
                5 -> showBorrowedBooks(CurrentUser.currentUser!!)
                else -> println("Invalid choice! Please enter a number between 0 and 5")
            }
        }
    }
}

fun handleUserSignMenu(loginManager: LoginManager, libraryManager: LibraryManager) {
    MainMenu.showGuidance()
    var exit = false

    while (!exit) {
        MainMenu.showSignMenu()
        println("\nEnter your choice (0-3):")
        val choice = readln().toIntOrNull()

        when (choice) {
            null -> println("Invalid input! Please enter a NUMBER")
            0 -> {
                println("Exiting...")
                CurrentUser.appMode = -1
                handleAppMode(loginManager, libraryManager)
                exit = true
            }
            1 -> MainMenu.showGuidance()
            2 -> {
                loginManager.handleUserSignIn()
                handleUserMenuChoice(loginManager, libraryManager)
                exit = true
            }
            3 -> loginManager.addNewAccount()
            else -> println("Invalid choice! Please enter a number between 0 and 3")
        }
    }
}

fun handleManagerMenuChoice(libraryManager: LibraryManager) {
    while (true) {
        MainMenu.showManagerMenu()
        println("\nEnter your choice (0-6):")
        val choice = readln().toIntOrNull()

        if (choice == null) {
            println("Invalid input! Please enter a NUMBER")
            continue
        }

        when (choice) {
            0 -> {
                println("Exit program...")
                return
            }

            1 -> libraryManager.addNewBook()
            2 -> libraryManager.removeBookById()
            3 -> libraryManager.updateBookQuantityById()
            4 -> libraryManager.showAllBooks()
            5 -> libraryManager.showBorrowingRecord()
            6 -> libraryManager.showUserAccounts()
            else -> println("Invalid choice! Please enter a number between 0 and 6")
        }
    }
}