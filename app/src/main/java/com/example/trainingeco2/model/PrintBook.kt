package com.example.trainingeco2.model

class PrintBook(
    bookName: String, author: String,
    publishYear: Int, genre: String,
    private val publisher: String, private val bindingType: String
) : Book(bookName, author, publishYear, genre) {

    override fun printInfo() {
        print("ID: $id, Book name: $bookName, Author: $author, Publish Year: $publishYear" +
                ", Genre: $genre, Publisher: $publisher, Binding Type: $bindingType\n")
    }

}