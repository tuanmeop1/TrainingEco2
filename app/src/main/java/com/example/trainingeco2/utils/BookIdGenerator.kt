package com.example.trainingeco2.utils

object BookIdGenerator {
    private var lastId: Int = 0

    fun getNextId(): Int {
        lastId += 1
        return lastId
    }
}