package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
class MainActivity : AppCompatActivity() {

    //1, Define a data model class as the data source - DONE; Movie
    //2. Add the recyclerView to the layout - DONE
    //3. Create a custom row layout XML file to visualize the item -DONE
    //4. Create an Adapter and ViewHolder to render the item
    //5. Bind the adapter to the data source to populate the RecyclerView
    //6. Bind a layout manager tothe RecyclerView

    //making private instance variable; dont want it to be changed out of here
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)


        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)


        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {

                Log.e(TAG,  "onFailure $statusCode") //error log
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON data $json")
                try{
                    val movieJsonArray = json.jsonObject.getJSONArray("results");
                    //now need to parse JSON data; send to calss that  can do this for us
                    movies.addAll(Movie.fromJSONArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie List $movies")
                } catch (e : JSONException){
                    Log.e(TAG, "Encountered exception $e")
                }



            }

        }) //making get request
    }
}