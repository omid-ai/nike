package com.example.one.model.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.one.model.dataClass.Product

@Database(entities = [Product::class],version = 1,exportSchema = false)
abstract class FavoriteProductDB:RoomDatabase() {
    abstract fun getDao():FavoriteProductDao
}