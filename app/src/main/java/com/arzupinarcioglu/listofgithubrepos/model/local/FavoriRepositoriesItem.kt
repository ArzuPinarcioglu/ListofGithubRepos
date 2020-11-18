package com.arzupinarcioglu.listofgithubrepos.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favori_repository_table")
data class FavoriteRepositoriesItem(
    @PrimaryKey val id: Int,
    var is_favorite: Boolean
)


