package com.mysoft.uldbsmarket.model.dto


sealed class RequestResult<T>()

class RequestSuccess<T>(
    val entity: T  //Предмет запроса
): RequestResult<T>()

class RequestError<T>(
    val message: String //сообщение ошибки
):RequestResult<T>()