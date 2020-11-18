package com.arzupinarcioglu.listofgithubrepos.model.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arzupinarcioglu.listofgithubrepos.App
import com.arzupinarcioglu.listofgithubrepos.R
import com.arzupinarcioglu.listofgithubrepos.model.RepositoriesItemModel
import com.arzupinarcioglu.listofgithubrepos.model.local.FavoriteRepositoriesItem
import com.arzupinarcioglu.listofgithubrepos.view.RepositoryDetail
import kotlinx.android.synthetic.main.row_list_item.view.*

class RepositoryAdapter(val repositories: MutableList<RepositoriesItemModel>) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_list_item, parent, false)
        return RepositoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {

        val repository = repositories[position]
        holder.itemView.txtCompanyName.text = repository.name
        var favoriteRepositoriesItem = App.db.repositoryDao().getRepository(repository.id)
        if (favoriteRepositoriesItem != null && favoriteRepositoriesItem.is_favorite) {
            holder.itemView.star.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.star_selected,
                0,
                0,
                0
            )
        } else {
            favoriteRepositoriesItem = FavoriteRepositoriesItem(repository.id, true)
            holder.itemView.star.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0)
        }

        holder.itemView.star.setOnClickListener {

            favoriteRepositoriesItem.is_favorite = !favoriteRepositoriesItem.is_favorite

            App.db.repositoryDao().insertRepository(
                favoriteRepositoriesItem
            )
            if (favoriteRepositoriesItem.is_favorite == true) {
                holder.itemView.star.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.star_selected,
                    0,
                    0,
                    0
                )
            } else {
                holder.itemView.star.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.star,
                    0,
                    0,
                    0
                )
            }

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RepositoryDetail::class.java)

            intent.putExtra("id", repository.id)
            intent.putExtra("avatar_url", repository.owner.avatar_url)
            intent.putExtra("owner", repository.owner.login)
            intent.putExtra("stargazers_count", repository.stargazers_count)
            intent.putExtra("open_issues_count", repository.open_issues_count)
            intent.putExtra("name", repository.name)

            it.context.startActivity(intent)
        }

    }

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}