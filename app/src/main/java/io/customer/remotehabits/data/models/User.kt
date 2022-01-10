package io.customer.remotehabits.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val email: String,
    @ColumnInfo(name = "name") val name: String?,
)
