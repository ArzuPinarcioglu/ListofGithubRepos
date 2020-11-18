package com.arzupinarcioglu.listofgithubrepos.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arzupinarcioglu.listofgithubrepos.model.local.dao.RepositoryDao


@Database(entities = [FavoriteRepositoriesItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao

}