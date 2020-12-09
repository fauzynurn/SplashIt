package com.splashit.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.splashit.R
import com.splashit.core.data.Resource
import com.splashit.core.ui.UserPhotoAdapter
import com.splashit.core.ui.model.PhotoModel
import com.splashit.core.ui.model.toPresentationModel
import com.splashit.databinding.ActivityDetailPhotoBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailPhotoActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityDetailPhotoBinding
    private val viewModel: DetailPhotoViewModel by viewModel()
    private lateinit var photo: PhotoModel
    private lateinit var mAdapter: UserPhotoAdapter

    companion object {
        const val PHOTO = "photo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_photo)
        photo = intent.getParcelableExtra(PHOTO)!!
        setSupportActionBar(dataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mAdapter = UserPhotoAdapter { data ->
            val intent = Intent(this, DetailPhotoActivity::class.java)
            intent.putExtra(PHOTO, data)
            startActivity(intent)
        }
        dataBinding.userPhotoRv.apply {
            layoutManager =
                LinearLayoutManager(this@DetailPhotoActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        viewModel.getUserPhoto(photo.user.username).observe(this, {
            when (it) {
                is Resource.Loading -> dataBinding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    dataBinding.progressBar.visibility = View.GONE
                    mAdapter.setData(it.data?.map { it.toPresentationModel() })
                }
                is Resource.Error -> {
                    with(dataBinding) {
                        progressBar.visibility = View.GONE
                        error.visibility = View.VISIBLE
                        error.text = it.message ?: getString(R.string.fetch_data_failed)
                    }
                }
            }
        })
        Glide.with(dataBinding.root.context)
            .load(photo.url)
            .apply(
                RequestOptions.placeholderOf(com.splashit.core.R.color.grey)
                    .error(com.splashit.core.R.color.grey)
            )
            .into(dataBinding.photoView)
        Glide.with(dataBinding.root.context)
            .load(photo.user.profileImageUrl)
            .apply(
                RequestOptions.placeholderOf(com.splashit.core.R.color.grey)
                    .error(com.splashit.core.R.color.grey)
            )
            .into(dataBinding.profilePhoto)
        dataBinding.name.text = photo.user.name
        if (photo.user.instagramUsername.isNotEmpty() && photo.user.location.isNotEmpty()) {
            dataBinding.additionalInfo.text =
                String.format("@%s, %s", photo.user.instagramUsername, photo.user.location)
        } else if (photo.user.instagramUsername.isEmpty()) {
            dataBinding.additionalInfo.text = String.format("%s", photo.user.location)
        } else if (photo.user.location.isEmpty()) {
            dataBinding.additionalInfo.text = String.format("@%s", photo.user.instagramUsername)
        } else {
            dataBinding.additionalInfo.visibility = View.GONE
        }
        if (photo.description.isEmpty()) dataBinding.description.visibility = View.GONE
        applyFavButtonStyle()
        dataBinding.favButton.setOnClickListener {
            viewModel.setFavoritePhoto(photo)
            applyFavButtonStyle()
        }
        dataBinding.likes.text = applyLikeText()
        dataBinding.description.text = photo.description
        dataBinding.createdDate.text = photo.createdDate
        dataBinding.userPhotoLabel.text = String.format("More from %s", photo.user.name)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    fun applyLikeText(): String {
        return if (photo.isFav) String.format(
            "You and %d people like this.",
            photo.likes
        ) else String.format("%d people like this.", photo.likes)
    }

    fun applyFavButtonStyle() {
        if (photo.isFav) {
            dataBinding.favCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.pink))
            dataBinding.favImage.setImageResource(R.drawable.baseline_favorite_black_24dp)
            dataBinding.favImage.setColorFilter(ContextCompat.getColor(this, R.color.white))
        } else {
            dataBinding.favCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.whiteGrey
                )
            )
            dataBinding.favImage.setImageResource(R.drawable.baseline_favorite_border_black_24dp)
            dataBinding.favImage.setColorFilter(ContextCompat.getColor(this, R.color.black))
        }
        dataBinding.likes.text = applyLikeText()
    }
}