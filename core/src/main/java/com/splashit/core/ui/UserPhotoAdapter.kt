package com.splashit.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.splashit.core.R
import com.splashit.core.databinding.UserPhotoItemLayoutBinding
import com.splashit.core.ui.model.PhotoModel
import java.util.*

class UserPhotoAdapter(val callback: ((PhotoModel) -> Unit)? = null) :
    RecyclerView.Adapter<UserPhotoAdapter.PhotoViewHolder>() {
    private var list = ArrayList<PhotoModel>()

    fun setData(newListData: List<PhotoModel>?) {
        if (newListData == null) return
        list.clear()
        list.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(
        val binding: UserPhotoItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PhotoModel) {
            with(binding) {
                photoCard.setOnClickListener { callback?.invoke(data) }
                Glide.with(binding.root.context)
                    .load(data.url)
                    .apply(
                        RequestOptions.placeholderOf(R.color.grey)
                            .error(R.color.grey)
                    )
                    .into(binding.photo)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserPhotoItemLayoutBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(list[position])
    }
}