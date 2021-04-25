package com.example.repoviewer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/allegro/repos?per_page=100")
    fun getAllRepos() : Call<List<Repository>>

    @GET("repos/allegro/{repo}/stats/commit_activity")
    fun getYearlyCommitActivity(@Path("repo") repo: String) : Call<List<WeeklyCommitActivity>>

    @GET("repos/allegro/{repo}/readme")
    fun getReadme(@Path("repo") repo: String) : Call<RepositoryDetailsActivity.Readme>
}