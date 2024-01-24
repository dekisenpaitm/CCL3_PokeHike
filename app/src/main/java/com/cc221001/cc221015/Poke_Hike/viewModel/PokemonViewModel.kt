package com.cc221001.cc221015.Poke_Hike.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc221001.cc221015.Poke_Hike.service.PokemonRepository
import com.cc221001.cc221015.Poke_Hike.data.PokemonBaseHandler
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.stateModel.PokemonViewState
import com.cc221001.cc221015.Poke_Hike.views.Screen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel responsible for managing Pokemon-related data and interactions.
class PokemonViewModel(private val db: PokemonBaseHandler) : ViewModel() {
	private val _pokemonViewState = MutableStateFlow(PokemonViewState())
	val pokemonViewState: StateFlow<PokemonViewState> = _pokemonViewState.asStateFlow()

	private val _currentListType = MutableStateFlow(ListType.FAVORITES)
	val currentListType: StateFlow<ListType> get() = _currentListType

	private val _isSearching = MutableStateFlow(false)
	val isSearching = _isSearching.asStateFlow()

	//second state the text typed by the user
	private val _searchText = MutableStateFlow("")
	val searchText = _searchText.asStateFlow()

	private val _pokemonList = MutableStateFlow(pokemonViewState.value.pokemons)
	val pokemonList = searchText
		.combine(_pokemonList) { text, pokemons ->//combine searchText with _contriesList
			if (text.isBlank()) { //return the entery list of countries if not is typed
				pokemons
			}
			pokemons.filter { pokemon ->// filter and return a list of countries based on the text the user typed
				pokemon!!.name.uppercase().contains(text.trim().uppercase())
			}
		}.stateIn(//basically convert the Flow returned from combine operator to StateFlow
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000),//it will allow the StateFlow survive 5 seconds before it been canceled
			initialValue = _pokemonList.value
		)

	fun onSearchTextChange(text: String) {
		_searchText.value = text
	}

	fun onToogleSearch() {
		_isSearching.value = !_isSearching.value
		if (!_isSearching.value) {
			onSearchTextChange("")
		}
	}

	// Fetch and load all Pokemon from the database.
	fun getPokemon() {
		_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
		_pokemonList.update {db.getPokemons()}
		_currentListType.value = ListType.ALL
	}

	// Fetch and load favorite Pokemon from the database.
	fun getFavPokemon() {
		_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
		_currentListType.value = ListType.FAVORITES
	}

	fun getOwnedPokemon() {
		_pokemonViewState.update { it.copy(pokemons = db.getOwnedPokemons()) }
		_currentListType.value = ListType.OWNED
	}

	enum class ListType {
		ALL,
		FAVORITES,
		OWNED
	}

	fun getNotOwnedPokemon(){
		_pokemonViewState.update { it.copy(availableAllPokemon = db.getNotOwnedPokemons(),
			notAvailableAllPokemon = db.getPokemons())}
	}
	//Fetches a certain type of Pokemon & checks if the Pokemon are already owned
	fun getRandomPokemon(type1:String, type2:String, type3:String) {
		_pokemonViewState.update { it.copy(pokemon = db.getRandomNewPokemon(type1, type2, type3)) }
		_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
	}

	fun getAvailablePokemon(type1:String, type2: String,type3: String) {
		_pokemonViewState.update {
			it.copy(availableTypePokemon = db.getPokemonsOfMultipleTypes(type1, type2, type3, "false"),
				notAvailableTypePokemon = db.getPokemonsOfMultipleTypes(type1, type2, type3, "true"))}
	}


	fun resetPokemonDatabase(){
		_pokemonViewState.update{it.copy(pokemons=db.resetPokemonDatabase())}
	}

	// Select a screen in the UI.
	fun selectScreen(screen: Screen) {
		_pokemonViewState.update { it.copy(selectedScreen = screen) }
	}

	// Unlike a Pokemon and update the view state.
	fun unlikePokemon(pokemon: Pokemon, currentList: String) {
		db.unlikePokemon(pokemon)
		_pokemonViewState.update {
			when (currentList) {
				"favorite" -> it.copy(pokemons = db.getFavPokemons())
				"owned" -> it.copy(pokemons = db.getOwnedPokemons())
				"all" -> it.copy(pokemons = db.getPokemons())
				else -> it
			}
		}
	}

	// Like a Pokemon and update the view state.
	fun likePokemon(pokemon: Pokemon, currentList: String) {
		db.likePokemon(pokemon)

		_pokemonViewState.update {
			when (currentList) {
				"favorite" -> it.copy(pokemons = db.getFavPokemons())
				"owned" -> it.copy(pokemons = db.getOwnedPokemons())
				"all" -> it.copy(pokemons = db.getPokemons())
				else -> it  // handle default case or leave it as is
			}
		}
	}

	// Delete all favorited Pokemon and update the view state.
	fun deleteAllFavedPokemon() {
		db.deleteFavPokemons()
		_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
	}

	// Load Pokemon data from an API and insert it into the database.
	fun loadPokemons() {
		GlobalScope.launch(Dispatchers.IO) {
			val pokemonsApiResult = PokemonRepository.listPokemons()
			if (pokemonsApiResult != null) {
				//println(pokemonsApiResult.results)
			}

			pokemonsApiResult?.results?.let { results ->
				val pokemonList = results.map { pokemonResult ->
					val number = pokemonResult.url
						.replace("https://pokeapi.co/api/v2/pokemon/", "")
						.replace("/", "").toInt()

					val pokemonApiResult = PokemonRepository.getPokemon(number)

					pokemonApiResult?.let {
						//println("This is types: $pokemonApiResult")
						val pokemon = Pokemon(
							pokemonApiResult.id,
							pokemonApiResult.name,
							pokemonApiResult.types[0].type.toString(),
							if (pokemonApiResult.types.count() > 1) {
								pokemonApiResult.types[1].type.toString()
							} else {
								""
							}
						)
						db.insertPokemon(pokemon)

						pokemon
					}
				}

				_pokemonViewState.value = PokemonViewState(pokemonList)
			}
		}
	}
}
