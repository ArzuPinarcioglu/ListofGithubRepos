package com.arzupinarcioglu.listofgithubrepos.view


import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.arzupinarcioglu.listofgithubrepos.App.Companion.db
import com.arzupinarcioglu.listofgithubrepos.R
import com.arzupinarcioglu.listofgithubrepos.model.adapter.RepositoryAdapter
import com.arzupinarcioglu.listofgithubrepos.model.RepositoriesItemModel
import com.arzupinarcioglu.listofgithubrepos.model.local.AppDatabase
import com.arzupinarcioglu.listofgithubrepos.viewmodel.RepositoryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mainViewModel: RepositoryViewModel? = null
    var username_recent: String = ""
    var username_current: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "repository_table")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        mainViewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)

        viewrepository.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        get_repos.setOnClickListener {
            //get user repositories button onclicked
            username_current = user.text.toString().trim()
            if (!username_current.equals(username_recent)) {

                if (username_current.length == 0) {
                    Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                } else {
                    hideKeyboard(currentFocus ?: View(this))
                    getRepositories(username_current)
                }
            }
        }


    }

    private fun getRepositories(username: String) {
        ///get the list of dev from api response
        loadBar?.visibility = View.VISIBLE
        mainViewModel!!.getRepositories(username).observe(this, { mDeveloperModel ->

            viewrepository.adapter =
                RepositoryAdapter(mDeveloperModel as ArrayList<RepositoriesItemModel>)
            loadBar?.visibility = View.GONE
            if (username_recent != "")
                viewrepository.adapter?.notifyDataSetChanged()
            username_recent = username_current
        })
    }

    override fun onRestart() {
        super.onRestart()
        viewrepository.adapter?.notifyDataSetChanged()
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
