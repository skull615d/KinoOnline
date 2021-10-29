package com.ldev.kinoonline.feature.main_screen.domain.model

data class Main(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)