package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon

class ShopBaseHandler (context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {
    companion object PokeBallDatabase {
        private const val dbName = "PokemonBallDatabase"
        private const val tableName = "PokeBall"
        private const val id = "_id"
        private const val name = "name"
        private const val type0 = "type0"
        private const val imageUrl = "imageUrl"
        private const val price = "price"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the Pokemon table when the database is first created.
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $tableName (" +
                    "$id INTEGER PRIMARY KEY, " +
                    "$name VARCHAR(30), " +
                    "$type0 VARCHAR(256), " +
                    "$price VARCHAR(256), " +
                    "$imageUrl VARCHAR(256));"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Upgrade the database (e.g., by dropping the table) if needed.
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    // Insert a Pokemon into the database.
    fun insertPokemonBall(pokeball: Pokeball) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, pokeball.number)
        values.put(name, pokeball.name)
        values.put(type0, pokeball.type0)
        values.put(price, pokeball.price)
        values.put(imageUrl, pokeball.imageUrl)

        db.insert(tableName, null, values)
    }

    // Retrieve all Pokemons from the database.
    fun getAllPokeballs(): List<Pokeball> {
        var allPokeballs = mutableListOf<Pokeball>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName", null)
        while (cursor.moveToNext()) {
            val idID = cursor.getColumnIndex(id)
            val nameID = cursor.getColumnIndex(name)
            val type0ID = cursor.getColumnIndex(type0)
            val priceID = cursor.getColumnIndex(price)
            val imageUrlID = cursor.getColumnIndex(imageUrl)
            if (nameID >= 0)
                allPokeballs.add(
                    Pokeball(
                        cursor.getInt(idID),
                        cursor.getString(nameID),
                        cursor.getString(type0ID),
                        cursor.getInt(priceID),
                        cursor.getString(imageUrlID),
                    )
                )
        }

        return allPokeballs.toList()
    }

    // Retrieve favorite Pokemons from the database.
    fun getSpecialPokeball(weather:String): List<Pokemon> {
        var specialPokeball = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $type0=$weather", null)
        while (cursor.moveToNext()) {
            val idID = cursor.getColumnIndex(id)
            val nameID = cursor.getColumnIndex(name)
            val type0ID = cursor.getColumnIndex(type0)
            val priceID = cursor.getColumnIndex(price)
            val imageUrlID = cursor.getColumnIndex(imageUrl)
            if (nameID >= 0)
                specialPokeball.add(
                    Pokemon(
                        cursor.getInt(idID),
                        cursor.getString(nameID),
                        cursor.getString(type0ID),
                        cursor.getString(priceID),
                        cursor.getString(imageUrlID)
                    )
                )
        }

        return specialPokeball.toList()
    }
}
