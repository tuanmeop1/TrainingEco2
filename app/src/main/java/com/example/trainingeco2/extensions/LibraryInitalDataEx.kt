package com.example.trainingeco2.extensions

import com.example.trainingeco2.data.LibraryData
import com.example.trainingeco2.model.Book
import com.example.trainingeco2.model.EBook
import com.example.trainingeco2.model.PrintBook

fun LibraryData.initDefaultData(books: MutableMap<Book, Int>) {
    if (books.isEmpty()) {
        val defaultBooks =
            listOf(
                EBook(
                    "The Hunger Games", "Suzanne Collins", 2010,
                    "Self help", "PDF", 56.1
                ),
                EBook(
                    "To Kill a Mockingbird", "Harper Lee", 1960,
                    "Fiction", "PDF", 38.4
                ),

                EBook(
                    "The Great Gatsby", "F. Scott Fitzgerald", 1925,
                    "Fiction", "EPUB", 29.7
                ),

                EBook(
                    "Pride and Prejudice", "Jane Austen", 1813,
                    "Romance", "MOBI", 35.2
                ),

                EBook(
                    "The Catcher in the Rye", "J.D. Salinger", 1951,
                    "Fiction", "PDF", 42.1
                ),

                EBook(
                    "Moby-Dick", "Herman Melville", 1851,
                    "Adventure", "EPUB", 61.5
                ),

                EBook(
                    "Brave New World", "Aldous Huxley", 1932,
                    "Dystopian", "MOBI", 47.3
                ),

                EBook(
                    "The Lord of the Rings", "J.R.R. Tolkien", 1954,
                    "Fantasy", "PDF", 112.9
                ),

                EBook(
                    "The Alchemist", "Paulo Coelho", 1988,
                    "Adventure", "EPUB", 45.0
                ),

                EBook(
                    "The Da Vinci Code", "Dan Brown", 2003,
                    "Thriller", "MOBI", 62.4
                ),

                EBook(
                    "The Shining", "Stephen King", 1977,
                    "Horror", "PDF", 54.7
                ),
                PrintBook(
                    "To Kill a Mockingbird", "Harper Lee", 1960,
                    "Fiction", "J.B. Lippincott & Co.", "Hardcover"
                ),

                PrintBook(
                    "1984", "George Orwell", 1949,
                    "Dystopian Fiction", "Secker & Warburg", "Paperback"
                ),

                PrintBook(
                    "Pride and Prejudice", "Jane Austen", 1813,
                    "Romance", "T. Egerton", "Hardcover"
                ),

                PrintBook(
                    "The Great Gatsby", "F. Scott Fitzgerald", 1925,
                    "Fiction", "Charles Scribner's Sons", "Paperback"
                ),

                PrintBook(
                    "Moby-Dick", "Herman Melville", 1851,
                    "Adventure", "Richard Bentley", "Leather-bound"
                ),

                PrintBook(
                    "Brave New World", "Aldous Huxley", 1932,
                    "Dystopian", "Chatto & Windus", "Paperback"
                )
            )

        books.putAll(defaultBooks
            .groupingBy { it }
            .eachCount()
            .toMutableMap())

    }
}