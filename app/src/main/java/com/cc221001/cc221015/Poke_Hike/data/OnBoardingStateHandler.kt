package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.OnBoardingState
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon

class OnBoardingStateHandler (context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {
    companion object OnBoardingStateHandler {
        private const val dbName = "OnBoardingStateDatabase"
        private const val tableName = "States"
        private const val id = "_id"
        private const val name = "name"
        private const val value = "value"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the Pokeball table when the database is first created.
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $tableName (" +
                    "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$name VARCHAR(30), " +
                    "$value BOOLEAN);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Upgrade the database (e.g., by dropping the table) if needed.
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertStates(state:OnBoardingState) {
        val db = this.writableDatabase
        if (!stateExists(db, state.name)) {
            val values = ContentValues()
            values.put(name, state.name)
            values.put(value, state.value)

            // Ensure db is not null before calling insert
            db?.insert(tableName, null, values)
        } else {
            //println("Pokeball is already created!")
        }
    }

    fun stateExists(db: SQLiteDatabase?, stateName: String): Boolean {
        // Ensure db is not null before querying
        db?.let {
            val query = "SELECT $id FROM $tableName WHERE $name = '$stateName'"
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
        values.put(value, newValue)

        db.update(tableName, values, "_id = ?", arrayOf(state?.number.toString()))
    }

    fun getState(stateName:String): OnBoardingState? {
        var state:OnBoardingState? = null

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $name = '$stateName'", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex(id)
                val nameIndex = cursor.getColumnIndex(name)
                val valueIndex = cursor.getColumnIndex(value)

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
        val cursor = db.rawQuery("SELECT * FROM $tableName", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val valueID = cursor.getColumnIndex(value)
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
}