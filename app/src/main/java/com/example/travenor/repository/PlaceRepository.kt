package com.example.travenor.repository

import com.example.travenor.R
import com.example.travenor.model.Place

class PlaceRepository {
    fun getPlaces(): List<Place> {
        return listOf(
            Place(1, "Niladri Reservoir", "Tekergat, Sunamgnj", 894, R.drawable.place_1),
            Place(2, "Casa Las Tortugas", "Av Damero, Mexico", 832, R.drawable.place_2),
            Place(3, "Aonang Villa Resort", "Bastola, Islampur", 761, R.drawable.place_3),
            Place(4, "Rangauti Resort", "Sylhet, Airport Road", 857, R.drawable.place_4),
            Place(5, "Kachura Resort", "Utah, United States", 999, R.drawable.place_5),
            Place(6, "Shakran Beach", "Kuwait City, Kuwait", 1256, R.drawable.place_6)
        )
    }
}