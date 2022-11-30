package com.nextgen.newsapp.helper

sealed class Async<out R> private constructor() {
    data class Success<out T>(val data: T) : Async<T>()
    data class Error(val error: String) : Async<Nothing>()
    object Loading : Async<Nothing>()
}