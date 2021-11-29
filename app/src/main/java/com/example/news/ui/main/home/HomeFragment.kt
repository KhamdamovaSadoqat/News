package com.example.news.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.database.FavouriteArticlesDao
import com.example.news.data.database.RoomDatabase
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.remote.Status
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.ui.base.BaseFragment
import com.example.news.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    InternetChecker.OnNetworkAvailableCallbacks {

    private lateinit var adapter: HomeAdapter
    private var listItem = listOf<ArticlesItem>()

    private var snackbar: InternetSnackbar? = null
    private var connectionStateMonitor: InternetChecker? = null

    @DelicateCoroutinesApi
    override fun onBound() {
        val dataSource = RoomDatabase.getDatabase(requireContext())
        setUp()
        setSwiper(dataSource.favouriteArticlesDao)
        setSnackbar()
    }

    private fun setUp() {
        adapter = HomeAdapter {
            Log.d("----------", "setUp: $it")
        }
        binding.rvArticles.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.rvArticles.adapter = adapter


        vm.getArticleRu().observe(this) { response ->

            when (response.status) {
                Status.LOADING -> {
                    Log.d("----------", "setUp: loading")
                    binding.pbLoading.visible()
                }
                Status.ERROR -> {
                    Log.d("----------", "setUp: error: ${response.message}")

                }
                Status.SUCCESS -> {
                    binding.pbLoading.invisible()
                    listItem = response.data?.articles as List<ArticlesItem>
                    adapter.setData(listItem)
                    Log.d("----------", "setUp: response: ${response.data.articles.size}")
                }
            }
        }
    }

    @DelicateCoroutinesApi
    fun setSwiper(dataSource: FavouriteArticlesDao) {
        val swipeHandler = object : SwipeToAdd(requireContext(), dataSource) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val adapter = binding.rvArticles.adapter as HomeAdapter
                Log.d("-------------", "onSwiped: id: ${viewHolder.adapterPosition}")
                super.getArticle(listItem[viewHolder.adapterPosition])
                adapter.setData(listItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvArticles)
    }

    private fun setSnackbar() {
        if (snackbar == null) {
            snackbar = InternetSnackbar.make(binding.cl, Snackbar.LENGTH_INDEFINITE)
        }

        if (connectionStateMonitor == null)
            connectionStateMonitor = InternetChecker(requireContext(), this)
        //Register
        connectionStateMonitor?.enable()

        // Recheck network status manually whenever activity resumes
        if (connectionStateMonitor?.hasNetworkConnection() == false) onNegative()
        else {
            onPositive()
        }
    }

    override fun onPause() {
        snackbar?.dismiss()
        snackbar = null
        //Unregister
        connectionStateMonitor?.disable()
        connectionStateMonitor = null
        super.onPause()
    }

    override fun onPositive() {
        if (isAdded)
            requireActivity().runOnUiThread {
                setUp()
                snackbar?.dismiss()
            }
    }

    override fun onNegative() {
        if (isAdded)
            requireActivity().runOnUiThread {
                snackbar?.setText(requireContext().getString(R.string.msg_no_network))?.show()
            }
    }

    override fun getLayoutResId() = R.layout.fragment_home
    override val vm: HomeViewModel
        get() = ViewModelProvider(this)[HomeViewModel::class.java]


}