package com.example.travenor.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travenor.R
import com.example.travenor.model.Place

class PlaceAdapter : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    private var places: List<Place> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Place>) {
        places = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView = itemView.findViewById(R.id.ivPlaceImage)
        private val tvName: TextView = itemView.findViewById(R.id.tvPlaceName)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvPlaceLocation)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPlacePrice)

        fun bind(place: Place) {
            tvName.text = place.name
            tvLocation.text = place.location
            ivImage.setImageResource(place.imageRes)

            // Форматування ціни (різні кольори)
            val priceText = "<font color='#2DB4EB'>$${place.price}</font><font color='#7D848D'>/Person</font>"
            tvPrice.text = Html.fromHtml(priceText, Html.FROM_HTML_MODE_COMPACT)
        }
    }
}