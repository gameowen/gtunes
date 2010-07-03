package com.g2one.gtunes

class Song {

   String title
   String genre
   Integer duration
   Integer year
   Integer trackNumber

   Album album
   Artist artist

   static constraints = {
      title (blank: false)
      duration (min: 1)
      year (range: 1900..2100)
      trackNumber (min: 1)
   }

   static optionals = ['genre', 'year', 'trackNumber']

   String toString() { title }
}
