package com.example.trainingeco2.menu

object MainMenu {
    fun showGuidance() {
        println("First, you need to enter your name. Once you do that, the system will automatically give you a unique ID.")
        println("You will need this ID to sign in and access all the features of the system.\n")
    }

    fun showSignMenu() {
        println("\n===== SIGN IN MENU =====")
        println("1. Show the guidance")
        println("2. Start signing in")
        println("3. Sign up")
        println("0. Exit")
    }

    fun showUserMenu() {
        println("\n===== USER MENU =====")
        println("1. Display all current books")
        println("2. Sorted book by name in ascending order")
        println("3. Sorted book by publish year in ascending order")
        println("4. Filter books published in the year of (input)")
        println("4. Filter books published after the year of (input)")
        println("6. Search books")
        println("7. Borrow a book")
        println("8. Return a book")
        println("9. View borrowing history")
        println("0. Exit")
    }

    fun showManagerMenu() {
        println("\n===== MANAGER MENU =====")
        println("1. Add new book")
        println("2. Remove book")
        println("3. Update book's quantity")
        println("4. View all books")
        println("5. View borrowing records")
        println("6. Manage users")
        println("0. Exit")
    }

}