package com.example.repoviewer

object RepositoryDataHolder {
    // This is used to hold data after API call
    // Thanks to this, when coming back to Main Activity, API doesn't have to be called again
    var repos: List<Repository>? = null
}