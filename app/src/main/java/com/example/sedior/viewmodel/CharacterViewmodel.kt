package com.example.sedior.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sedior.network.CharacterResponse
import com.example.sedior.network.ResultsItem
import com.example.sedior.repository.ApiRepository
import com.example.sedior.utlisChars.ErrorHandle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewmodel(
    private val apiRepository: ApiRepository,
    context: Context,
    private val sharedPreferences: SharedPreferences
): ViewModel(){
    private val TAG ="CharacterViewmodel"
    var currentPage = 1
    var  loadError = mutableStateOf("")
    var  isLoading = mutableStateOf(false)
    var  reachEnded = mutableStateOf(false)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "exceptionHandler: ${throwable.localizedMessage?.toString()}", )
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                Toast.makeText(context,"No Internet/Poor Connection!", Toast.LENGTH_LONG).show()
            }
        }

    }
    private val _characters = MutableStateFlow<List<ResultsItem>>(emptyList())
    val characters: StateFlow<List<ResultsItem>> get() = _characters
    private val _charactersResponses = MutableStateFlow<ErrorHandle<CharacterResponse>>(ErrorHandle.Loading)
    val charactersResponses: StateFlow<ErrorHandle<CharacterResponse>> get() = _charactersResponses
    private val _characterResponses = MutableStateFlow<ErrorHandle<ResultsItem>>(ErrorHandle.Loading)
    val characterResponses: StateFlow<ErrorHandle<ResultsItem>> get() = _characterResponses
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            apiRepository.searchChars(currentPage).collect { response ->
                _charactersResponses.value = response
                when (response) {
                    is ErrorHandle.Success -> {
                        if (response.data.results?.isNotEmpty() == true) {
                            _characters.value = _characters.value+ (response.data.results as List<ResultsItem>?)!!
                        }
                    }

                    else -> {}
                }

            }
        }
    }

    fun loadNextPage() {
        currentPage++
        fetchCharacters()
    }

    fun searchSongs(serach: String) {
        _search.value = serach
    }

    fun fetchCahrecter(id: Int?) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            apiRepository.searchCharacter(id.toString()).collect { response ->
                _characterResponses.value = response
            }
        }
    }

    fun logOut(function: () -> Unit) {
        sharedPreferences.edit().apply {
            putBoolean("Login",false)
        }.apply()
        function()
    }
    fun login(function: (Boolean) -> Unit) {
        sharedPreferences.edit().apply {
            putBoolean("Login",true)
        }.apply()
        function(true)
    }
    private val _search = MutableStateFlow("")
    val searchValue = _search.asStateFlow()

    @OptIn(FlowPreview::class)
    val searchedChars = searchValue .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(characters) { text, company ->
            Log.e("TAG" , "onSearchTextChange-->: $text"  )
            if(text.isBlank()) {
                company
            } else {
                delay(1000L)
                company.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(1500L),
            emptyList()
        )
}