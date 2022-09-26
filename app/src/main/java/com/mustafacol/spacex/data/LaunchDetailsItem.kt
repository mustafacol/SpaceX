package com.mustafacol.spacex.data

data class LaunchDetailsItem(
    var details: String? = null,
    var id: String? = null,
    var launchDateLocal: String? = null,
    var missionName: String? = null,
    var rocket: Rocket? = Rocket(),
    var ships: List<String?> = listOf(),
    var links: Links? = Links(),
    var launchSuccess: Boolean? = null,
    var upcoming: Boolean? = null
)

