package com.g2one.gtunes

class Artist {

    String name

    static constraints = {
       name (blank:false)
    }

   static hasMany = [ album: Album ]

   String toString() { name }
}
