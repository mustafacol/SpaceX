package com.mustafacol.spacex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mustafacol.spacex.R
import com.mustafacol.spacex.adapter.LaunchesAdapter.LaunchViewHolder
import com.mustafacol.spacex.data.LaunchItem
import okhttp3.internal.notify

class LaunchesAdapter(private val launchList: List<LaunchItem>) :
    RecyclerView.Adapter<LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_launch, parent, false)
        return LaunchViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val launchItem = launchList[position]
        holder.bindView(launchItem)
    }

    override fun getItemCount(): Int = launchList.size

    class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val launchCardLayout: ConstraintLayout =
            itemView.findViewById(R.id.launch_card_layout)
        private val launchThumbnail: ImageView = itemView.findViewById(R.id.imageview_launch_icon)
        private val launchName: TextView = itemView.findViewById(R.id.launch_mission_name)
        private val launchLaunchDate: TextView =
            itemView.findViewById(R.id.launch_mission_date)

        fun bindView(launchItem: LaunchItem) {
            launchThumbnail.load(launchItem.imageUrl) {
                crossfade(500)
                error(R.drawable.rocket_launch)
            }
            launchName.text = launchItem.missionName
            launchLaunchDate.text = launchItem.launchDateLocal
        }
    }

}