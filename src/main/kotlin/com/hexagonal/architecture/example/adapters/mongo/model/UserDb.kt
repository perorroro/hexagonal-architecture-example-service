package com.hexagonal.architecture.example.adapters.mongo.model

class UserDb(
    val id: String,
    val userInfo: UserInfoDb,
    val post: PostDb,
)

class UserInfoDb(
    val userName: String,
    val name: String,
    val lastName: String,
    val location: LocationDb,
)

class LocationDb(
    val country: CountryDb,
    val position: PositionDb
)

class CountryDb(
    val isoCode: String,
    val countryName: String
)

class PositionDb(
    val longitude: String,
    val latitude: String
)
