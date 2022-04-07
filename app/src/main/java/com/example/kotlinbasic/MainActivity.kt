

package com.example.kotlinbasic

import CustomAdapter
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinbasic.retrofit.QuotesApi
import com.example.kotlinbasic.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var count = 0
    var page = 1  // start with page 1 of the quotes
    private val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
    private val data = ArrayList<ItemsViewModel>()
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerview)
        val loadingPB = findViewById<ProgressBar>(R.id.idPBLoading)
        val nestedSV = findViewById<NestedScrollView>(R.id.idNestedSV)
        recyclerview.layoutManager = LinearLayoutManager(this)

        getData(page++)

        nestedSV.setOnScrollChangeListener {
                ns: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == ns.getChildAt(0).measuredHeight - ns.measuredHeight) {
                count++
                loadingPB.visibility = View.VISIBLE
                if (count < 20) {
                    getData(page++)
                }
            }
        }
    }

    fun getData(page : Int) {

        GlobalScope.launch(Dispatchers.Main) {
            val result = quotesApi.getQuotes(page)

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