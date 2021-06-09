package com.trkkr.trkkrclean.utilities

data class OpeningHours(
    var literal: String?,
    var ruleSets: List<RuleSet>?
) {
    data class RuleSet (
        var literal: String
    )

    companion object {
        fun createOpeningHours(openingHours: String) : OpeningHours {
            val ruleSets : List<RuleSet>? = null
            return OpeningHours(openingHours, ruleSets)
        }
    }

}