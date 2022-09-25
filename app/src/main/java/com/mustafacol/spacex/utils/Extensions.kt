package com.mustafacol.spacex.utils

import com.mustafacol.spacex.LaunchListQuery
import com.mustafacol.spacex.data.LaunchItem
import java.util.Date

fun LaunchListQuery.LaunchesPast.toApiModel(): LaunchItem = LaunchItem(
    missionName = mission_name,
    launchDateLocal = launch_date_local.toString(),
    imageUrl = links?.mission_patch_small
)