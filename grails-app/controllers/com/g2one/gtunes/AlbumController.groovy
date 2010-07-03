package com.g2one.gtunes

class AlbumController {

   def index = { }

   def show = {
      def album = Album.get(params.id)
      if (album) {
         return [ album: album ]
      } else {
         response.sendError 404
      }
   }
}
