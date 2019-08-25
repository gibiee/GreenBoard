package com.gibisoft.kwton_environment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gibisoft.kwton_environment.source.TimelineAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.login.view.*
import kotlinx.android.synthetic.main.timeline.view.*
import kotlinx.android.synthetic.main.timeline_item.view.*

class Timeline : Fragment() {
    private lateinit var mainActivity : MainActivity
    private var timelineList = arrayListOf<TimelineItem>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.timeline, null)

        val db = FirebaseFirestore.getInstance()
        db.collection("timeline")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for(data in result) {
                    timelineList.add(TimelineItem("${data.get("writer")}",
                        "${data.get("text")}",
                        "${data.get("date")}",
                        "${data.get("tree")}",
                        "${data.get("comment")}"))
                }

                val timelineAdapter = TimelineAdapter(mainActivity, timelineList)
                view.timelineListView.adapter = timelineAdapter
            }

        view.writeButton.setOnClickListener {
            mainActivity.onFragmentChange("글작성")
        }

        view.rankButton.setOnClickListener {
            mainActivity.onFragmentChange("랭크")
        }

        view.infoButton.setOnClickListener {
            mainActivity.onFragmentChange("인포")
        }
        return view
    }
}