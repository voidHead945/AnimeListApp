package com.android.animeAndroidApp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.animeAndroidApp.models.Anime
import com.android.animeAndroidApp.models.Pictures
import com.android.animeAndroidApp.repository.AnimeRepository
import com.android.animeAndroidApp.ui.MainActivity
import com.android.animeAndroidApp.ui.MainFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContentInfoViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    var animeRepository = AnimeRepository()

    var animeId: String = ""

    var _animePictures = MutableLiveData<Pictures>()
    val animePictures: LiveData<Pictures>
    get() = _animePictures

    private var _animePicturesFetched = MutableLiveData<Boolean>()
    val animePicturesFetched: LiveData<Boolean>
    get() = _animePicturesFetched

    private val pictureObserver = Observer<Pictures?> {
        setPictureValue()
    }


    init {
        animeRepository.pictures.observeForever(pictureObserver)
    }

    suspend fun setTheAnimeId(value: String){
        animeId = value
        Log.d("AnimeAppContentViwMod", "The Anime Id Is $animeId")
        GlobalScope.launch { animeRepository.setUpAnimePictures(animeId) }

        Log.d("AnimeAppContentViwMod", animePicturesFetched.value.toString())
    }

     fun setPictureValue(){
        _animePictures.value = animeRepository.pictures.value
         GlobalScope.launch { dataFetched() }
        Log.d("AnimeAppContentViwMod", "The Anime Picture is ${animePictures.value?.pictures} and ${animePicturesFetched.value}")
    }

    suspend fun dataFetched(){
        delay(200)
        if(_animePictures.value != null){
            _animePicturesFetched.postValue(true)
            Log.d("AnimeAppContentViwMod", "Fectched the data so what's the problem ${animePicturesFetched.value}")
        }
    }

    override fun onCleared() {
        animeRepository.pictures.removeObserver(pictureObserver)
        super.onCleared()
    }

}