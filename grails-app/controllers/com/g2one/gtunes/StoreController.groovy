package com.g2one.gtunes

class StoreController {

   def index = { }

   def shop = {
      def genreList = Album.withCriteria {
         projections {
            distinct "genre"
         }
      }
      [ genres: genreList.sort() ]
   }

   def genre = {
      def max = params.max?.toInteger() ?: 10;
      max = max > 100 ? 10 : max
      def offset = params.offset?.toInteger() ?: 0

      def total = Album.countByGenre(params.name)
      def albumList = Album.withCriteria {
         eq 'genre' , params.name
         projections {
            artist { order 'name' }
         }
         maxResults max
         firstResult offset
      }
      [
         albums: albumList,
         totalAlbums: total,
         genre: params.name
      ]
   }
}
