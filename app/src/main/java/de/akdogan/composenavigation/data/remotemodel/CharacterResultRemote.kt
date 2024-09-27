package de.akdogan.composenavigation.data.remotemodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResultRemote(
    @SerialName("info") val info: InfoRemote,
    @SerialName("results") val results: List<CharacterRemote>?
)

@Serializable
data class InfoRemote(
    @SerialName("count") val count: Int?,
    @SerialName("pages") val pages: Int?,
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?
)

@Serializable
data class LocationRemote(
    @SerialName("name") val name: String?,
    @SerialName("url") val url: String?
)

@Serializable
data class CharacterRemote(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("image") val imageUrl: String?,
    @SerialName("location") val location: LocationRemote?,
    @SerialName("origin") val origin: LocationRemote?,
    @SerialName("species") val species: String?,
    @SerialName("status") val status: String?,
    @SerialName("type") val type: String?,
    @SerialName("episode") val episodes: List<String>?
)
