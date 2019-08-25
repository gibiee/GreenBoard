package com.gibisoft.kwton_environment.source

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gibisoft.kwton_environment.R
import com.gibisoft.kwton_environment.TimelineItem
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.timeline_item.view.*

class TimelineAdapter(val context: Context, val timelineList: ArrayList<TimelineItem>) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.timeline_item , null)

        val profileImage = view.findViewById<ImageView>(R.id.profileImage)
        val photo = view.findViewById<ImageView>(R.id.picture)

        val id = view.findViewById<TextView>(R.id.profileId)
        val message = view.findViewById<TextView>(R.id.message)
        val date = view.findViewById<TextView>(R.id.date)
        val treeComment = view.findViewById<TextView>(R.id.treeComment)

        val timeline = timelineList[p0]

        Glide.with(context)
            .load("https://storage.googleapis.com/kwton-c336f.appspot.com/profile_image/${timeline.id}.jpg")
            .into(profileImage)

        Glide.with(context)
            .load("https://storage.googleapis.com/kwton-c336f.appspot.com/timeline_picture/${timeline.date}.jpg")
            .into(photo)

        id.text = timeline.id
        message.text = timeline.text
        date.text = timeline.date
        treeComment.text = "트리 ${timeline.tree}그루    댓글 ${timeline.comment}개"

        view.treeButton.setOnClickListener {
            view.treeButton.setBackgroundResource(R.drawable.tree_color)
        }

        return view
    }
    override fun getItem(p0: Int): Any {
        return timelineList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return timelineList.size
    }
}