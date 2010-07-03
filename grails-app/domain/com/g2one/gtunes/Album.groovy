package com.g2one.gtunes

class Album {

   String title
   Integer year
   String genre

   List songs

   static constraints = {
      title (blank: false)
      year (range: 1900..2100)
   }

   static haMany = [ songs: Song ]
   static belongsTo = [ artist: Artist ]
   static optionals = [ 'year', 'genre' ]

   String toString() { title }
}
