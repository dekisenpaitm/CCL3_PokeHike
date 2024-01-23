package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball

class PokeballBaseHandler (context: Context) : SQLiteOpenHelper(context, dbName, null, 2) {
    companion object PokeBallDatabase {
        private const val dbName = "PokeBallDatabase"
        private const val tableName = "PokeBall"
        private const val id = "_id"
        private const val name = "name"
        private const val type0 = "type0"
        private const val type1 = "type1"
        private const val type2 = "type2"
        private const val type3 = "type3"
        private const val imageUrl = "imageUrl"
        private const val price = "price"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the Pokeball table when the database is first created.
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $tableName (" +
                    "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$name VARCHAR(30), " +
                    "$type0 VARCHAR(256), " +
                    "$type1 VARCHAR(256), " +
                    "$type2 VARCHAR(256), " +
                    "$type3 VARCHAR(256), " +
                    "$price INTEGER, " +
                    "$imageUrl VARCHAR(256));"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Upgrade the database (e.g., by dropping the table) if needed.
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    // Insert a Pokeball into the database. Checks if the Pokeball already exists
    fun insertPokemonBall(pokeball: Pokeball) {
        val db = this.writableDatabase

        if (!pokeballExists(db, pokeball.name)) {
            val values = ContentValues()
            values.put(name, pokeball.name)
            values.put(type0, pokeball.type0)
            values.put(type1, pokeball.type1)
            values.put(type2, pokeball.type2)
            values.put(type3, pokeball.type3)
            values.put(price, pokeball.price)
            values.put(imageUrl, pokeball.imageUrl)

            // Ensure db is not null before calling insert
            db?.insert(tableName, null, values)
        } else {
            //println("Pokeball is already created!")
        }
    }

    fun pokeballExists(db: SQLiteDatabase?, pokeballName: String): Boolean {
        // Ensure db is not null before querying
        db?.let {
            val query = "SELECT $id FROM $tableName WHERE $name = '$pokeballName'"
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
        val cursor = db.rawQuery("SELECT * FROM $tableName", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val type0ID = cursor.getColumnIndex(type0)
                val type1ID = cursor.getColumnIndex(type1)
                val type2ID = cursor.getColumnIndex(type2)
                val type3ID = cursor.getColumnIndex(type3)
                val priceID = cursor.getColumnIndex(price)
                val imageUrlID = cursor.getColumnIndex(imageUrl)
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
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $type0= '$weather' OR $type0='All'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val type0ID = cursor.getColumnIndex(type0)
                val type1ID = cursor.getColumnIndex(type1)
                val type2ID = cursor.getColumnIndex(type2)
                val type3ID = cursor.getColumnIndex(type3)
                val priceID = cursor.getColumnIndex(price)
                val imageUrlID = cursor.getColumnIndex(imageUrl)
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
