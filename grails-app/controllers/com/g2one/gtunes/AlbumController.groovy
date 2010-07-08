package com.g2one.gtunes

class AlbumController {

   def index = { }

   def display = {
      def album = Album.get(params.id)
      if (album) {
         def artist = album.artist
         if (request.xhr) {
            render (template: 'album', model: [ artist: artist, album: album ])
         } else {
            render (view: 'show', model: [ artist: artist, album: album ])
         }
      } else {
         render 'Album not found'
      }
   }

   def show = {
      def album = Album.get(params.id)
      if (album) {
         return [ artist: album.artist, album: album ]
      } else {
         response.sendError 404
      }
   }
}
