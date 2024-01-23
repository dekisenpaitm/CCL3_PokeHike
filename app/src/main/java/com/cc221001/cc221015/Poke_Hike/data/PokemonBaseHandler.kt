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
        val cursor = db.rawQuery(query, arrayOf(pokemon.number.toString()))

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
        val cursor = db.rawQuery("SELECT * FROM $tableName", null)
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

        //println("those are all your pokemon: $allPokemons")
        return allPokemons.toList()
    }

    // Retrieve favorite Pokemons from the database.
    fun getOwnedPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $owned='true'", null)
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

        return allPokemons.toList()
    }

    // Retrieve favorite Pokemons from the database.
    fun getFavPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $liked='true'", null)
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

        return allPokemons.toList()
    }

    fun getNotOwnedPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $owned='false'", null)
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

        return allPokemons.toList()
    }

    // Delete all favorite Pokemons from the database.
    fun deleteFavPokemons() {
        var allPokemons = getFavPokemons()
        for (pokemon in allPokemons){
            unlikePokemon(pokemon)
        }
    }
    fun getPokemonsOfMultipleTypes(type_1:String,type_2:String,type_3:String,string:String):List<Pokemon?> {
        //println("pokemonOfTypeCalled")
        var allPokemons = mutableListOf<Pokemon>()
        val db = this.readableDatabase
        var cursor = db.rawQuery("SELECT * FROM $tableName WHERE ($type0 LIKE '%$type_1%' OR $type0 LIKE '%$type_2%' OR $type0 LIKE '%$type_3%' OR $type1 LIKE '%$type_1%' OR $type1 LIKE '%$type_2%' OR $type1 LIKE '%$type_3%');", null)
        if(string == "true"){
            cursor = db.rawQuery("SELECT * FROM $tableName WHERE $owned='false' AND ($type0 LIKE '%$type_1%' OR $type0 LIKE '%$type_2%' OR $type0 LIKE '%$type_3%' OR $type1 LIKE '%$type_1%' OR $type1 LIKE '%$type_2%' OR $type1 LIKE '%$type_3%');", null)
        }
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
        return allPokemons.toList()
    }
    fun getPokemonsOfType(type:String):List<Pokemon?> {
        //println("pokemonOfTypeCalled")
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $owned='false' AND $type0 LIKE '%$type%' OR $type1 LIKE '%$type';", null)
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

