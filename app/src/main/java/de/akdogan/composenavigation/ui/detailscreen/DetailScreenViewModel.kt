package de.akdogan.composenavigation.ui.detailscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.akdogan.composenavigation.data.RickAndMortyApi
import de.akdogan.composenavigation.data.getApiInstance
import de.akdogan.composenavigation.ui.detailscreen.model.DetailCharacter
import de.akdogan.composenavigation.ui.detailscreen.model.toDetailCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailScreenViewModel : ViewModel() {

    private val TAG = this::class.java.simpleName

    // todo use repo
    private val api = getApiInstance(
        "https://rickandmortyapi.com/api/",
        RickAndMortyApi::class.java
    )

    private val _detailInfo: MutableStateFlow<DetailCharacter?> = MutableStateFlow(null)
    val detailInfo: StateFlow<DetailCharacter?> = _detailInfo.asStateFlow()

    fun loadData(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val result = api.getCharacter(characterId).toDetailCharacter()
                _detailInfo.update { result }
            }.onFailure {
                Log.w(TAG, "Failed to load character for id $characterId", it)
            }
        }
    }
}
