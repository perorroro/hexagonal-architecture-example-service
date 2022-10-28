package com.hexagonal.architecture.example.adapters.mongo.model.converters

import com.hexagonal.architecture.example.adapters.mongo.model.CountryDb
import com.hexagonal.architecture.example.adapters.mongo.model.LocationDb
import com.hexagonal.architecture.example.adapters.mongo.model.PositionDb
import com.hexagonal.architecture.example.adapters.mongo.model.PostDb
import com.hexagonal.architecture.example.adapters.mongo.model.UserDb
import com.hexagonal.architecture.example.adapters.mongo.model.UserInfoDb
import com.hexagonal.architecture.example.domain.model.Country
import com.hexagonal.architecture.example.domain.model.Location
import com.hexagonal.architecture.example.domain.model.Position
import com.hexagonal.architecture.example.domain.model.Post
import com.hexagonal.architecture.example.domain.model.User
import com.hexagonal.architecture.example.domain.model.UserInfo


fun UserDb.toDomain() = User(
    id = this.id,
    userInfo = this.userInfo.toDomain(),
)

fun PostDb.toDomain() = Post(
    id = this.id,
    userId = this.userId,
    content = this.content,
    userInfo = this.userInfo.toDomain()
)

fun UserInfoDb.toDomain() = UserInfo(
    userName = this.userName,
    name = this.name,
    lastName = this.lastName,
    location = this.location.toDomain()
)

fun LocationDb.toDomain() = Location(
    country = this.country.toDomain(),
    position = this.position.toDomain()
)


fun PositionDb.toDomain() = Position(
    longitude = this.longitude,
    latitude = this.latitude
)

fun CountryDb.toDomain() = Country(
    isoCode = this.isoCode,
    countryName = this.countryName
)
