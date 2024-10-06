package com.example.demoapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.demoapp.data.model.Movie
import com.example.demoapp.databinding.ItemMovieBinding
import com.example.demoapp.utils.StringUtil
import javax.inject.Inject

class MovieAdapter @Inject constructor(private val glide: RequestManager) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(diffCallback) {

     private var onClick : ((Int) -> Unit)? = null

     init {
        setHasStableIds(true)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }

     fun setOnClick(onClick : (Int) -> Unit) {
         this.onClick = onClick
     }

     override fun getItemId(position: Int): Long = getItem(position).id!!

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(private val itemBinding : ItemMovieBinding): RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.setOnClickListener {
                onClick?.invoke(bindingAdapterPosition)
            }
        }

        fun bind(movie : Movie) {
            with(itemBinding) {
                itemName.text = movie.title
                itemDescription.text = movie.overview
                itemRating.text = movie.voteAverage
                glide.load(movie.posterPath?.let {
                    StringUtil.buildImageUrl(it)
                }).into(itemBinding.itemImage)
            }
        }
    }

}