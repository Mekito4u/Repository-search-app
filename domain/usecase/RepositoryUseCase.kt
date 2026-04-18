package com.app.modules.module4.pr3.domain.usecase

import android.content.Context
import com.app.modules.module4.pr3.domain.model.RepositoryModel
import kotlinx.serialization.json.Json

fun getRepositoryList(context: Context): List<RepositoryModel> {
    return try {
        val json = context.assets.open("github_repos.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<List<RepositoryModel>>(json)
    } catch (e: Exception) {
        println(e.message)
        emptyList()
    }
}