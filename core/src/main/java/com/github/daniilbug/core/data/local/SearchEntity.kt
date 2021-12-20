package com.github.daniilbug.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "first_definition") val firstDefinition: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)