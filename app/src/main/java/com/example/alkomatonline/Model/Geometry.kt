package com.example.alkomatonline.Model

import android.location.Location

class Geometry {
    var viewport: Viewport? = null
    var location: Location? = null


    inner class Location {
        var lat:Double=0.0
        var lng:Double=0.0

    }
}