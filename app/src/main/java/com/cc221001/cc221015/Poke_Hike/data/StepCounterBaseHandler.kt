package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.domain.StepCounter

class StepCounterBaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, 2) {
    companion object StepDatabase {
        private const val dbName = "StepCounterDatabase"
        private const val tableName = "StepCounter"
        private const val id = "_id"
        private const val stepCountColumn = "step_count"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //println("I've been called")
        // Create the Pokemon table when the database is first created.
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $tableName (" +
                    "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$stepCountColumn INTEGER);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Upgrade the database (e.g., by dropping the table) if needed.
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun isDatabaseInitialized(): Boolean {
        val db = readableDatabase
        var tableExists = false

        // Check if the "StepCounter" table exists by attempting to query it
        db?.let { database ->
            val tableName = "StepCounter" // Replace with your actual table name
            val cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", arrayOf(tableName))

            cursor.use { cur ->
                if (cur.moveToFirst()) {
                    // The table exists
                    tableExists = true
                }
            }
        }

        // Close the database connection
        return tableExists
    }

    fun insertSteps(stepCounter: StepCounter) {
        val db = this.writableDatabase
        if(!stepsExists(db,stepCounter.id)) {
            val values = ContentValues()
            values.put(id, stepCounter.id)
            values.put(stepCountColumn, stepCounter.amount)

            db.insert(tableName, null, values)
        }else{
            print("steps already exists")
        }
    }

    fun stepsExists(db:SQLiteDatabase?, stepId:Int):Boolean{
        db?.let{
            val query = "SELECT $id FROM $tableName WHERE $id = '$stepId'"
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
        db.update(tableName, values, "$id = ?", selectionArgs)
    }

    fun retrieveSteps(stepsId: Int): StepCounter? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $id = '$stepsId'", null) // No binding arguments required

        var stepCounter: StepCounter? = null
        if (cursor.moveToFirst()) {
            val idID = cursor.getColumnIndex(id)
            val stepCountColumnID = cursor.getColumnIndex(stepCountColumn)
            stepCounter = StepCounter(
                cursor.getInt(idID),
                cursor.getInt(stepCountColumnID)
            )
        }
        cursor.close()
        //db.close()
        //println("this is your stepCoutner:$stepCounter")
        return stepCounter
    }

    fun getAllSteps():List<StepCounter>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $id = ?", null)
        val stepCounterList = mutableListOf<StepCounter>()

        while (cursor.moveToNext()) {
            val idID = cursor.getColumnIndex(id)
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

        //println("those are all your pokemon: $allPokemons")
        return stepCounterList.toList()
    }
}

