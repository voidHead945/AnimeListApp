package com.android.animeAndroidApp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.animeAndroidApp.models.Anime
import com.android.animeAndroidApp.models.Top
import com.android.animeAndroidApp.repository.AnimeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var animeRepository: AnimeRepository

    private var _topAnimes = MutableLiveData<Top>()
    val topAnimes: LiveData<Top>
    get() = _topAnimes


    var dataFetched: Boolean = false


    private var _anime = MutableLiveData<Anime>()
    val anime: LiveData<Anime>
    get() = _anime


    init {
        GlobalScope.launch { setDataFetchValue() }
    }

//    suspend fun tempFun() {
//        Log.d("AnimApp", "Before ${animeRepository.dataFetched}")
//
//        if(animeRepository.dataFetched){
//            _topAnimes.value = animeRepository.toplist
//            Log.d("AnimeAppViewModel", _topAnimes.value!!.top[0].title)
//        }
//    }


//    public fun setTopAnimes(top: Top?){
//        //_topAnimes = MutableLiveData<Top>(top)
//        val topL: Top? = top
//    }

    suspend fun setDataFetchValue(){
        animeRepository = AnimeRepository()
        delay(6000)
        _topAnimes.postValue(animeRepository.toplist)
        Log.d("AnimApp", "After ${animeRepository.dataFetched} " +
                "${animeRepository.toplist.toString()}")
    }

    public fun setAnime(tAnime: Anime){
        _anime.value = tAnime
    }

}