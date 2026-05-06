package com.ekodex.manpowerhrms.News

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentNewsBinding
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsFragment : Fragment() {

    lateinit var binding: FragmentNewsBinding
    val newsApiClient = NewsApiClient("fae4a714259046b6a05995ef7a358b72")
    var newsList = mutableListOf<NewsData>()
    lateinit var adapter:NewsAdapter
    var searchList =  arrayListOf<NewsData>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        adapter = NewsAdapter(newsList)
        getNews("general",null)


        binding.button9.setOnClickListener {
            getNews("buisness",null)
        }

        binding.button10.setOnClickListener {
            getNews("entertainment",null)
        }

        binding.button11.setOnClickListener {
            getNews("general",null)
        }

        binding.button12.setOnClickListener {
            getNews("health",null)
        }

        binding.button13.setOnClickListener {
            getNews("science",null)
        }

        binding.button14.setOnClickListener {
            getNews("sports",null)
        }

        binding.button15.setOnClickListener {
            getNews("technology",null)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               getNews("general",newText)
                return true
            }
        })

        return binding.root

    }

    private fun getNews(category:String,search_keyword:String?) {

        newsList.clear()

        newsApiClient.getTopHeadlines(
            TopHeadlinesRequest.Builder()
                .category(category)
                .q(search_keyword)
                .language("en")
                .build(),
            object : NewsApiClient.ArticlesResponseCallback{
                override fun onSuccess(response: ArticleResponse?) {
                    binding.progressBar2.visibility = View.GONE
                    response!!.articles.forEach {

                        var url = ""
                        if (it.urlToImage != null) {
                            url = it.urlToImage
                        }
                        val news = NewsData(
                            "1",
                            url,
                            it.title,
                            it.source.name,
                            it.url
                        )
                        newsList.add(news)
                        // searchList = empDirectoryList as ArrayList<EmployeeDirectoryData>
                        adapter = NewsAdapter(newsList)
                        binding.newsList.adapter = adapter
                    }
                }
                override fun onFailure(throwable: Throwable?) {
                    Toast.makeText(requireContext(),throwable?.message.toString(),Toast.LENGTH_SHORT).show()
                }

            }
        )
        }



}
