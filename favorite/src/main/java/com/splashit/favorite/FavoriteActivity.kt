package com.splashit.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.splashit.core.ui.PhotoAdapter
import com.splashit.core.ui.model.toPresentationModel
import com.splashit.detail.DetailPhotoActivity
import com.splashit.favorite.databinding.ActivityFavoriteBinding
import com.splashit.favorite.di.favModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var mAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favModule)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)
        mAdapter = PhotoAdapter { data ->
            val intent = Intent(this, DetailPhotoActivity::class.java)
            intent.putExtra(DetailPhotoActivity.PHOTO, data)
            startActivity(intent)
        }
        with(dataBinding) {
            photoRv.apply {
                layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
                adapter = mAdapter
            }
        }
        viewModel.favPhotoList.observe(this, {
            if (it.isNotEmpty()) {
                mAdapter.setData(it.map { it.toPresentationModel() })
            } else {
                dataBinding.message.apply {
                    visibility = View.VISIBLE
                    text = context.getString(com.splashit.core.R.string.no_data)
                }
            }
        })
    }
}