package com.arzupinarcioglu.listofgithubrepos.model.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arzupinarcioglu.listofgithubrepos.model.local.FavoriteRepositoriesItem

@Dao
interface RepositoryDao{

    @Query("SELECT * FROM favori_repository_table WHERE id= :id")
    fun getRepository(id:Int): FavoriteRepositoriesItem


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepository(favoriteRepositoriesItem: FavoriteRepositoriesItem)

}