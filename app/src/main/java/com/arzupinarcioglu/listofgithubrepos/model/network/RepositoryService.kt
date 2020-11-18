package com.arzupinarcioglu.listofgithubrepos.model.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.androidmvvmdatabindingrecyclerviewkotlin.network.RetrofitClient
import com.arzupinarcioglu.listofgithubrepos.model.RepositoriesItemModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryService {


    /*
    MutableLiveData = MutableLiveData is a subclass of LiveData which is used for some of it's properties (setValue/postValue) and using these properties
    we can easily notify the ui when onChange() is called.
    */
    fun getMutableLiveData(username: String): MutableLiveData<List<RepositoriesItemModel>> {

        val mutableLiveData = MutableLiveData<List<RepositoriesItemModel>>()
        val repositoryList = ArrayList<RepositoriesItemModel>()

        // init retrofit class
        val apiService = RetrofitClient.service

        // We are using a sealed class to have a “enum” that can handle the different results
        val call = apiService.getRepos(username = username)


        call.enqueue(object : Callback<JsonArray> {

            override fun onResponse(call: Call<JsonArray>, resp: Response<JsonArray>) {

                // Parse the data if Response successfully and store data in MutableLiveData
                val gson = GsonBuilder().create()

                if (resp != null && resp.body() != null) {

                    val jsonArray = JsonParser().parse(resp.body()!!.toString()).asJsonArray

                    if (jsonArray != null) {

                        for (i in 0..jsonArray.size() - 1) {
                            try {

                                val jsonobj =
                                    JsonParser().parse(jsonArray.get(i).toString()).asJsonObject

                                val responseModel =
                                    gson.fromJson(jsonobj, RepositoriesItemModel::class.java)

                                repositoryList.add(responseModel)

                            } catch (ex: Exception) {
                                Log.e(TAG, ex.message)
                            }
                        }
                        mutableLiveData.value = repositoryList

                    }
                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return mutableLiveData
    }

    companion object {

        private val TAG = "RepositoryService"
    }
}
