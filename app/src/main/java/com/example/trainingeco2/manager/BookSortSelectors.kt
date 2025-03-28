package com.example.trainingeco2.manager

import com.example.trainingeco2.model.Book

//Enum
enum class BookSortSelectors{
    BY_YEAR,
    BY_NAME;

    fun getComparator(): Comparator<Book> {
        return when (this) {
            BY_YEAR -> compareBy { it.publishYear }
            BY_NAME -> compareBy { it.bookName }
        }
    }
}
