package com.gibisoft.kwton_environment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gibisoft.kwton_environment.source.RankAdapter
import com.gibisoft.kwton_environment.source.RankItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.rank.view.*

class Rank : Fragment() {
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.rank, null)

        view.timelineButton.setOnClickListener {
            mainActivity.onFragmentChange("타임라인")
        }

        view.infoButton.setOnClickListener {
            mainActivity.onFragmentChange("인포")
        }
        return view
    }
}