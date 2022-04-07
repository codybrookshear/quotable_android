package com.example.kotlinbasic.fragments

import CustomAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinbasic.ItemsViewModel
import com.example.kotlinbasic.R
import com.example.kotlinbasic.databinding.FragmentFirstBinding
import com.example.kotlinbasic.retrofit.QuotesApi
import com.example.kotlinbasic.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //private var _inflater : LayoutInflater? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        //_inflater = inflater;
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
        val data = ArrayList<ItemsViewModel>()
        GlobalScope.launch(Dispatchers.Main) {
            val result = quotesApi.getQuotes()

            val resultList = result.body()!!.results

            for (i in resultList.indices) {
                data.add(
                    ItemsViewModel(resultList[i].author, resultList[i].content)
                )
            }
        }

        val adapter = CustomAdapter(data)
        recyclerview.adapter = adapter
    }
//
//                val row = TableRow(activity)
//                val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
//                row.layoutParams = lp
//
//                val author = TextView(activity)
//                author.text = resultList[i].author
//                row.addView(author)
//
//                val quote = TextView(activity)
//                quote.text = resultList[i].content
//                row.addView(quote)
//
//                quotesTable.addView(row);


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}