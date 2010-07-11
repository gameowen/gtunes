package com.g2one.gtunes

class GtunesService {

    static transactional = true

    static expose = [ 'jmx' ]

    int getNumberOfAlbums() {
       Album.count()
    }

    int getNumberOfAlbumsForGenre(String genre) {
       Album.countByGenre(genre)
    }
}
