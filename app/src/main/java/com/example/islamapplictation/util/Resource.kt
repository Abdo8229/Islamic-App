package com.example.islamapplictation.util

sealed class Resource<T>(val data:T?,val massage:String?){
    class Success<T>(data: T): Resource<T>(data, null)
    class Error<T>(message:String?):Resource<T>(null,message)
}
