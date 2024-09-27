package de.akdogan.composenavigation.data

import de.akdogan.composenavigation.data.remotemodel.CharacterRemote
import de.akdogan.composenavigation.data.remotemodel.CharacterResultRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): CharacterResultRemote

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") characterId: Int
    ): CharacterRemote
}
