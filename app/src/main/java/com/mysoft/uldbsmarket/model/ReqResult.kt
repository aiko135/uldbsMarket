package com.mysoft.uldbsmarket.model

//Позволяет расширить любой тип, добавив полезную нагрузку
class ReqResult<T>(
    val isSuccess: Boolean, //Успешен ли был Веб-запрос
    val message: String,        //Сообщение полученное с сервера, либо сообщение об ошибке
    val entity: T?              //Предмет запроса
)