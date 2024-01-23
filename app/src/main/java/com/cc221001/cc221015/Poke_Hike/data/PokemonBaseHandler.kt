package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import kotlinx.coroutines.runBlocking

// This class handles database operations for storing Pokemon data.
class PokemonBaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, 2) {
    companion object PokemonDatabase {
        private const val dbName = "PokemonDatabase"
        private const val tableName = "Pokemon"
        private const val id = "_id"
        private const val name = "name"
        private const val type0 = "type0"
        private const val type1 = "type1"
        private const val imageUrl = "imageUrl"
        private const val liked = "liked"
        private const val owned = "owned"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the Pokemon table when the database is first created.
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $tableName (" +
                    "$id INTEGER PRIMARY KEY, " +
                    "$name VARCHAR(30), " +
                    "$type0 VARCHAR(256), " +
                    "$type1 VARCHAR(256), " +
                    "$imageUrl VARCHAR(256), " +
                    "$liked VARCHAR(256)," +
                    "$owned VARCHAR(256));"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Upgrade the database (e.g., by dropping the table) if needed.
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }


    fun insertPokemon(pokemon: Pokemon) {
        val db = this.writableDatabase

        val query = "SELECT * FROM $tableName WHERE $id = ?"
        val cursor = db.rawQuery(query, arrayOf(pokemon.number.toString())).use { cursor ->

            if (cursor.moveToFirst()) {
                return
            }

            val values = ContentValues()
            values.put(id, pokemon.number)
            values.put(name, pokemon.name)
            values.put(type0, pokemon.type0)
            values.put(type1, pokemon.type1)
            values.put(liked, pokemon.liked)
            values.put(owned, pokemon.owned)
            values.put(imageUrl, pokemon.imageUrl)

            db.insert(tableName, null, values)
        }
    }

    // Mark a Pokemon as liked in the database.
    fun likePokemon(pokemon: Pokemon) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(liked, "true")

        db.update(tableName, values, "_id = ?", arrayOf(pokemon.number.toString()))
    }

    // Mark a Pokemon as unliked in the database.
    fun unlikePokemon(pokemon: Pokemon) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(liked, "false")

        db.update(tableName, values, "_id = ?", arrayOf(pokemon.number.toString()))
    }

    fun resetPokemonDatabase(): List<Pokemon>{
        val allPokemon:List<Pokemon> = getPokemons()
        allPokemon.forEach { pokemon ->
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(liked, "false")
            values.put(owned, "false")

            db.update(tableName, values, "_id = ?", arrayOf(pokemon.number.toString()))
        }
        return allPokemon
    }

    // Retrieve all Pokemons from the database.
    fun getPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val type0ID = cursor.getColumnIndex(type0)
                val type1ID = cursor.getColumnIndex(type1)
                val likedID = cursor.getColumnIndex(liked)
                val ownedID = cursor.getColumnIndex(owned)
                val imageUrlID = cursor.getColumnIndex(imageUrl)
                if (nameID >= 0)
                    allPokemons.add(
                        Pokemon(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(type0ID),
                            cursor.getString(type1ID),
                            cursor.getString(imageUrlID),
                            cursor.getString(likedID),
                            cursor.getString(ownedID)
                        )
                    )
            }
        }

        //println("those are all your pokemon: $allPokemons")
        return allPokemons.toList()
    }

    // Retrieve favorite Pokemons from the database.
    fun getOwnedPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $owned='true'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val type0ID = cursor.getColumnIndex(type0)
                val type1ID = cursor.getColumnIndex(type1)
                val likedID = cursor.getColumnIndex(liked)
                val ownedID = cursor.getColumnIndex(owned)
                val imageUrlID = cursor.getColumnIndex(imageUrl)
                if (nameID >= 0)
                    allPokemons.add(
                        Pokemon(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(type0ID),
                            cursor.getString(type1ID),
                            cursor.getString(imageUrlID),
                            cursor.getString(likedID),
                            cursor.getString(ownedID)
                        )
                    )
            }
        }

        return allPokemons.toList()
    }

    // Retrieve favorite Pokemons from the database.
    fun getFavPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $liked='true'", null).use { cursor ->


            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val type0ID = cursor.getColumnIndex(type0)
                val type1ID = cursor.getColumnIndex(type1)
                val likedID = cursor.getColumnIndex(liked)
                val ownedID = cursor.getColumnIndex(owned)
                val imageUrlID = cursor.getColumnIndex(imageUrl)
                if (nameID >= 0)
                    allPokemons.add(
                        Pokemon(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(type0ID),
                            cursor.getString(type1ID),
                            cursor.getString(imageUrlID),
                            cursor.getString(likedID),
                            cursor.getString(ownedID)
                        )
                    )
            }
        }

        return allPokemons.toList()
    }

    fun getNotOwnedPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $owned='false'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val type0ID = cursor.getColumnIndex(type0)
                val type1ID = cursor.getColumnIndex(type1)
                val likedID = cursor.getColumnIndex(liked)
                val ownedID = cursor.getColumnIndex(owned)
                val imageUrlID = cursor.getColumnIndex(imageUrl)
                if (nameID >= 0)
                    allPokemons.add(
                        Pokemon(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(type0ID),
                            cursor.getString(type1ID),
                            cursor.getString(imageUrlID),
                            cursor.getString(likedID),
                            cursor.getString(ownedID)
                        )
                    )
            }
        }

        return allPokemons.toList()
    }

    // Delete all favorite Pokemons from the database.
    fun deleteFavPokemons() {
        var allPokemons = getFavPokemons()
        for (pokemon in allPokemons){
            unlikePokemon(pokemon)
        }
    }
    fun getPokemonsOfMultipleTypes(pkmntype1: String, pkmntype2: String, pkmntype3: String, ownedFilter: String): List<Pokemon> {
        val allPokemons = mutableListOf<Pokemon>()
        val db = this.readableDatabase

        // Lowercase the type parameters for case-insensitive comparison
        val lowerType1 = pkmntype1.lowercase()
        val lowerType2 = pkmntype2.lowercase()
        val lowerType3 = pkmntype3.lowercase()

        // Constructing the base query with LOWER() for case-insensitivity
        val baseQuery = "SELECT * FROM $tableName WHERE " +
                "((LOWER($type0) LIKE '%$lowerType1%' OR LOWER($type0) LIKE '%$lowerType2%' OR LOWER($type0) LIKE '%$lowerType3%') OR " +
                "(LOWER($type1) LIKE '%$lowerType1%' OR LOWER($type1) LIKE '%$lowerType2%' OR LOWER($type1) LIKE '%$lowerType3%'))"

        // Adding condition for 'owned' if required
        val query = if (ownedFilter == "false") {
            "$baseQuery AND $owned='false'"
        } else {
            baseQuery
        }

        db.rawQuery(query, null).use { cursor ->
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex(id)
                val nameIndex = cursor.getColumnIndex(name)
                val type0Index = cursor.getColumnIndex(type0)
                val type1Index = cursor.getColumnIndex(type1)
                val likedIndex = cursor.getColumnIndex(liked)
                val ownedIndex = cursor.getColumnIndex(owned)
                val imageUrlIndex = cursor.getColumnIndex(imageUrl)

                if (nameIndex >= 0) {
                    allPokemons.add(
                        Pokemon(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(type0Index),
                            cursor.getString(type1Index),
                            cursor.getString(imageUrlIndex),
                            cursor.getString(likedIndex),
                            cursor.getString(ownedIndex)
                        )
                    )
                }
            }
        }
        println(allPokemons)
        return allPokemons.toList()
    }


    fun getPokemonsOfType(type:String):List<Pokemon?> {
        //println("pokemonOfTypeCalled")
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor =db.rawQuery( "SELECT * FROM $tableName WHERE $owned='false' AND (LOWER($type0) LIKE LOWER('%$type%') OR LOWER($type1) LIKE LOWER('%$type%'));", null).use{cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val type0ID = cursor.getColumnIndex(type0)
                val type1ID = cursor.getColumnIndex(type1)
                val likedID = cursor.getColumnIndex(liked)
                val ownedID = cursor.getColumnIndex(owned)
                val imageUrlID = cursor.getColumnIndex(imageUrl)
                if (nameID >= 0)
                    allPokemons.add(
                        Pokemon(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(type0ID),
                            cursor.getString(type1ID),
                            cursor.getString(imageUrlID),
                            cursor.getString(likedID),
                            cursor.getString(ownedID)
                        )
                    )
            }
        }
        return allPokemons.toList()
    }
    fun getRandomNewPokemon(type1: String = "", type2: String = "", type3: String = ""): Pokemon? {
        var randomPokemon: Pokemon?
        if(type1=="All"){
            randomPokemon = getNotOwnedPokemons().random()

            if(randomPokemon.name != "") {
                //ACTIVATE TO TAG POKEMONS AS OWNED!
                randomPokemon=tagPokemonAsOwned(randomPokemon)
            }
            //println("thisisthetaggedpokemon $randomPokemon")
        } else {
            val pokemonsOfTypeOne = if (type1.isNotEmpty()) getPokemonsOfType(type1) else emptyList()
            val pokemonsOfTypeTwo = if (type2.isNotEmpty()) getPokemonsOfType(type2) else emptyList()
            val pokemonsOfTypeThree = if (type3.isNotEmpty()) getPokemonsOfType(type3) else emptyList()

            val combinedPokemonList = pokemonsOfTypeOne + pokemonsOfTypeTwo + pokemonsOfTypeThree

            if (combinedPokemonList.isEmpty()) {
                // If no valid types were provided, return null or handle it accordingly
                //println("No valid types provided. Unable to get a random Pokemon.")
                return null
            }

            randomPokemon = combinedPokemonList.random()
            if(randomPokemon != null) {
                //ACTIVATE TO TAG POKEMONS AS OWNED!
                randomPokemon=tagPokemonAsOwned(randomPokemon)
            }
            //println("thisisthetaggedpokemon $randomPokemon")

        }
        return randomPokemon
    }

    fun tagPokemonAsOwned(randomPokemon:Pokemon):Pokemon{
        //println(randomPokemon)
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(owned, "true")

        db.update(tableName, values, "_id = ?", arrayOf(randomPokemon?.number.toString()))
        return randomPokemon
    }

}

