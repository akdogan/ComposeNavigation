package de.akdogan.composenavigation.ui.detailscreen.model

import de.akdogan.composenavigation.data.remotemodel.CharacterRemote

data class DetailCharacter(
    val name: String,
    val imageUrl: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val origin: String?,
    val location: String?,
    val numEpisodes: Int
)

fun CharacterRemote.toDetailCharacter(): DetailCharacter? {
    return DetailCharacter(
        name = this.name ?: return null,
        imageUrl = this.imageUrl ?: return null,
        status = this.status,
        species = this.species,
        type = this.type,
        location = this.location?.name,
        origin = this.origin?.name,
        numEpisodes = this.episodes?.size ?: 0
    )
}
