package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.PokeCoin

class PokeCoinBaseHandler(context: Context):SQLiteOpenHelper(context, dbname, null, 1) {

    companion object PokeCoinDatabase {
        private const val dbname ="PokeCoinDatabase"
        private const val tableName ="PokeCoins"
        private const val id = "_id"
        private const val name ="name"
        private const val amount = "amount"
    }

    override fun onCreate(db: SQLiteDatabase?){
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $tableName (" +
            "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$name VARCHAR(30)," +
            "$amount INT);"
        )
    }

    override fun onUpgrade(db:SQLiteDatabase?, p1: Int, p2: Int){
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertCoin(pokeCoin: PokeCoin){
        val db = this.writableDatabase

        if(!pokeCoinExists(db, pokeCoin.name)){
            val values = ContentValues()
            values.put(id, pokeCoin.id)
            values.put(name, pokeCoin.name)
            values.put(amount, pokeCoin.amount)

            db?.insert(tableName, null, values)
        } else {
            println("pokeCoin is already created!")
        }
    }

    fun pokeCoinExists(db:SQLiteDatabase?, pokeCoinName:String):Boolean{
        db?.let{
            val query = "SELECT $id FROM $tableName WHERE $name = '$pokeCoinName'"
            val cursor = it.rawQuery(query, null)

            if(cursor != null){
                val exists = cursor.moveToFirst()
                cursor.close()
                return exists
            }
        }
        return false
    }

    fun getAllPokeCoins():List<PokeCoin>{
        var allPokeCoins = mutableListOf<PokeCoin>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName", null)
        while(cursor.moveToNext()){
            val idID = cursor.getColumnIndex(id)
            val nameID = cursor.getColumnIndex(name)
            val amountID = cursor.getColumnIndex(amount)

            if(nameID >= 0){
                allPokeCoins.add(
                    PokeCoin(
                        cursor.getInt(idID),
                        cursor.getString(nameID),
                        cursor.getInt(amountID)
                    )
                )
            }
        }
        return allPokeCoins.toList()
    }

    fun updatePokeCoin(pokeCoin: PokeCoin): List<PokeCoin?> {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, pokeCoin.id)
        values.put(name, pokeCoin.name)
        values.put(amount, pokeCoin.amount)

        db.update(tableName, values, "_id = ?", arrayOf(pokeCoin.id.toString()))

        return getAllPokeCoins()
    }

    fun deletePokeCoin(pokeCoin: PokeCoin) {
        val db = writableDatabase
        db.delete(tableName, "_id = ?", arrayOf(pokeCoin.id.toString()))
    }

}