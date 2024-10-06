package com.example.demoapp.ui.details

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.demoapp.R
import com.example.demoapp.data.model.Movie
import com.example.demoapp.databinding.ItemMovieSmallBinding
import com.example.demoapp.utils.StringUtil
import javax.inject.Inject

internal class SimilarMovieAdapter @Inject constructor(private val glide : RequestManager):
            ListAdapter<Movie, SimilarMovieAdapter.SimilarMovieViewHolder>(diffCallback) {

    private var onClick : ((Int) -> Unit)? = null

    init{
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieAdapter.SimilarMovieViewHolder {
        val binding = ItemMovieSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMovieAdapter.SimilarMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SimilarMovieViewHolder(private val itemBinding : ItemMovieSmallBinding): RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.setOnClickListener {
                onClick?.invoke(bindingAdapterPosition)
            }
        }

        fun bind(movie : Movie) {
            with(itemBinding) {
                val cornerRadius =itemBinding.root.context.resources.getDimension(R.dimen.dimen_16dp)
                glide.load(movie.posterPath?.let {
                    StringUtil.buildImageUrl(it) }).transform(RoundedCorners(cornerRadius.toInt()))
                    .placeholder(ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bg_placeholder))
                    .into(image)
                title.text = movie.title
                releaseDate.text = movie.releaseDate
                rating.text = movie.voteAverage
            }
        }
    }
}