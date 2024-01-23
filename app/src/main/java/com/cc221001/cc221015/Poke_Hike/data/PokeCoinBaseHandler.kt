package com.cc221001.cc221015.Poke_Hike.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221001.cc221015.Poke_Hike.domain.PokeCoin

class PokeCoinBaseHandler(context: Context):SQLiteOpenHelper(context, dbname, null, 2) {

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
            "$id INTEGER PRIMARY KEY, " +
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
            //println("pokeCoin is already created!")
        }
    }

    fun pokeCoinExists(db:SQLiteDatabase?, pokeCoinName:String):Boolean{
        db?.let{
            val query = "SELECT $id FROM $tableName WHERE $name = '$pokeCoinName'"
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
            db.rawQuery("SELECT * FROM $tableName WHERE $id = ?", arrayOf(coinId.toString()))
                .use { cursor ->
                    if (cursor.moveToFirst()) {
                        val idIndex = cursor.getColumnIndex(id)
                        val nameIndex = cursor.getColumnIndex(name)
                        val amountIndex = cursor.getColumnIndex(amount)
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
        val cursor = db.rawQuery("SELECT * FROM $tableName", null).use { cursor ->
            while (cursor.moveToNext()) {
                val idID = cursor.getColumnIndex(id)
                val nameID = cursor.getColumnIndex(name)
                val amountID = cursor.getColumnIndex(amount)

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
        values.put(id, pokeCoin.id)
        values.put(name, pokeCoin.name)
        values.put(amount,changeAmount)

        db.update(tableName, values, "_id = ?", arrayOf(pokeCoin.id.toString()))

        return getAllPokeCoins()
    }

    fun addPokeCoin(pokeCoin: PokeCoin, changeAmount:Int): List<PokeCoin?> {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, pokeCoin.id)
        values.put(name, pokeCoin.name)
        values.put(amount, pokeCoin.amount.plus(changeAmount))

        db.update(tableName, values, "_id = ?", arrayOf(pokeCoin.id.toString()))

        return getAllPokeCoins()
    }
    fun usePokeCoin(pokeCoin: PokeCoin, changeAmount:Int): List<PokeCoin?> {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, pokeCoin.id)
        values.put(name, pokeCoin.name)
        values.put(amount, pokeCoin.amount.minus(changeAmount))

        db.update(tableName, values, "_id = ?", arrayOf(pokeCoin.id.toString()))

        return getAllPokeCoins()
    }

    fun deletePokeCoin(pokeCoin: PokeCoin) {
        val db = writableDatabase
        db.delete(tableName, "_id = ?", arrayOf(pokeCoin.id.toString()))
    }

}