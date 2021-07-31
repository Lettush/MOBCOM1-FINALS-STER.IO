package com.example.sterio

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var uID: TextView? = null
    private var uReview: TextView? = null
    private var uRating: TextView? = null

    init{
        uID = itemView.findViewById(R.id.lblID)
        uReview = itemView.findViewById(R.id.lblReview)
        uRating= itemView.findViewById(R.id.lblRating)
    }

    fun bind(user: User){
        uID?.text = user.id.toString()
        uReview?.text = user.review.toString()
        uRating?.text = user.rating.toString()
    }
}