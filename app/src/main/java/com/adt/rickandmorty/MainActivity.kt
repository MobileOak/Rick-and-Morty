package com.adt.rickandmorty

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity(), ResponseHandler {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CharacterAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))



        viewManager = LinearLayoutManager(this)

        viewAdapter = CharacterAdapter(emptyArray(), this)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        val networkManager = NetworkManager(this, this)
        networkManager.requestCharacters()
    }

    override fun onDataLoaded(root: Root) {
        val characterArray = root.results?.toTypedArray() ?: emptyArray()
        viewAdapter.updateData(characterArray)
        viewAdapter.notifyDataSetChanged()
    }
}