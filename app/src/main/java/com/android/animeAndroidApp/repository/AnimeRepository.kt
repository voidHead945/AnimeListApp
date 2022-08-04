package com.android.animeAndroidApp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.animeAndroidApp.models.Anime
import com.android.animeAndroidApp.models.Pictures
import com.android.animeAndroidApp.models.Top
import com.android.animeAndroidApp.services.AnimeService
import com.android.animeAndroidApp.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimeRepository {

    // private val top: LiveData<Top>
    var toplist: Top? = null
    //val mainViewModel = MainViewModel()
    var dataFetched = false
    var pictureDataFetched = MutableLiveData<Boolean>(false)
    private val animeService = AnimeService

    var anime: Anime? = null
    var pictures = MutableLiveData<Pictures?>(null)

    init {
        animeService.getTopAnime()
        GlobalScope.launch { setUpData() }
        //Log.d("AnimeApp", "Running")
        //top = AnimeService.top as LiveData<Top>
        //top = animeService.getTop()
    }


    //Here we are using a delay to wait for the network response which is made in animeService
    //We are using a while loop because the time required for the network request is not always same
    //-so the while loop will help us keep asking for until we have a non null request
    suspend fun setUpData(){

        delay(2000)
        toplist = animeService.getTopList()

        while(toplist == null){
            delay(2000)
            Log.d("AnimApp", "Repo")
            toplist = animeService.getTopList()
        }


        if(toplist != null){
            dataFetched = true
            Log.d("AnimApp", "Data Fetched $dataFetched  ${toplist!!.top[0].title}")
        }


        //Log.d("AnimeAppRepo", "This is not ${toplist?.top!![0].title}")
        //mainViewModel.setTopAnimes(toplist)
    }


    suspend fun setUpAnimePictures(animeId: String){
        animeService.getAnimePictures(animeId)
        delay(2000)

        pictures.postValue(animeService.getThePictures())

        while(pictures.value == null){
            Log.d("AnimeAppRepo", "Picture is very very null")
            delay(2000)
            pictures.postValue(animeService.getThePictures())
        }


        if(pictures.value != null){
            pictureDataFetched.postValue(true)
            Log.d("AnimeAppRepo", "Picture Data Fetched")
            Log.d("AnimeAppRepo", pictures.value.toString())
        }

    }



    suspend fun setUpAnimeData(animeId: String, subType: String){
        animeService.getAnime(animeId, subType)
        delay(2000)

        anime = animeService.getTheAnime()

        while(anime == null){
            delay(2000)
            anime = animeService.getTheAnime()
        }
    }


}