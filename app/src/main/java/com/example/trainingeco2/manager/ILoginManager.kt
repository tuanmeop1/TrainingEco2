package com.example.trainingeco2.manager

interface ILoginManager {
    suspend fun addNewAccount()
    fun logout()
    suspend fun handleUserSignIn()
}