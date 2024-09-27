package de.akdogan.composenavigation.ui.listscreen.model

import de.akdogan.composenavigation.data.remotemodel.CharacterRemote

data class ListCharacter(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val originName: String?
)

fun CharacterRemote.toListCharacter(): ListCharacter? {
    return ListCharacter(
        id = this.id ?: return null,
        name = this.name ?: return null,
        imageUrl = this.imageUrl,
        originName = this.location?.name
    )
}
