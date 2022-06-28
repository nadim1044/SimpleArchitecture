package com.example.practicedemo.mvvm.model.response

data class Film(
    val age_rating: List<AgeRating>,
    val film_id: Int,
    val film_name: String,
    val images: Images,
    val imdb_id: Int,
    val imdb_title_id: String,
    val other_titles: Any,
    val version_type: String
)