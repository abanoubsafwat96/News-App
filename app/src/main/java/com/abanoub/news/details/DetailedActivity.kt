package com.abanoub.news.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abanoub.news.R
import com.abanoub.news.base.ParentActivity
import com.abanoub.news.data.model.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detailed.*

class DetailedActivity : ParentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        var article=intent.getSerializableExtra("article") as Article
        initUI(article)
    }

    private fun initUI(article: Article?) {
        Glide.with(this)
            .load(article?.urlToImage)
            .placeholder(R.drawable.placeholder)
            .into(image)

        titleTv.text=article?.title
        description.text=article?.description
        source.text=article?.source?.name
        content.text=article?.content
        author.text=article?.author
        publishedAt.text=article?.publishedAt

        navigateBtn.setOnClickListener{
            val uri: Uri = Uri.parse(article?.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}