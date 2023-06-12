package com.example.islamapplictation.data.pojo.user

data class User(
    val email:String,
    val fullName:String,
    val password:String,
    val mAuth:String
    )
enum class AuthType{
    GOOGLE,
    FireBaseEmailPassword
}