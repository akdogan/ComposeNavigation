package de.akdogan.composenavigation.ui.listscreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.akdogan.composenavigation.data.RickAndMortyApi
import de.akdogan.composenavigation.data.getApiInstance
import de.akdogan.composenavigation.ui.listscreen.model.ListCharacter
import de.akdogan.composenavigation.ui.listscreen.model.toListCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListScreenViewModel : ViewModel() {

    private val TAG = this::class.java.simpleName

    // todo use repo
    private val api = getApiInstance(
        "https://rickandmortyapi.com/api/",
        RickAndMortyApi::class.java
    )

    val characters: SnapshotStateList<ListCharacter> = mutableStateListOf()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // todo bypassing pagination in api
            launch { load(1) }
            launch { load(2) }
            launch { load(3) }
        }
    }

    private suspend fun load(page: Int) {

        runCatching {
            val result = api.getAllCharacters(page)

            Log.i(TAG, "Received data with ${result.info.pages} pages and ${result.info.count} items --> nextPage = ${result.info.next}")

            val characterList = requireNotNull(result.results).mapNotNull { it.toListCharacter() }
            withContext(Dispatchers.Main) {
                characters.addAll(characterList)
            }
        }.onFailure {
            Log.w(TAG, "Failed to load characters", it)
            // todo show an error
        }
    }
}
