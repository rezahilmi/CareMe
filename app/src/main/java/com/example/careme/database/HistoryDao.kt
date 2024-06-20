package com.example.careme.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.careme.data.network.ResultItem

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: List<ResultItem>)

    @Query("SELECT * FROM history")
    fun getAllHistory(): PagingSource<Int, ResultItem>

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}