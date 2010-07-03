package com.g2one.gtunes

class ArtistController {

    def index = { }

   def display = {
	def artist = Artist.get(params.id)
	if (artist) {
		def albums = Album.findAllByArtist(artist)
		render (template: 'artist', model: [artist: artist, albums: albums])
	} else {
		render 'Album not found'
	}
   }
}
