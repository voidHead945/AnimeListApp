package com.android.animeAndroidApp.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.animeAndroidApp.models.Anime
import com.android.animeAndroidApp.models.Pictures
import com.android.animeAndroidApp.models.Top
import com.android.animeAndroidApp.repository.AnimeRepository
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import okhttp3.*
import okhttp3.internal.wait
import java.io.IOException

object AnimeService {

    var tops: Top? = null
    var anime: Anime? = null
    var pictures: Pictures? = null

    //val animeRepository = AnimeRepository()

    fun getTopAnime() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://jikan1.p.rapidapi.com/top/anime/1/airing")
            .get()
            .addHeader("X-RapidAPI-Host", "jikan1.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "dade1f31e8mshe8b49acf891ea7bp10b7bbjsncdc61e9c7e27")
            .build()


       client.newCall(request).enqueue(object: Callback {
           override fun onFailure(call: Call, e: IOException) {
               Log.d("AnimeApp", "Error On Network Request", e)
           }

           override fun onResponse(call: Call, response: Response) {
               val body = response.body?.string()

               val gson = GsonBuilder().create()
               tops = gson.fromJson(body, Top::class.java)
               Log.d("AnimeApp", tops!!.top[0].title)
               //animeRepository.setUpData(top)
               //animeRepository
           }


       })
    }


    fun getAnime(animeId: String, subType: String){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://jikan1.p.rapidapi.com/anime/$animeId/$subType")
            .get()
            .addHeader("X-RapidAPI-Key", "dade1f31e8mshe8b49acf891ea7bp10b7bbjsncdc61e9c7e27")
            .addHeader("X-RapidAPI-Host", "jikan1.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("AnimeApp", "Error On Anime Network Request", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                anime = gson.fromJson(body, Anime::class.java)
            }

        })
    }


    //Some time is taken to fetch the data
    fun getAnimePictures(animeId: String){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://jikan1.p.rapidapi.com/anime/$animeId/pictures")
            .get()
            .addHeader("X-RapidAPI-Key", "dade1f31e8mshe8b49acf891ea7bp10b7bbjsncdc61e9c7e27")
            .addHeader("X-RapidAPI-Host", "jikan1.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("AnimeApp", "Error On Anime Network Request", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()

                pictures = gson.fromJson(body, Pictures::class.java)
                //Log.d("AnimeAppService", pictures.toString())
            }

        })
    }


    public fun getTopList(): Top? {
        return tops
    }

    public fun getTheAnime(): Anime?{
        return anime
    }

    public fun getThePictures(): Pictures?{
        return pictures
    }
}