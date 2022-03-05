package com.example.flixster

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private const val TAG = "MovieAdapter"
const val MOVIE_EXTRA = "MOVIE_EXTRA"
class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    //Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create viewholder of type
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }
    //Cheap operation: bind dtat to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //bind data into viewholder
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)

    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        //every time we vreate view holder we sill set onlclick lsiterner
        init{
            itemView.setOnClickListener(this) //this refers to the class
        }

        fun bind(movie: Movie) {
            //get referenced to ind comp in item view
            tvTitle.text = movie.title
            tvOverview.text = movie.overview

            //TODO: populaet imageview
            val radius = 30; // corner radius, higher value = more rounded
            val margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context).load(movie.posterImageUrl).centerCrop().transform(RoundedCornersTransformation(radius, margin)).into(ivPoster)
        }
        override fun onClick(v : View?){
        //1. Get notified of the particular movie which was clicked
            val movie = movies[adapterPosition]
            //Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
        //2. Use the intent system to naviagte ro the new activity
            val intent = Intent(context, DetailActivity::class.java)
           //want to pass all info at once
            intent.putExtra(    MOVIE_EXTRA, movie)
            context.startActivity(intent)



        }
    }
}
