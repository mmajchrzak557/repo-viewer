package com.example.repoviewer

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.util.Linkify
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.tiagohm.markdownview.css.styles.Github
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_repo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


class RepositoryDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repoJson = intent.getStringExtra("repo")
        val repo = Gson().fromJson(repoJson, Repository::class.java)
        repo.validate()

        setContentView(R.layout.activity_repo)
        title = repo.name

        initializeTextViews(repo)

        val clientInstance = RetrofitClientInstance().getRetrofitInstance()
        val service = clientInstance.create(GithubService::class.java)
        getCommitActivityFromAPI(service, repo.name)
        getReadmeFromAPI(service, repo.name)
    }

    private fun getCommitActivityFromAPI(service: GithubService, repoName: String) {
        val call = service.getYearlyCommitActivity(repoName)

        call.enqueue(object : Callback<List<WeeklyCommitActivity>> {
            override fun onFailure(call: Call<List<WeeklyCommitActivity>>, t: Throwable) {
                Toast.makeText(applicationContext, R.string.toast_commit_activity_error, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<WeeklyCommitActivity>>, response: Response<List<WeeklyCommitActivity>>) {
                if (response.code() == 200) {
                    val activityChartView = CommitActivityChart(response.body()!!, applicationContext)
                    frame.addView(activityChartView)
                    return
                }
                Toast.makeText(applicationContext, R.string.toast_commit_activity_error, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getReadmeFromAPI(service: GithubService, repoName: String) {
        val readmeCall = service.getReadme(repoName)
        readmeCall.enqueue(
            object : Callback<Readme> {
                override fun onFailure(call: Call<Readme>, t: Throwable) {
                    Toast.makeText(applicationContext, R.string.toast_readme_error, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Readme>, response: Response<Readme>) {
                    parseReadmeResponse(response)
                }
            })
    }

    private fun parseReadmeResponse(response: Response<Readme>) {
        val markdownString = if (response.code() == 200) {
            val decodedContent = Base64.decode(response.body()!!.content, Base64.DEFAULT)
            String(decodedContent, StandardCharsets.UTF_8)

        } else {
            resources.getString(R.string.markdown_no_readme)
        }

        markdown_view.addStyleSheet(Github())
        markdown_view.loadMarkdown(markdownString)
    }

    private fun initializeTextViews(repo: Repository) {
        tvName.text = formatTextViewString(R.string.label_name, repo.name)
        tvDescription.text = formatTextViewString(R.string.label_description, repo.description!!)
        tvLanguage.text = formatTextViewString(R.string.label_language, repo.language!!)
        tvOwner.text = formatTextViewString(R.string.label_owner, repo.owner.login)
        tvUrl.text = formatTextViewString(R.string.label_url, repo.html_url)
        Linkify.addLinks(tvUrl, Linkify.WEB_URLS)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ENGLISH)
        tvPushedAt.text =
            formatTextViewString(R.string.label_pushed_at, dateFormat.format(repo.pushed_at))
        tvUpdatedAt.text =
            formatTextViewString(R.string.label_updated_at, dateFormat.format(repo.updated_at))
        tvWatchers.text =
            formatTextViewString(R.string.label_watchers, repo.watchers_count.toString())
        tvForks.text = formatTextViewString(R.string.label_forks, repo.forks_count.toString())
        tvStarred.text =
            formatTextViewString(
                R.string.label_stars, repo.stargazers_count.toString())
    }

    private fun formatTextViewString(id: Int, insert: String): Spanned {
        val baseString = resources.getString(id, insert)
        return Html.fromHtml(baseString, Html.FROM_HTML_MODE_LEGACY)
    }

    data class Readme(val content: String)
}