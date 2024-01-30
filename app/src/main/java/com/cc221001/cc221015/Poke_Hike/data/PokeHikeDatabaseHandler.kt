package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.OnBoardingState
import com.cc221001.cc221015.Poke_Hike.domain.PokeCoin
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.domain.PokemonTrainer
import com.cc221001.cc221015.Poke_Hike.domain.StepCounter

class PokeHikeDatabaseHandler (context: Context) : SQLiteOpenHelper(context, dbName, null, 2) {
    companion object PokeHikeDatabaseHandler {
        private val dbName = "PokeHikeDatabaseHandler"

        //States

        private const val stateTable = "States"
        private const val stateId = "_id"
        private const val stateName = "name"
        private const val stateValue = "value"

        //Pokeball

        private const val pokeballTable = "PokeBall"
        private const val pokeballId = "_id"
        private const val pokeballName = "name"
        private const val pokeballType0 = "type0"
        private const val pokeballType1 = "type1"
        private const val pokeballType2 = "type2"
        private const val pokeballType3 = "type3"
        private const val pokeballImageUrl = "imageUrl"
        private const val pokeballPrice = "price"

        //PokeCoin

        private const val pokeCoinTable ="PokeCoins"
        private const val pokeCoinId = "_id"
        private const val pokeCoinName ="name"
        private const val pokeCoinAmount = "amount"

        //Pokemon

        private const val pokemonTable = "Pokemon"
        private const val pokemonId = "_id"
        private const val pokemonName = "name"
        private const val pokemonType0 = "type0"
        private const val pokemonType1 = "type1"
        private const val pokemonImageUrl = "imageUrl"
        private const val pokemonLiked = "liked"
        private const val pokemonOwned = "owned"

        //StepCounter

        private const val stepCounterTable = "StepCounter"
        private const val stepCounterId = "_id"
        private const val stepCountColumn = "step_count"

        //PokemonTrainer

        private const val trainerTable = "PokemonTrainer"
        private const val trainerId = "_id"
        private const val trainerName = "name"
        private const val hometown = "hometown"
        private const val sprite = "sprite"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $stateTable (" +
                    "$stateId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$stateName VARCHAR(30), " +
                    "$stateValue BOOLEAN);"
        )

        db?.execSQL("CREATE TABLE IF NOT EXISTS $trainerTable (" +
                "$trainerId INTEGER PRIMARY KEY, " +
                "$trainerName VARCHAR(30), " +
                "$hometown VARCHAR(256), " +
                "$sprite VARCHAR(256));"
        )

        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $stepCounterTable (" +
                    "$stepCounterId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$stepCountColumn INTEGER);"
        )

        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $pokeCoinTable (" +
                    "$pokeCoinId INTEGER PRIMARY KEY, " +
                    "$pokeCoinName VARCHAR(30)," +
                    "$pokeCoinAmount INT);"
        )

        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $pokemonTable (" +
                    "$pokemonId INTEGER PRIMARY KEY, " +
                    "$pokemonName VARCHAR(30), " +
                    "$pokemonType0 VARCHAR(256), " +
                    "$pokemonType1 VARCHAR(256), " +
                    "$pokemonImageUrl VARCHAR(256), " +
                    "$pokemonLiked VARCHAR(256)," +
                    "$pokemonOwned VARCHAR(256));"
        )

        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $pokeballTable (" +
                    "$pokeballId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$pokeballName VARCHAR(30), " +
                    "$pokeballType0 VARCHAR(256), " +
                    "$pokeballType1 VARCHAR(256), " +
                    "$pokeballType2 VARCHAR(256), " +
                    "$pokeballType3 VARCHAR(256), " +
                    "$pokeballPrice INTEGER, " +
                    "$pokeballImageUrl VARCHAR(256));"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $stateTable")
        onCreate(db)

        db?.execSQL("DROP TABLE IF EXISTS $trainerTable")
        onCreate(db)

        db?.execSQL("DROP TABLE IF EXISTS $stepCounterTable")
        onCreate(db)

        db?.execSQL("DROP TABLE IF EXISTS $pokeCoinTable")
        onCreate(db)

        db?.execSQL("DROP TABLE IF EXISTS $pokemonTable")
        onCreate(db)

        db?.execSQL("DROP TABLE IF EXISTS $pokeballTable")
        onCreate(db)
    }

    //StateHandler

    fun insertStates(state: OnBoardingState) {
        val db = this.writableDatabase
        if (!stateExists(db, state.name)) {
            val values = ContentValues()
            values.put(stateName, state.name)
            values.put(stateValue, state.value)

            // Ensure db is not null before calling insert
            db?.insert(stateTable, null, values)
        } else {
            //println("Pokeball is already created!")
        }
    }

    fun stateExists(db: SQLiteDatabase?, sName: String): Boolean {
        // Ensure db is not null before querying
        db?.let {
            val query = "SELECT $stateId FROM $stateTable WHERE $stateName = '$sName'"
            val cursor = it.rawQuery(query, null)

            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    return true
                }
            }
        }
        return false
    }

    fun setState(name:String, newValue:Boolean){
        val state = getState(name)
        println(state)
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(stateValue, newValue)

        db.update(stateTable, values, "_id = ?", arrayOf(state?.number.toString()))
    }

    fun getState(sName:String): OnBoardingState? {
        var state: OnBoardingState? = null

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $stateTable WHERE $stateName = '$sName'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex(stateId)
                val nameIndex = cursor.getColumnIndex(stateName)
                val valueIndex = cursor.getColumnIndex(stateValue)

                val id = cursor.getInt(idIndex)
                val name = cursor.getString(nameIndex)
                val value = cursor.getInt(valueIndex) != 0 // Convert integer to boolean

                state = OnBoardingState(id, name, value)
            }
        }
        return state
    }

    fun getAllStates():List<OnBoardingState?>{
        var allStates = mutableListOf<OnBoardingState?>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $stateTable", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(stateId)
                val nameID = cursor.getColumnIndex(stateName)
                val valueID = cursor.getColumnIndex(stateValue)
                if (nameID >= 0)
                    allStates.add(
                        OnBoardingState(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getInt(valueID)>0,
                        )
                    )
            }
        }
        return allStates.toList()
    }

    // TrainerHandler

    fun insertPokemonTrainer(pokemonTrainer: PokemonTrainer) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(trainerName, pokemonTrainer.name)
        values.put(hometown, pokemonTrainer.hometown)
        values.put(sprite, pokemonTrainer.sprite)

        db.insert(trainerTable, null, values)
    }

    // Update an existing Pokemon trainer in the database.
    fun updatePokemonTrainer(pokemonTrainer: PokemonTrainer) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(trainerId, pokemonTrainer.id)
        values.put(trainerName, pokemonTrainer.name)
        values.put(hometown, pokemonTrainer.hometown)
        values.put(sprite, pokemonTrainer.sprite)

        db.update(trainerTable, values, "_id = ?", arrayOf(pokemonTrainer.id.toString()))
    }

    // Delete a Pokemon trainer from the database.
    fun deletePokemonTrainer(pokemonTrainer: PokemonTrainer) {
        val db = writableDatabase
        db.delete(trainerTable, "_id = ?", arrayOf(pokemonTrainer.id.toString()))
    }

    // Retrieve all Pokemon trainers from the database.
    fun getPokemonTrainers(): List<PokemonTrainer> {
        var allPokemonTrainers = mutableListOf<PokemonTrainer>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $trainerTable", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(trainerId)
                val nameID = cursor.getColumnIndex(trainerName)
                val hometownID = cursor.getColumnIndex(hometown)
                val spriteID = cursor.getColumnIndex(sprite)
                if (nameID >= 0 && hometownID >= 0 && spriteID >= 0)
                    allPokemonTrainers.add(
                        PokemonTrainer(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(hometownID),
                            cursor.getString(spriteID)
                        )
                    )
            }
        }

        return allPokemonTrainers.toList()
    }

    //StepCounterHandler

    fun isDatabaseInitialized(name:String): Boolean {
        val db = readableDatabase
        var tableExists = false

        // Check if the "StepCounter" table exists by attempting to query it
        db?.let { database ->
            val tableName = name
            val cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", arrayOf(tableName))

            cursor.use { cur ->
                if (cur.moveToFirst()) {
                    // The table exists
                    tableExists = true
                }
            }
        }

        return tableExists
    }

    fun insertSteps(stepCounter: StepCounter) {
        val db = this.writableDatabase
        if(!stepsExists(db,stepCounter.id)) {
            val values = ContentValues()
            values.put(stepCounterId, stepCounter.id)
            values.put(stepCountColumn, stepCounter.amount)

            db.insert(stepCounterTable, null, values)
        }else{
            print("steps already exists")
        }
    }

    fun stepsExists(db:SQLiteDatabase?, stepId:Int):Boolean{
        db?.let{
            val query = "SELECT $stepCounterId FROM $stepCounterTable WHERE $stepCounterId = '$stepId'"
            val cursor = it.rawQuery(query, null)

            if(cursor != null){
                val exists = cursor.moveToFirst()
                cursor.close()
                return exists
            }
        }
        return false
    }

    fun updateCurrentSteps(stepsId: Int, steps: Int) {
        //println("function get called $stepsId and $steps")
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(stepCountColumn, steps)

        val selectionArgs = arrayOf(stepsId.toString())
        db.update(stepCounterTable, values, "$stepCounterId = ?", selectionArgs)
    }

    fun retrieveSteps(stepsId: Int): StepCounter? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $stepCounterTable WHERE $stepCounterId = '$stepsId'", null) // No binding arguments required

        var stepCounter: StepCounter? = null
        if (cursor.moveToFirst()) {
            val idID = cursor.getColumnIndex(stepCounterId)
            val stepCountColumnID = cursor.getColumnIndex(stepCountColumn)
            stepCounter = StepCounter(
                cursor.getInt(idID),
                cursor.getInt(stepCountColumnID)
            )
        }
        cursor.close()

        return stepCounter
    }

    fun getAllSteps():List<StepCounter>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $stepCounterTable WHERE $stepCounterId = ?", null)
        val stepCounterList = mutableListOf<StepCounter>()

        while (cursor.moveToNext()) {
            val idID = cursor.getColumnIndex(stepCounterId)
            val stepCountColumnID = cursor.getColumnIndex(stepCountColumn)

            if (stepCountColumnID >= -1)
                stepCounterList.add(
                    StepCounter(
                        cursor.getInt(idID),
                        cursor.getInt(stepCountColumnID),
                    )
                )
        }
        cursor.close()

        return stepCounterList.toList()
    }

    //PokeCoinHandler

    fun insertCoin(pokeCoin: PokeCoin){
        val db = this.writableDatabase
        if(!pokeCoinExists(db, pokeCoin.name)){
            val values = ContentValues()
            values.put(pokeCoinId, pokeCoin.id)
            values.put(pokeCoinName, pokeCoin.name)
            values.put(pokeCoinAmount, pokeCoin.amount)

            db?.insert(pokeCoinTable, null, values)
        } else {
            //println("pokeCoin is already created!")
        }
    }

    fun pokeCoinExists(db:SQLiteDatabase?, pcName:String):Boolean{
        db?.let{
            val query = "SELECT $pokeCoinId FROM $pokeCoinTable WHERE $pokeCoinName = '$pcName'"
            val cursor = it.rawQuery(query, null).use{ cursor ->
                if(cursor != null){
                    val exists = cursor.moveToFirst()
                    cursor.close()
                    return exists
                }
            }
        }
        return false
    }

    fun getPokeCoinById(coinId: Int): PokeCoin {
        var pokeCoin = PokeCoin(0, "Default", 0)
        val db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM $pokeCoinTable WHERE $pokeCoinId = ?", arrayOf(coinId.toString()))
                .use { cursor ->
                    if (cursor.moveToFirst()) {
                        val idIndex = cursor.getColumnIndex(pokeCoinId)
                        val nameIndex = cursor.getColumnIndex(pokeCoinName)
                        val amountIndex = cursor.getColumnIndex(pokeCoinAmount)
                        pokeCoin = PokeCoin(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(amountIndex)
                        )
                        cursor.close()
                    }
                    return pokeCoin
                }
    }



    fun getAllPokeCoins():List<PokeCoin>{
        val allPokeCoins = mutableListOf<PokeCoin>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $pokeCoinTable", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokeCoinId)
                val nameID = cursor.getColumnIndex(pokeCoinName)
                val amountID = cursor.getColumnIndex(pokeCoinAmount)

                if (nameID >= 0) {
                    allPokeCoins.add(
                        PokeCoin(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getInt(amountID)
                        )
                    )
                }
            }
        }
        return allPokeCoins.toList()
    }

    fun updatePokeCoin(pokeCoin: PokeCoin, changeAmount:Int): List<PokeCoin?> {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(pokeCoinId, pokeCoin.id)
        values.put(pokeCoinName, pokeCoin.name)
        values.put(pokeCoinAmount,changeAmount)

        db.update(pokeCoinTable, values, "_id = ?", arrayOf(pokeCoin.id.toString()))

        return getAllPokeCoins()
    }

    fun addPokeCoin(pokeCoin: PokeCoin, changeAmount:Int): List<PokeCoin?> {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(pokeCoinId, pokeCoin.id)
        values.put(pokeCoinName, pokeCoin.name)
        values.put(pokeCoinAmount, pokeCoin.amount.plus(changeAmount))

        db.update(pokeCoinTable, values, "_id = ?", arrayOf(pokeCoin.id.toString()))

        return getAllPokeCoins()
    }
    fun usePokeCoin(pokeCoin: PokeCoin, changeAmount:Int): List<PokeCoin?> {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(pokeCoinId, pokeCoin.id)
        values.put(pokeCoinName, pokeCoin.name)
        values.put(pokeCoinAmount, pokeCoin.amount.minus(changeAmount))

        db.update(pokeCoinTable, values, "_id = ?", arrayOf(pokeCoin.id.toString()))

        return getAllPokeCoins()
    }

    fun deletePokeCoin(pokeCoin: PokeCoin) {
        val db = writableDatabase
        db.delete(pokeCoinTable, "_id = ?", arrayOf(pokeCoin.id.toString()))
    }

    //PokemonHandler

    fun insertPokemon(pokemon: Pokemon) {
        val db = this.writableDatabase

        val query = "SELECT * FROM $pokemonTable WHERE $pokemonId = ?"
        val cursor = db.rawQuery(query, arrayOf(pokemon.number.toString())).use { cursor ->

            if (cursor.moveToFirst()) {
                return
            }

            val values = ContentValues()
            values.put(pokemonId, pokemon.number)
            values.put(pokemonName, pokemon.name)
            values.put(pokemonType0, pokemon.type0)
            values.put(pokemonType1, pokemon.type1)
            values.put(pokemonLiked, pokemon.liked)
            values.put(pokemonOwned, pokemon.owned)
            values.put(pokemonImageUrl, pokemon.imageUrl)

            db.insert(pokemonTable, null, values)
        }
    }

    // Mark a Pokemon as liked in the database.
    fun likePokemon(pokemon: Pokemon) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(pokemonLiked, "true")

        db.update(pokemonTable, values, "_id = ?", arrayOf(pokemon.number.toString()))
    }

    // Mark a Pokemon as unliked in the database.
    fun unlikePokemon(pokemon: Pokemon) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(pokemonLiked, "false")

        db.update(pokemonTable, values, "_id = ?", arrayOf(pokemon.number.toString()))
    }

    fun resetPokemonDatabase(): List<Pokemon>{
        val allPokemon:List<Pokemon> = getPokemons()
        allPokemon.forEach { pokemon ->
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(pokemonLiked, "false")
            values.put(pokemonOwned, "false")

            db.update(pokemonTable, values, "_id = ?", arrayOf(pokemon.number.toString()))
        }
        return allPokemon
    }

    // Retrieve all Pokemons from the database.
    fun getPokemons(): List<Pokemon> {
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $pokemonTable", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokemonId)
                val nameID = cursor.getColumnIndex(pokemonName)
                val type0ID = cursor.getColumnIndex(pokemonType0)
                val type1ID = cursor.getColumnIndex(pokemonType1)
                val likedID = cursor.getColumnIndex(pokemonLiked)
                val ownedID = cursor.getColumnIndex(pokemonOwned)
                val imageUrlID = cursor.getColumnIndex(pokemonImageUrl)
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
        val cursor = db.rawQuery("SELECT * FROM $pokemonTable WHERE $pokemonOwned='true'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokemonId)
                val nameID = cursor.getColumnIndex(pokemonName)
                val type0ID = cursor.getColumnIndex(pokemonType0)
                val type1ID = cursor.getColumnIndex(pokemonType1)
                val likedID = cursor.getColumnIndex(pokemonLiked)
                val ownedID = cursor.getColumnIndex(pokemonOwned)
                val imageUrlID = cursor.getColumnIndex(pokemonImageUrl)
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
        val cursor = db.rawQuery("SELECT * FROM $pokemonTable WHERE $pokemonLiked='true'", null).use { cursor ->


            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokemonId)
                val nameID = cursor.getColumnIndex(pokemonName)
                val type0ID = cursor.getColumnIndex(pokemonType0)
                val type1ID = cursor.getColumnIndex(pokemonType1)
                val likedID = cursor.getColumnIndex(pokemonLiked)
                val ownedID = cursor.getColumnIndex(pokemonOwned)
                val imageUrlID = cursor.getColumnIndex(pokemonImageUrl)
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
        val cursor = db.rawQuery("SELECT * FROM $pokemonTable WHERE $pokemonOwned='false'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokemonId)
                val nameID = cursor.getColumnIndex(pokemonName)
                val type0ID = cursor.getColumnIndex(pokemonType0)
                val type1ID = cursor.getColumnIndex(pokemonType1)
                val likedID = cursor.getColumnIndex(pokemonLiked)
                val ownedID = cursor.getColumnIndex(pokemonOwned)
                val imageUrlID = cursor.getColumnIndex(pokemonImageUrl)
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
        val baseQuery = "SELECT * FROM $pokemonTable WHERE " +
                "((LOWER($pokemonType0) LIKE '%$lowerType1%' OR LOWER($pokemonType1) LIKE '%$lowerType2%' OR LOWER($pokemonType0) LIKE '%$lowerType3%') OR " +
                "(LOWER($pokemonType1) LIKE '%$lowerType1%' OR LOWER($pokemonType0) LIKE '%$lowerType2%' OR LOWER($pokemonType1) LIKE '%$lowerType3%'))"

        // Adding condition for 'owned' if required
        val query = if (ownedFilter == "false") {
            "$baseQuery AND $pokemonOwned='false'"
        } else {
            baseQuery
        }

        db.rawQuery(query, null).use { cursor ->
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex(pokemonId)
                val nameIndex = cursor.getColumnIndex(pokemonName)
                val type0Index = cursor.getColumnIndex(pokemonType0)
                val type1Index = cursor.getColumnIndex(pokemonType1)
                val likedIndex = cursor.getColumnIndex(pokemonLiked)
                val ownedIndex = cursor.getColumnIndex(pokemonOwned)
                val imageUrlIndex = cursor.getColumnIndex(pokemonImageUrl)

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
        return allPokemons.toList()
    }


    fun getPokemonsOfType(type:String):List<Pokemon?> {
        //println("pokemonOfTypeCalled")
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor =db.rawQuery( "SELECT * FROM $pokemonTable WHERE $pokemonOwned='false' AND (LOWER($pokemonType0) LIKE LOWER('%$type%') OR LOWER($pokemonType1) LIKE LOWER('%$type%'));", null).use{ cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokemonId)
                val nameID = cursor.getColumnIndex(pokemonName)
                val type0ID = cursor.getColumnIndex(pokemonType0)
                val type1ID = cursor.getColumnIndex(pokemonType1)
                val likedID = cursor.getColumnIndex(pokemonLiked)
                val ownedID = cursor.getColumnIndex(pokemonOwned)
                val imageUrlID = cursor.getColumnIndex(pokemonImageUrl)
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

    fun tagPokemonAsOwned(randomPokemon: Pokemon): Pokemon {
        //println(randomPokemon)
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(pokemonOwned, "true")

        db.update(pokemonTable, values, "_id = ?", arrayOf(randomPokemon?.number.toString()))
        return randomPokemon
    }

    //PokeballHandler

    fun insertPokemonBall(pokeball: Pokeball) {
        val db = this.writableDatabase

        if (!pokeballExists(db, pokeball.name)) {
            val values = ContentValues()
            values.put(pokeballName, pokeball.name)
            values.put(pokeballType0, pokeball.type0)
            values.put(pokeballType1, pokeball.type1)
            values.put(pokeballType2, pokeball.type2)
            values.put(pokeballType3, pokeball.type3)
            values.put(pokeballPrice, pokeball.price)
            values.put(pokeballImageUrl, pokeball.imageUrl)

            // Ensure db is not null before calling insert
            db?.insert(pokeballTable, null, values)
        } else {
            //println("Pokeball is already created!")
        }
    }

    fun pokeballExists(db: SQLiteDatabase?, pName: String): Boolean {
        // Ensure db is not null before querying
        db?.let {
            val query = "SELECT $pokeballId FROM $pokeballTable WHERE $pokeballName = '$pName'"
            val cursor = it.rawQuery(query, null)

            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    return true
                }
            }
        }
        return false
    }


    // Retrieve all Pokeballs from the database.
    fun getAllPokeballs(): List<Pokeball> {
        var allPokeballs = mutableListOf<Pokeball>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $pokeballTable", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokeballId)
                val nameID = cursor.getColumnIndex(pokeballName)
                val type0ID = cursor.getColumnIndex(pokeballType0)
                val type1ID = cursor.getColumnIndex(pokeballType1)
                val type2ID = cursor.getColumnIndex(pokeballType2)
                val type3ID = cursor.getColumnIndex(pokeballType3)
                val priceID = cursor.getColumnIndex(pokeballPrice)
                val imageUrlID = cursor.getColumnIndex(pokeballImageUrl)
                if (nameID >= 0)
                    allPokeballs.add(
                        Pokeball(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(type0ID),
                            cursor.getString(type1ID),
                            cursor.getString(type2ID),
                            cursor.getString(type3ID),
                            cursor.getInt(priceID),
                            cursor.getString(imageUrlID),
                        )
                    )
            }
        }
        return allPokeballs.toList()
    }

    // Retrieve special Pokeballs from the database.
    fun getSpecialPokeball(weather:String): List<Pokeball> {
        var specialPokeball = mutableListOf<Pokeball>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $pokeballTable WHERE $pokeballType0= '$weather' OR $pokeballType0='All'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(pokeballId)
                val nameID = cursor.getColumnIndex(pokeballName)
                val type0ID = cursor.getColumnIndex(pokeballType0)
                val type1ID = cursor.getColumnIndex(pokeballType1)
                val type2ID = cursor.getColumnIndex(pokeballType2)
                val type3ID = cursor.getColumnIndex(pokeballType3)
                val priceID = cursor.getColumnIndex(pokeballPrice)
                val imageUrlID = cursor.getColumnIndex(pokeballImageUrl)
                if (nameID >= 0)
                    specialPokeball.add(
                        Pokeball(
                            cursor.getInt(idID),
                            cursor.getString(nameID),
                            cursor.getString(type0ID),
                            cursor.getString(type1ID),
                            cursor.getString(type2ID),
                            cursor.getString(type3ID),
                            cursor.getInt(priceID),
                            cursor.getString(imageUrlID)
                        )
                    )
            }
        }

        return specialPokeball.toList()
    }

}



