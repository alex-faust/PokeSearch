package com.example.pokesearch.utils

import androidx.room.TypeConverter
import com.example.pokesearch.model.Types

class TypesConverters {
    companion object {
        @TypeConverter
        fun fromTypes(value: Types): List<String?> {
            return listOf(value.type1, value.type2)
        }

        @TypeConverter
        fun toTypes(value: List<String?>): Types {
            return Types(value[0]!!, value[1])
        }
    }
}