package com.app.modules.module4.pr3.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryModel(
    val id: Long,
    val full_name: String,
    val description: String,
    val stargazers_count: Int,
    val language: String
)