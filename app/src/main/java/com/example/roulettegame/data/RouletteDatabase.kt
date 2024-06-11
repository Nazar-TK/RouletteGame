package com.example.roulettegame.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roulettegame.data.entity.StakeEntity
import com.example.roulettegame.data.entity.UserDataEntity

@Database(
    entities = [
        StakeEntity::class,
        UserDataEntity::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RouletteDatabase : RoomDatabase() {

    abstract val dao: RouletteDao
}