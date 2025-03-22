package com.example.chesstats.presentation.firstScreen

enum class TitledMasters(val titled: String) {
    GM("Gran Maestro"),
    WGM("Gran Maestra Femenina"),
    IM("Maestro Internacional"),
    WIM("Maestra Internacional Femenina"),
    FM("Maestro Fide"),
    WFM("Maestra Fide Femenina"),
    NM("Maestro Nacional"),
    WNM("Maestra Nacional Femenina"),
    CM("Candidato a Maestro"),
    WCM("Candidata a Maestra Femenina");

    companion object {
        fun fromString(value: String?): TitledMasters? {
            return TitledMasters.entries.find { it.name == value }
        }
    }

}