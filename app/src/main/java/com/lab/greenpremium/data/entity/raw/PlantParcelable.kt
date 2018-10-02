package com.lab.greenpremium.data.entity.raw

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.lab.greenpremium.R
import java.io.Serializable


data class PlantParcelable(val name: String,
                           val info1: String,
                           val info2: String,
                           val price: Double,
                           val discount: Double,
                           val type: Type,
                           val drawableResId: Int, //todo в будущем URL картинки
                           var count: Int = 0,
                           var isFavorite: Boolean = false) : Parcelable {

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(info1)
        dest?.writeString(info2)
        dest?.writeDouble(price)
        dest?.writeDouble(discount)
        dest?.writeInt(type.ordinal)
        dest?.writeInt(drawableResId)
        dest?.writeInt(count)
        dest?.writeByte((if (isFavorite) 1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlantParcelable> {

        private fun getPlant(parcel: Parcel): PlantParcelable {
            return PlantParcelable(
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readDouble(),
                    parcel.readDouble(),
                    fromInt(parcel.readInt()),
                    parcel.readInt(),
                    parcel.readInt(),
                    parcel.readByte() != 0.toByte()
            )
        }

        fun getTitles(context: Context?): Array<String> {
            val titles: Array<String> = Array(Type.values().size) { "" }
            enumValues<Type>().forEach { titles[it.ordinal] = context!!.getString(it.titleResId) }
            return titles
        }

        fun fromInt(value: Int): Type {
            for (type in Type.values()) {
                if (value == type.ordinal) {
                    return type
                }
            }
            return Type.LIVING
        }

        override fun createFromParcel(parcel: Parcel): PlantParcelable {
            return getPlant(parcel)
        }

        override fun newArray(size: Int): Array<PlantParcelable?> {
            return arrayOfNulls(size)
        }
    }

    enum class Type(var titleResId: Int) : Serializable {
        LIVING(R.string.title_plants_living),
        ARTIFICIAL(R.string.title_plants_artificial),
        BIG(R.string.title_plants_big);
    }

}