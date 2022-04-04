package com.rappi.technicalexercise.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.annotation.ExperimentalCoilApi
import com.rappi.technicalexercise.databinding.ActivityMainBinding
import com.rappi.technicalexercise.ui.common.PermissionRequester
import com.rappi.technicalexercise.ui.common.startActivity
import com.rappi.technicalexercise.ui.detail.DetailActivity
import com.rappi.technicalexercise.ui.main.MainViewModel.UiModel
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MoviesAdapter
    private val TAG = "MainActivity"
    private val coarsePermissionRequester =
        PermissionRequester(this, ACCESS_COARSE_LOCATION)
    private var isLocationRequest = false

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
        binding.btnConnection.setOnClickListener {retryConnection()}
    }

    private fun updateUi(model: UiModel) {

        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        binding.recycler.visibility = if (model is UiModel.Content || model is UiModel.Navigation) View.VISIBLE else View.GONE
        binding.btnConnection.visibility = if (model is UiModel.Error) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Error -> Toast.makeText(this@MainActivity, model.msg, Toast.LENGTH_LONG).show()
            is UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie.id)
            }
            UiModel.RequestLocationPermission -> syncRequest()
        }
    }

    private fun syncRequest(){
        coarsePermissionRequester.request {
            if(it){
                viewModel.onCoarsePermissionRequested()
            }else{
                isLocationRequest = true
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri: Uri = Uri.fromParts("package", this@MainActivity.packageName, null)
                intent.data = uri
                this@MainActivity.startActivity(intent)
            }
        }
    }

    private fun retryConnection(){
        syncRequest()
    }

    override fun onResume() {
        super.onResume()
        if(isLocationRequest){
            isLocationRequest = false
            Toast.makeText(this, "Es obligatorio los permisos de Acceso a la ubicación por favor", Toast.LENGTH_SHORT).show()
            syncRequest()
        }
    }
}