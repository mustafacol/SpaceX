package com.mustafacol.spacex.utils

import com.mustafacol.spacex.LaunchDetailsQuery
import com.mustafacol.spacex.LaunchListQuery
import com.mustafacol.spacex.data.LaunchDetailsItem
import com.mustafacol.spacex.data.LaunchItem
import com.mustafacol.spacex.data.Links
import com.mustafacol.spacex.data.Rocket

fun LaunchListQuery.LaunchesPast.toApiModel(): LaunchItem = LaunchItem(
    id = id,
    missionName = mission_name,
    launchDateLocal = launch_date_local.toString(),
    imageUrl = links?.mission_patch_small
)

fun LaunchDetailsQuery.Launch.toApiModel(): LaunchDetailsItem = LaunchDetailsItem(
    id = id,
    missionName = mission_name,
    launchDateLocal = launch_date_local.toString(),
    details = details,
    rocket = Rocket(rocketName = rocket?.rocket_name, rocketType = rocket?.rocket_type),
    ships = ships?.map { ship -> ship?.name }!!.toList(),
    launchSuccess = launch_success,
    upcoming = upcoming,
    links = Links(
        flickrImages = links?.flickr_images
    )
)