package com.splashit.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.splashit.R
import com.splashit.core.data.Resource
import com.splashit.core.ui.PhotoAdapter
import com.splashit.core.ui.model.toPresentationModel
import com.splashit.databinding.ActivityHomeBinding
import com.splashit.detail.DetailPhotoActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var mAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        mAdapter = PhotoAdapter { data ->
            val intent = Intent(this, DetailPhotoActivity::class.java)
            intent.putExtra(DetailPhotoActivity.PHOTO, data)
            startActivity(intent)
        }
        with(dataBinding) {
            photoRv.apply {
                layoutManager = GridLayoutManager(this@HomeActivity, 2)
                adapter = mAdapter
            }
            favButton.setOnClickListener {
                val uri = Uri.parse("splashit://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        viewModel.photoList.observe(this, {
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
    }
}