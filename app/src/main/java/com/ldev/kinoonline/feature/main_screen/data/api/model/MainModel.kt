package com.ldev.kinoonline.feature.main_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class MainModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MoveModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)