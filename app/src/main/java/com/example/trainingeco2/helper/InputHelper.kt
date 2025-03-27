package com.example.trainingeco2.helper

const val HARD_COVER = "Hard Cover"
const val PAPERBACK = "Paperback"
const val MASS_PAPERBACK = "Mass Paperback"

object InputHelper {
    fun enterBookName(): String {
        while (true) {
            print("Book name: ")
            val bookName = readlnOrNull()
            if (bookName != null) return bookName else print("Please enter the book's name:")
        }
    }

    fun enterQuantity(): Int {
        while (true) {
            print("Quantity: ")
            val quantity = readln().toIntOrNull()

            when {
                quantity == null -> println("Quantity must be a NUMBER")
                quantity < 0 -> println("Quantity must be a POSITIVE NUMBER")
                else -> return quantity
            }
        }
    }


    fun enterFullName(paramName: String): String {
        print("$paramName: ")
        val name = readln()
        return if (checkIfNameIsValid(name)) {
            print("This name is not validate, please enter another name\n")
            print("This name must be start with a letter and does not contain any special character or number\n")
            enterFullName(paramName)
        } else name
    }

    fun enterPublishYear(): Int {
        print("Publish year: ")
        val publishYear = readln().toIntOrNull()
        return if (publishYear == null || publishYear < 0) {
            print("Publish year is not validate\n")
            enterPublishYear()
        } else publishYear
    }

    fun enterGenre(): String {
        print("Genre: ")
        val genre = readln()
        return if (checkIfNameIsValid(genre)) {
            print("This genre is not validate, please enter another name\n")
            print("Book genre must be start with a letter and does not contain any special character or number\n")
            enterGenre()
        } else genre
    }

    fun enterFormat(): String {
        val format = readln()
        return if (checkIfNameIsValid(format)) {
            print("This genre is not validate, please enter another name\n")
            print("Book genre must be start with a letter and does not contain any special character or number\n")
            enterFormat()
        } else format
    }

    fun enterSizeInMB(): Double {
        print("Size: ")
        val size = readln().toDoubleOrNull()
        return if (size == null || size < 0) {
            print("Book's Size must be a positive NUMBER\n")
            enterSizeInMB()
        } else size
    }

    fun enterPublisher(): String {
        while (true) {
            print("Publisher: ")
            val publisher = readln()
            if (checkIfNameIsValid(publisher)) {
                print("This publisher's name is not validate, please enter another name\n")
                print("Name must be start with a letter and does not contain any special character or number\n")
            } else return publisher
        }
    }

    fun enterBindingType(): String {
        while (true) {
            println("Binding Type?")
            println("\t 1 - Hard Cover, 2 - Paperback, 3 - Mass Paperback")
            val type = readln().toIntOrNull()
            when (type) {
                1 -> return HARD_COVER
                2 -> return PAPERBACK
                3 -> return MASS_PAPERBACK
                else -> print("Type input MUST be 1 2 or 3")
            }
        }
    }

    fun enterPassword(): String {
        while (true) {
            print("Password: ")
            val password = readln()
            if (checkIfPasswordIsValid(password)) {
                print("Invalid password. Password must be at least 6 characters long and cannot contain spaces\n")
            } else return password
        }
    }

    fun enterID(): String {
        while (true) {
            print("ID: ")
            val id = readln().trim()

            if (id.isEmpty()) {
                println("ID cannot be empty. Please enter a valid ID.")
            }

            else if (!id.all { it.isDigit() }) {
                println("ID must be numbers. Please enter a valid numeric ID.")
            } else return id
        }
    }


    private fun checkIfPasswordIsValid(password: String): Boolean {
        return password.contains(" ") || password.length < 6
    }

    private fun checkIfNameIsValid(name: String): Boolean {
        //val regex = "^[A-Za-z]+([ A-Za-z]+)*$"
        return name.trim().isEmpty() || name.trim().contains(Regex("^[ A-Za-z]+\$")).not()
    }

}