package com.android.animeAndroidApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.animeAndroidApp.MainRvAdapter
import com.android.animeAndroidApp.R
import com.android.animeAndroidApp.databinding.ActivityMainBinding
import com.android.animeAndroidApp.repository.AnimeRepository
import com.android.animeAndroidApp.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    var mainFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainFragment = MainFragment()


        //GlobalScope.launch { mainViewModel.tempFun() }
    }


    public fun openContentFragment(){
        mainFragment?.gotoContentFragment()
        Log.d("AnimeAppMain", "The call to main from adapter is working $mainFragment")
    }


}


