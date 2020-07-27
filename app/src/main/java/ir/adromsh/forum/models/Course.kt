package ir.adromsh.forum.models

import android.os.Parcel
import android.os.Parcelable

class Course(
    var id: String?,
    var title: String?,
    var price: String?,
    var image: String?,
    var link: String?,
    var text: String?,
    var priority:Int,
    var color: String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(price)
        parcel.writeString(image)
        parcel.writeString(link)
        parcel.writeString(text)
        parcel.writeInt(priority)
        parcel.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }

}
