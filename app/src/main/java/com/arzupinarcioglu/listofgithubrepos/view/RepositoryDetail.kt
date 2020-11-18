package com.arzupinarcioglu.listofgithubrepos.view

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arzupinarcioglu.listofgithubrepos.App
import com.arzupinarcioglu.listofgithubrepos.R
import com.arzupinarcioglu.listofgithubrepos.model.local.FavoriteRepositoriesItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.actionbar_customized.view.*
import kotlinx.android.synthetic.main.actionbar_customized.view.star
import kotlinx.android.synthetic.main.activity_repository_detail.*

class RepositoryDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)

        val layoutParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        layoutParams.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL

        val inflator = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflator.inflate(R.layout.actionbar_customized, null)
        val id = intent.extras?.get("id") as Int
        v.title.text = intent.extras?.get("name").toString()
        owner.text = intent.extras?.get("owner").toString()
        stars.text = "Stars: " + intent.extras?.get("stargazers_count").toString()
        issues.text = "Open Issues: " + intent.extras?.get("open_issues_count").toString()
        Glide.with(this).load(intent.extras?.get("avatar_url").toString()).into(avatar)

        val favoriteRepositoriesItem = App.db.repositoryDao().getRepository(id)

        if (favoriteRepositoriesItem != null && favoriteRepositoriesItem.is_favorite) {
            v.star.setImageDrawable(getResources().getDrawable(R.drawable.star_selected, null))
        } else {
            v.star.setImageDrawable(getResources().getDrawable(R.drawable.star, null))
        }

        v.star.setOnClickListener {

            var favoriteRepositoriesItem = App.db.repositoryDao().getRepository(id)

            if (favoriteRepositoriesItem != null && favoriteRepositoriesItem.is_favorite) {
                favoriteRepositoriesItem.is_favorite = false
            } else if (favoriteRepositoriesItem == null) {
                favoriteRepositoriesItem = FavoriteRepositoriesItem(id, true)
            } else {
                favoriteRepositoriesItem.is_favorite = true
            }

            App.db.repositoryDao().insertRepository(
                favoriteRepositoriesItem
            )
            if (favoriteRepositoriesItem.is_favorite) {
                v.star.setImageDrawable(getResources().getDrawable(R.drawable.star_selected, null))
                Toast.makeText(
                    this,
                    "This repo has been added to your favorites list",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                v.star.setImageDrawable(getResources().getDrawable(R.drawable.star, null))
                Toast.makeText(
                    this,
                    "This repo has been removed from your favorites list",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        supportActionBar?.apply {

            customView = v
            customView.layoutParams = layoutParams
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowCustomEnabled(true)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
