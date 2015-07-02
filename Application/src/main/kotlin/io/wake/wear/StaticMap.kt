package io.wake.wear

public fun getStaticMapUrl(latitude: Double, longitude: Double, zoom: Int = 17): String =
        "https://maps.googleapis.com/maps/api/staticmap?zoom=${zoom}&size=256x256&maptype=roadmap&markers=color:red%7C${latitude},${longitude}"
