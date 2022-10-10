package nl.hva.task02.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "results")
data class Result(
    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "computer_move")
    val computerMove: Move,

    @ColumnInfo(name = "player_move")
    val playerMove: Move,

    @ColumnInfo(name = "result")
    val result: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null
)
