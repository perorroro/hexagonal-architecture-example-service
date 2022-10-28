package com.hexagonal.architecture.example.domain.model

class User(
    val id: String,
    val userInfo: UserInfo,
)

class UserInfo(
    val userName: String,
    val name: String,
    val lastName: String,
    val location: Location,
)

class Location(
    val country: Country,
    val position: Position
)

class Country(
    val isoCode: String,
    val countryName: String
)

class Position(
    val longitude: String,
    val latitude: String
)


