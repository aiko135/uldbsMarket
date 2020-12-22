package com.mysoft.uldbsmarket.model.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.gson.Gson
import com.mysoft.uldbsmarket.model.User
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.OutputStream
import java.util.*

//Сериалайзер нужен для сохранения в DataStore
//TODO это под рефактор

object SettingsSerializer  : Serializer<User> {
    override fun readFrom(input: InputStream): User {
        try {
            val reader : BufferedReader = input.bufferedReader(Charsets.UTF_8)
            var json : String = "";
            var nextLine : String? = reader.readLine()
            while (nextLine  != null){
                json += nextLine;
                nextLine = reader.readLine();
            }

            if(json != ""){
                val result : User? = Gson().fromJson(json, User::class.java);
                return result ?: defaultValue;
            }
            else
                return defaultValue;

        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: User, output: OutputStream){
        try{
            val data : String  = Gson().toJson(t);
            val writer = output.bufferedWriter(Charsets.UTF_8);
            writer.write(data);
            writer.flush();
        }catch (exception: Exception){
            throw CorruptionException("Cannot read proto.", exception)
        }
    };
    override val defaultValue: User
        get() = User("null","null","null",1,"null", Date(),"null")
}
