package com.example.repoviewer

import java.util.*

data class Repository(
    val id: Int,
    val name: String,
    var language: String?,
    var description: String?,
    val owner: Owner,
    val html_url: String,
    val forks_count: Int,
    val stargazers_count: Int,
    val watchers_count: Int,
    val pushed_at: Date,
    val updated_at: Date
    ) {
    fun validate(){
        if(description == null){
            this.description = "Description not provided"
        }
        if(language == null){
            language = " - "
        }
    }

    data class Owner(val login: String)
}
