package com.example.news.ui.main.favourites

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.database.FavouriteArticlesDao
import com.example.news.data.database.RoomDatabase
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.FavouriteArticles
import com.example.news.databinding.FragmentFavouritesBinding
import com.example.news.ui.base.BaseFragment
import com.example.news.ui.main.home.HomeAdapter
import com.example.news.utils.SwipeToAdd
import com.example.news.utils.SwipeToDelete
import com.example.news.utils.invisible
import kotlinx.coroutines.DelicateCoroutinesApi

class FavouritesFragment : BaseFragment<FragmentFavouritesBinding, FavouritesViewModel>() {

    private lateinit var adapter: FavouritesAdapter
    private var listItem = listOf<FavouriteArticles>()

    @DelicateCoroutinesApi
    override fun onBound() {
        val dataSource = RoomDatabase.getDatabase(requireContext())
        setUp()
        setSwiper(dataSource.favouriteArticlesDao)
    }

    private fun setUp() {
        //setting adapter to fragment layout
        adapter = FavouritesAdapter()
        binding.rvFavouriteArticles.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.rvFavouriteArticles.adapter = adapter

        //getting data from database via viewModel and setting to adapter
        vm.getFavouriteArticle().observe(viewLifecycleOwner) { favouriteArticle ->
            binding.pbLoading.invisible()
            listItem = favouriteArticle
            adapter.setData(listItem)
        }
    }

    @DelicateCoroutinesApi
    fun setSwiper(dataSource: FavouriteArticlesDao){
        val swipeHandler = object : SwipeToDelete(requireContext(), dataSource) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val adapter = binding.rvFavouriteArticles.adapter as FavouritesAdapter
                Log.d("-------------", "onSwiped: id: ${viewHolder.adapterPosition}")
                super.getArticle(listItem[viewHolder.adapterPosition])
                adapter.setData(listItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvFavouriteArticles)
    }

    override fun getLayoutResId() = R.layout.fragment_favourites
    override val vm: FavouritesViewModel
        get() = ViewModelProvider(
            this,
            FavouritesViewModelFactory(RoomDatabase.getDatabase(requireContext()).favouriteArticlesDao)
        )[FavouritesViewModel::class.java]


}