package com.example.trainingeco2.model

class EBook (
    bookName: String, author: String,
    publishYear: Int, genre: String,
    private val format: String, private val sizeInMB: Double
) : Book(bookName, author, publishYear, genre) {

    override fun printInfo() {
        print("ID: $id, Book name: $bookName, Author: $author, Publish Year: $publishYear" +
                ", Genre: $genre, Format: $format, Size (MB): $sizeInMB\n")
    }

}