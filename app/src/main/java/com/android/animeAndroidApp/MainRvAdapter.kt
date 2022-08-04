package com.android.animeAndroidApp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.animeAndroidApp.models.Anime
import com.android.animeAndroidApp.models.Top
import com.android.animeAndroidApp.ui.MainActivity
import com.android.animeAndroidApp.ui.MainFragment
import com.android.animeAndroidApp.viewmodel.MainViewModel
import com.bumptech.glide.Glide

class MainRvAdapter(val topAnimeList: List<Anime>, val mainFragment: MainFragment, val mainViewModel: MainViewModel): RecyclerView.Adapter<MainRvAdapter.MainViewHolder>() {


    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rankText: TextView = itemView.findViewById(R.id.rank_text)
        val contentImage: ImageView = itemView.findViewById(R.id.content_Image)
        val titleText: TextView = itemView.findViewById(R.id.title_text)
        val contentTypeText: TextView = itemView.findViewById(R.id.content_type_text)
        val contentEpisodesText: TextView = itemView.findViewById(R.id.content_episodes_text)
        val contentStartDateText: TextView = itemView.findViewById(R.id.content_startDate_text)
        val contentScoreText: TextView = itemView.findViewById(R.id.content_score_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            rankText.text = topAnimeList[position].rank.toString()
            //contentImage
            titleText.text = topAnimeList[position].title
            contentTypeText.text = topAnimeList[position].type
            if(topAnimeList[position].episodes == null){
                contentEpisodesText.text = "Episodes: "
            }else{
                contentEpisodesText.text = "Episodes " + topAnimeList[position].episodes.toString()
            }

            if(topAnimeList[position].start_date == null){
                contentStartDateText.text = "Coming Soon"
            }else{
                contentStartDateText.text = topAnimeList[position].start_date
            }
            contentScoreText.text = "Score: " + topAnimeList[position].score + "/10"

            Glide
                .with(holder.itemView.context)
                .load(topAnimeList[position].image_url)
                .into(contentImage)
        }
        Log.d("AnimeRV", topAnimeList[position].title)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Anime is ${topAnimeList[position].title}", Toast.LENGTH_SHORT).show()
            mainViewModel.setAnime(topAnimeList[position])
            mainFragment.gotoContentFragment()
        }
    }

    override fun getItemCount(): Int {
        return topAnimeList.size
    }


}

interface IMainRvAdapter{
    fun onItemClicked()
}