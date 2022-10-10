package nl.hva.task02.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import nl.hva.task02.model.Result

@Dao
interface ResultDao {
    @Query("SELECT * FROM results")
    suspend fun getAllResults(): List<Result>

    @Query("SELECT COUNT(*) FROM results")
    suspend fun getNumberOfResults(): Int

    @Query("SELECT COUNT(*) FROM results WHERE result = 'Win'")
    suspend fun getNumberOfWins(): Int

    @Query("SELECT COUNT(*) FROM results WHERE result = 'Draw'")
    suspend fun getNumberOfDraws(): Int

    @Query("SELECT COUNT(*) FROM results WHERE result = 'Loss'")
    suspend fun getNumberOfLosses(): Int

    @Insert
    suspend fun addResult(result: Result)

    @Query("DELETE FROM results")
    suspend fun deleteAllResults()
}