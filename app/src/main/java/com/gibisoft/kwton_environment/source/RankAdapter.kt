package com.gibisoft.kwton_environment.source

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gibisoft.kwton_environment.R

class RankAdapter(val context: Context, val rankList: ArrayList<RankItem>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.rank_item , null)

        val rank = view.findViewById<TextView>(R.id.rank_rank)
        val name = view.findViewById<TextView>(R.id.rank_name)
        val score = view.findViewById<TextView>(R.id.rank_score)

        val rankitem = rankList[p0]

        rank.text = rankitem.rank.toString()
        name.text = rankitem.name
        score.text = rankitem.score

        return view
    }

    override fun getItem(p0: Int): Any {
        return rankList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return rankList.size
    }
}