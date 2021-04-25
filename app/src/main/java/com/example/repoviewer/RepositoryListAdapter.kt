package com.example.repoviewer

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*

class RepositoryListAdapter(private val repositoryList: List<Repository>) :
    RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repositoryList[position]
        holder.bind(item)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvLanguage: TextView = itemView.findViewById(R.id.tvLanguage)
        private val ivLanguageCircle: ImageView = itemView.findViewById(R.id.language_circle)
        private val tvStarred: TextView = itemView.findViewById(R.id.tvStarred)
        private val tvContributors: TextView = itemView.findViewById(R.id.tvContributors)

        fun bind(item: Repository) {
            tvName.text = item.name
            tvName.setOnClickListener {
                goToRepoActivity(item)
            }

            if (item.language != null) {
                tvLanguage.text = item.language
                ivLanguageCircle.setColorFilter(generateRandomColor())
                ivLanguageCircle.visibility = View.VISIBLE
                tvLanguage.visibility = View.VISIBLE
                ivLanguageCircle.scaleX = 1f
                tvLanguage.scaleX = 1f
            } else {
                tvLanguage.text = ""
                ivLanguageCircle.visibility = View.INVISIBLE
                ivLanguageCircle.scaleX = 0f
                tvLanguage.visibility = View.INVISIBLE
                tvLanguage.scaleX = 0f
            }

            tvDescription.text = item.description
            tvStarred.text = item.stargazers_count.toString()
            tvContributors.text = item.forks_count.toString()
        }

        private fun generateRandomColor(): Int {
            val rnd = Random()
            val r = rnd.nextInt(75) + 130
            val g = rnd.nextInt(75) + 130
            val b = rnd.nextInt(75) + 130

            return Color.argb(255, r, g, b)
        }

        private fun goToRepoActivity(pojo: Repository) {
            val intent = Intent(itemView.context, RepositoryDetailsActivity::class.java)
            val serializedRepo = Gson().toJson(pojo)
            intent.putExtra("repo", serializedRepo)
            startActivity(itemView.context, intent, null)
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item, parent, false)

                return ViewHolder(view)
            }
        }
    }
}
