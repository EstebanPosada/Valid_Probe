package com.estebanposada.prueba_valid.service.model

fun Artists.toRoomArtist(): Artist =
    Artist(
        name,
        0,
        mbid,
        url,
        listeners,
        streamable,
        image
    )

fun Artist.toDomainArtist(): Artists =
    Artists(
        name,
        mbid,
        url,
        listeners,
        streamable,
        image
    )