package com.example.trainingeco2.model

import com.example.trainingeco2.utils.BookIdGenerator

abstract class Book(
    val bookName: String, val author: String,
    val publishYear: Int, val genre: String
) {

    val id: Int = BookIdGenerator.getNextId()

    abstract fun printInfo()

    fun printDetailInfo(quantity: Int) {
        printInfo()
        println("\tQuantity: $quantity")
    }
}