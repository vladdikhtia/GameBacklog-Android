package nl.hva.madlevel6task1.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun dateFromTimestamp(value : Long?) : Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp (date : Date?) : Long? {
        return date?.time
    }
}