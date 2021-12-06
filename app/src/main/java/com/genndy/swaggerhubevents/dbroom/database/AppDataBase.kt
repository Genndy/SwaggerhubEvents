package com.genndy.swaggerhubevents.dbroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.genndy.swaggerhubevents.dbroom.dao.EventDAO
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event


 @Database(entities = [Event::class], version = 1, exportSchema = false )
abstract class AppDataBase : RoomDatabase() {

    abstract fun eventDAO() : EventDAO
    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context) : AppDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

//    abstract fun eventDAO(): EventDAO?

//    companion object{
//        private var INSTANCE: AppDataBase? = null

//        fun getDatabase(context: Context): AppDataBase? {
//            if (INSTANCE == null) {
//                INSTANCE = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDataBase::class.java,
//                    "database"
//                )
//                    .allowMainThreadQueries()
//                    .build()
//            }
//            return INSTANCE
//        }
    }
}