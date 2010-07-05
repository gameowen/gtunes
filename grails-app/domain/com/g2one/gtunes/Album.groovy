package com.g2one.gtunes

class Album implements Serializable {

   String title
   Integer year
   String genre

   Date dateCreated
   Date lastUpdated

   List songs

   static constraints = {
      title (blank: false)
      year (range: 1900..2100)
   }

   static hasMany = [ songs: Song ]
   static belongsTo = [ artist: Artist ]
   static optionals = [ 'year', 'genre' ]

   static searchable = [ only: ['genre', 'title' ] ]

   String toString() { title }
}
