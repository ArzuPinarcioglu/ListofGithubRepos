package com.arzupinarcioglu.listofgithubrepos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arzupinarcioglu.listofgithubrepos.model.RepositoriesItemModel
import com.arzupinarcioglu.listofgithubrepos.model.network.RepositoryService

class RepositoryViewModel : ViewModel() {

    private val repositoryService: RepositoryService

    fun getRepositories(username: String): LiveData<List<RepositoriesItemModel>> {
        return repositoryService.getMutableLiveData(username)
    }

    init {
        repositoryService = RepositoryService()
    }

}