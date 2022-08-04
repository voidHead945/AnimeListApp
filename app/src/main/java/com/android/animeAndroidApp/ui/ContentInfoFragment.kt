package com.android.animeAndroidApp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.android.animeAndroidApp.ImageRvAdapter
import com.android.animeAndroidApp.databinding.ContentInfoFragmentBinding
import com.android.animeAndroidApp.viewmodel.ContentInfoViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ContentInfoFragment : Fragment() {

    lateinit var binding: ContentInfoFragmentBinding

    private lateinit var viewModel: ContentInfoViewModel
    var animeId: String = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContentInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(
            requireActivity().application)).get(ContentInfoViewModel::class.java)


        var args = this.arguments

        if(args != null){
            var title = args?.getString("ANIME_NAME", "Anime Name")
            var animeEpisode = args?.getString("ANIME_EPISODE", "0")
            var animeImageUrl = args?.getString("ANIME_IMAGE_URL", "noImage")
            animeId = args?.getString("ANIME_ID", "0")

            binding.contentInfoNameText.text = title
            binding.contentInfoEpisodesText.text = "Episodes: $animeEpisode"
            Log.d("AnimeApp", title.toString() + "Content Fragment")
        }else{
            Log.d("AnimeApp", "args is null")
        }

        binding.imageRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.imageRecyclerView)


        viewModel.animePicturesFetched.observe(viewLifecycleOwner, Observer { animePicturesFetched ->
            if(animePicturesFetched == true){
                val adapter = ImageRvAdapter(viewModel._animePictures.value!!.pictures)
                binding.imageRecyclerView.adapter = adapter
                //viewModel.setPictureValue()
                adapter.notifyDataSetChanged()
                Log.d("AnimeAppContentFrag", "Picture Fetched")
            }else{
                Log.d("AnimeAppContentFrag", "Picture Not Fetched")
            }
        })



        GlobalScope.launch { setUpAnime() }
    }

    suspend fun setUpAnime(){
        viewModel.setTheAnimeId(animeId)
    }
}