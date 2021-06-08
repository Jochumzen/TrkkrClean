package com.trkkr.trkkrclean.utilities

import com.mapbox.geojson.Feature
import com.trkkr.trkkrclean.domain.OsmType

fun Feature.getOSMId(): String?
{
        return this.id()?.dropLast(1)
}

fun Feature.getOSMType(): OsmType?
{
    val lastChar = this.id()?.lastOrNull()

    return if(lastChar != null)
        when (lastChar) {
            '0' -> OsmType.NODE
            '1' -> OsmType.WAY
            '2' -> OsmType.RELATION
            else -> OsmType.UNKNOWN
        }
    else
        null
}