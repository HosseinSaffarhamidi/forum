package ir.adromsh.forum.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RCourse(@PrimaryKey(autoGenerate = false) var id:Int,
              @ColumnInfo var image:String,
              @ColumnInfo var title:String,
              @ColumnInfo var link:String,
              @ColumnInfo var text:String,
              @ColumnInfo var priority:Int,
              @ColumnInfo var price:String)