package com.arzupinarcioglu.listofgithubrepos.model

data class RepositoriesItemModel(
    val id: Int,
    val name: String,
    val stargazers_count: Int,
    val open_issues_count: Int,
    val owner: OwnerModel
)

