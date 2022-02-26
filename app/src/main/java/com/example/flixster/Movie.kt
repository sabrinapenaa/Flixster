package com.example.flixster

import org.json.JSONArray

//want this class to represent one movie object
data class Movie(
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overview: String,
) {
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object{
        fun fromJSONArray(movieJsonArray: JSONArray) : List<Movie> {
            //want to iterate through array and return a list of the movie class types
            val movies = mutableListOf<Movie>()
            for(i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                        Movie(
                                movieJson.getInt("id"),
                                movieJson.getString("poster_path"),
                                movieJson.getString("title"),
                                movieJson.getString("overview")
                        )

                )
            }
            return movies
        }



    }
}