package com.mustafacol.spacex.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mustafacol.spacex.R
import com.mustafacol.spacex.adapter.LaunchesAdapter.LaunchViewHolder
import com.mustafacol.spacex.data.LaunchItem
import com.mustafacol.spacex.ui.launch_list.LaunchListFragmentDirections
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LaunchesAdapter(
    private val launchList: List<LaunchItem>,
    private val onEndOfListReached: (() -> Unit)
) :
    RecyclerView.Adapter<LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_launch, parent, false)
        return LaunchViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val launchItem = launchList[position]
        holder.bindView(launchItem)

        if (position == launchList.size - 1) {
            onEndOfListReached.invoke()
        }
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

            launchLaunchDate.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val localDateTime =
                    LocalDateTime.parse(launchItem.launchDateLocal?.substringBeforeLast("-"))
                val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
                formatter.format(localDateTime)

            } else {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("MMMM dd, yyyy")
                formatter.format(parser.parse(launchItem.launchDateLocal));
            }

            //launchLaunchDate.text = launchItem.launchDateLocal

            launchCardLayout.setOnClickListener {
                val launchId = launchItem.id!!
                val action =
                    LaunchListFragmentDirections.actionLaunchListFragmentToLaunchDetailsFragment()
                        .apply {
                            this.launchId = launchId
                        }
                itemView.findNavController().navigate(action)
            }
        }
    }

}