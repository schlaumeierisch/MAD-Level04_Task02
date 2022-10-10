package nl.hva.task02.repository

import android.content.Context
import nl.hva.task02.dao.ResultDao
import nl.hva.task02.database.ResultRoomDatabase
import nl.hva.task02.model.Result

class ResultRepository(context: Context) {
    private var resultDao: ResultDao

    init {
        val resultRoomDatabase = ResultRoomDatabase.getDatabase(context)
        resultDao = resultRoomDatabase!!.resultDao()
    }

    suspend fun getAllResults(): List<Result> = resultDao.getAllResults()

    suspend fun getNumberOfResults(): Int = resultDao.getNumberOfResults()

    suspend fun getNumberOfWins(): Int = resultDao.getNumberOfWins()

    suspend fun getNumberOfDraws(): Int = resultDao.getNumberOfDraws()

    suspend fun getNumberOfLosses(): Int = resultDao.getNumberOfLosses()

    suspend fun addResult(result: Result) = resultDao.addResult(result)

    suspend fun deleteAllResults() = resultDao.deleteAllResults()
}