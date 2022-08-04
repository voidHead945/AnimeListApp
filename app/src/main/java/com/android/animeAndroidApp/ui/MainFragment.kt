package com.android.animeAndroidApp.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.animeAndroidApp.MainRvAdapter
import com.android.animeAndroidApp.R
import com.android.animeAndroidApp.databinding.FragmentMainBinding
import com.android.animeAndroidApp.viewmodel.ContentInfoViewModel
import com.android.animeAndroidApp.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var contentInfoViewModel: ContentInfoViewModel
    lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(
            requireActivity().application)).get(MainViewModel::class.java)
        contentInfoViewModel = ViewModelProvider(this).get(ContentInfoViewModel::class.java)


        binding.recyclerView.layoutManager = LinearLayoutManager(activity)


        mainViewModel.topAnimes.observe(viewLifecycleOwner, Observer { topList ->
            if(topList != null){
                Log.d("AnimeAppMain", "MainActivity ${topList.top[0].title}" )
                val adapter = MainRvAdapter(mainViewModel.topAnimes.value!!.top, this, mainViewModel)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }else{
                Log.d("AnimeAppMain", "It's sad")
            }
        })



        binding.searchBar.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(binding.searchBar.windowToken, 0)

                Toast.makeText(activity, "${binding.searchBar.text.toString()}", Toast.LENGTH_SHORT).show()
                true
            }else{
                Toast.makeText(activity, "Search Bar is not working!", Toast.LENGTH_SHORT).show()
                false
            }
        }





    }


    public fun gotoContentFragment(){

        bundle = Bundle()
        val mainFragment = MainFragment()
        bundle.putString("ANIME_NAME", mainViewModel.anime.value?.title)
        bundle.putString("ANIME_EPISODE", mainViewModel.anime.value?.episodes.toString())
        bundle.putString("ANIME_IMAGE_URL", mainViewModel.anime.value?.image_url)
        bundle.putString("ANIME_ID", mainViewModel.anime.value?.mal_id.toString())
        //mainFragment.arguments = bundle


//        val contentInfoFragment = ContentInfoFragment()
//        val transaction = childFragmentManager.beginTransaction()
//        transaction.replace(R.id.mainFragment, contentInfoFragment, contentInfoFragment.tag)
//        transaction.disallowAddToBackStack()
//        transaction.commit()

        //GlobalScope.launch { contentInfoViewModel.setTheAnimeId(mainViewModel.anime.value?.mal_id.toString()) }
        findNavController().navigate(R.id.action_mainFragment_to_contentInfoFragment, bundle)
        Log.d("AnimeAppMainFragment", "The call to the main fragment is also working")
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}