package com.g2one.gtunes

class Artist implements Serializable {

   String name

   Date dateCreated
   Date lastUpdated

   static constraints = {
      name (blank:false)
   }

   static hasMany = [ albums: Album ]

   static searchable = [ only: [ 'name' ] ]
   String toString() { name }
}
