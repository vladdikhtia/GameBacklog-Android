package nl.hva.madlevel6task1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


//Using the annotation @Entity we have defined that this is an entity
// that needs to be stored in the database

//Using tableName we provide Room
// with a tableName we want to store this entity in.
@Entity(tableName = "game")
data class Game(

    @ColumnInfo(name = "title")
    var title : String,

    @ColumnInfo(name = "platform")
    var platform : String,

    @ColumnInfo(name = "release")
    var release : Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long = 0
)
