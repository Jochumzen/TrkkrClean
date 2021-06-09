package com.trkkr.trkkrclean.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nationality (
    val id: Int? = null,
    val uid: String? = null,
    val nationality: String? = null,
    val language: String? = null,
    val capital: String? = null,
    val national_sport: String? = null,
    val flag: String? = null
) : Parcelable