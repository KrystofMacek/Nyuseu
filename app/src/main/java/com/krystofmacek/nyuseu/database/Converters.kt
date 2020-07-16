package com.krystofmacek.nyuseu.database

import androidx.room.TypeConverter
import com.krystofmacek.nyuseu.models.Source


class Converters {

    // Converting custom objects (Source) to primitives (String)
    @TypeConverter
    fun fromSource(source: Source): String {
        // Using only the name from the source
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}