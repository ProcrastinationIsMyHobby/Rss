package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.adapter.FeedAdapter
import com.example.test.common.HTTPDataHandler
import com.example.test.model.RSSObject
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity() {

    private var rssLink:String = ""
    private val rssToJsonAPI:String = "https://api.rss2json.com/v1/api.json?rss_url="
    lateinit var rssObject: RSSObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val arguments = intent.extras
        rssLink =  arguments!!.get("url").toString()

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(baseContext,
            LinearLayoutManager.VERTICAL,false)

        recycler.layoutManager = linearLayoutManager

        CoroutineScope(Dispatchers.Main).launch {
            loadRRS()
        }
    }

    private suspend fun loadRRS() {

        var res2:String? =""

        CoroutineScope(Dispatchers.IO).launch {
            val res = async {
                val http = HTTPDataHandler()
                http.getHTTPData(rssToJsonAPI + rssLink)
            }
            res2 = res.await()
        }.join()

        rssObject = Gson().fromJson(res2, RSSObject::class.java)
        val adapter = FeedAdapter(rssObject, baseContext)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}