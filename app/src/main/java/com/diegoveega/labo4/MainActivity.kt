package com.diegoveega.labo4

import android.graphics.Movie
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val  movieList: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun initRecyclerView(){
        viewManager = LinearLayoutManager(this)
        movie_list_rv.apply {
            TODO("es View en lugar de RecyclerView")
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = movieAdapter
        }
    }

    fun addMovieToList(movie: Movie) {
        movieList.add(movie)
        movieAdapter.changeList(movieList)
        Log.d("Number", movieList.size.toString())
    }


    private inner class fetchMovie : AsyncTask<String, Void, String>(){

        override fun doInBackground(vararg params: String): String {

            if(params.isNullOrEmpty()) return ""

            val movieName = params[0]
            val movieURL = NetworkUtils().buildSearchUrl(movieName)

            return try{
                NetworkUtils().getRespenseFromHttpUrl(movieURL)
            }catch (e: IOException){
                ""
            }

        }

        override fun onPostExecute(movieInfo: String?) {
            super.onPostExecute(movieInfo)

            if(!movieInfo.isEmpty()){
                val movieJson = JSONObject(movieInfo)

                if(movieJson.getString("Response")== "True"){
                    val movie = Gson().fromJson<Movie>(movieInfo, Movie::class.java)

                }else{
                    Snackbar.make(main_ll, "NO", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

}
