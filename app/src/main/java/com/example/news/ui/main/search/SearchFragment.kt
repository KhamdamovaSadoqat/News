package com.example.news.ui.main.search

import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.database.FavouriteArticlesDao
import com.example.news.data.database.RoomDatabase
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.remote.Status
import com.example.news.databinding.FragmentSearchBinding
import com.example.news.ui.base.BaseFragment
import com.example.news.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(),
    InternetChecker.OnNetworkAvailableCallbacks {

    private lateinit var adapter: SearchAdapter
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
        adapter = SearchAdapter()
        binding.rvSearchArticles.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.rvSearchArticles.adapter = adapter

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                vm.getSearchArticle(query).observe(this@SearchFragment) { response ->
                    when (response.status) {
                        Status.LOADING -> {
                            Log.d("----------", "setUp: loading")
                            binding.pbLoading.visible()
                        }
                        Status.ERROR -> {
                            binding.pbLoading.invisible()
                            Log.d("----------", "setUp: error")
                        }
                        Status.SUCCESS -> {
                            binding.pbLoading.invisible()
                            listItem = response.data!!.articles
                            adapter.setData(listItem)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                vm.getSearchArticle(newText).observe(this@SearchFragment) { response ->
                    when (response.status) {
                        Status.LOADING -> {
                        }
                        Status.ERROR -> {
                            binding.pbLoading.invisible()
                            Log.d("----------", "setUp: error")
                        }
                        Status.SUCCESS -> {
                            binding.pbLoading.invisible()
                            listItem = response.data!!.articles
                            adapter.setData(listItem)
                        }
                    }
                }
                return false
            }
        })
    }

    @DelicateCoroutinesApi
    fun setSwiper(dataSource: FavouriteArticlesDao) {
        val swipeHandler = object : SwipeToAdd(requireContext(), dataSource) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val adapter = binding.rvSearchArticles.adapter as SearchAdapter
                super.getArticle(listItem[viewHolder.adapterPosition])
                adapter.setData(listItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvSearchArticles)
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

    override fun getLayoutResId() = R.layout.fragment_search
    override val vm: SearchViewModel
        get() = ViewModelProvider(this)[SearchViewModel::class.java]


}