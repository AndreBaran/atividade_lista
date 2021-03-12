package com.example.lista

import androidx.room.*

@Dao
interface AfazerDao {
   @Query("SELECT * FROM afazer WHERE id = :id")
   fun get(id: Long): Afazer

    @Query("SELECT * FROM afazer ORDER BY id DESC")
    fun getAll(): List<Afazer>

    @Insert
    fun insert(afazer: Afazer): Long

    @Update
    fun update(afazer: Afazer)

    @Delete
    fun delete(afazer: Afazer)

}
