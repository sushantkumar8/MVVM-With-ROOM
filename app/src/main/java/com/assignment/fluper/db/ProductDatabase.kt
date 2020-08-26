package com.assignment.fluper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *  @ProductDatabase: It is the Room Database,
 *                which holds the INSTANCE of ProductDatabase.
 *  @see  ProductDatabase
 *
 *  @author  Sushant Kumar
 */
@Database(entities = [ProductEntity::class],version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract val productDAO : ProductDAO

    companion object{
        @Volatile
        private var INSTANCE : ProductDatabase? = null
            fun getInstance(context: Context):ProductDatabase{
               synchronized(this){
                var instance:ProductDatabase? = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_data_database").build()
                }
                return instance
            }
        }
    }
}