package com.example.repoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(RepositoryDataHolder.repos == null){
            getReposFromAPI()
            return
        }
        generateRepoList(RepositoryDataHolder.repos!!)
    }

    private fun getReposFromAPI(){
        val clientInstance = RetrofitClientInstance().getRetrofitInstance()
        val service = clientInstance.create(GithubService::class.java)
        val call = service.getAllRepos()

        call.enqueue(object: Callback<List<Repository>>{

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Toast.makeText(applicationContext, R.string.toast_api_error, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                parseResponse(response)
            }
        })
    }

    private fun parseResponse(response: Response<List<Repository>>){
        if(response.code() == 200) {
            generateRepoList(response.body()!!)
            RepositoryDataHolder.repos = response.body()!!
        } else if(response.code() == 403){
            Toast.makeText(applicationContext, R.string.toast_api_rate_limit, Toast.LENGTH_LONG).show()
        }
    }

    private fun generateRepoList(repoList: List<Repository>){
        val adapter = RepositoryListAdapter(repoList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}