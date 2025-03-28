package com.example.chesstats.presentation.firstScreen

import com.example.chesstats.R

enum class TitledMasters(val titled: Int) {
    GM(R.string.title_grandmaster),
    WGM(R.string.title_women_grandmaster),
    IM(R.string.title_international_master),
    WIM(R.string.title_women_international_master),
    FM(R.string.title_fide_master),
    WFM(R.string.title_women_fide_master),
    NM(R.string.title_national_master),
    WNM(R.string.title_women_national_master),
    CM(R.string.title_candidate_master),
    WCM(R.string.title_women_candidate_master);

    companion object {
        fun fromString(value: String?): TitledMasters? {
            return TitledMasters.entries.find { it.name == value }
        }
    }

}