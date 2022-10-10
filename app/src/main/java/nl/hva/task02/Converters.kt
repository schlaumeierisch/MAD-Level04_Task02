package nl.hva.task02

import androidx.room.TypeConverter
import nl.hva.task02.model.Move
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toMove(move: String) = enumValueOf<Move>(move)

    @TypeConverter
    fun fromMove(move: Move) = move.name
}