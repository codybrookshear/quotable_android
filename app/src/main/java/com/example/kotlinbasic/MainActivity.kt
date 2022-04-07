

package com.example.kotlinbasic

import CustomAdapter
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinbasic.databinding.ActivityMainBinding
import com.example.kotlinbasic.retrofit.QuotesApi
import com.example.kotlinbasic.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemsViewModel>()

        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            val result = quotesApi.getQuotes()

            val resultList = result.body()!!.results

            for (i in resultList.indices) {
                data.add(
                    ItemsViewModel(resultList[i].author, resultList[i].content)
                )
            }

            val adapter = CustomAdapter(data)
            recyclerview.adapter = adapter
        }

    }
}