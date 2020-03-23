package com.example.studentportal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_portal.view.*

class PortalsAdapter(
    private val reminders: ArrayList<Portal>,
    val clickListener: (Portal) -> Unit) :
    RecyclerView.Adapter<PortalsAdapter.ViewHolderCard>() {

    class ViewHolderCard(cardViewText: View) : RecyclerView.ViewHolder(cardViewText) {
        fun bind(portal: Portal, clickListener: (Portal) -> Unit) {
            itemView.portal_title.text = portal.titleText
            itemView.portal_url.text = portal.urlText
            itemView.setOnClickListener { clickListener(portal)}
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderCard {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_portal, parent, false)
        return ViewHolderCard(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolderCard, position: Int) {
        holder.bind(reminders[position], clickListener)
    }

    override fun getItemCount(): Int {
        return reminders.size
    }
}
