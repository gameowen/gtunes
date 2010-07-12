package com.g2one.gtunes

class User implements Serializable {

    String login
    String password
    String firstName
    String lastName
    String email

    Date dateCreated
    Date lastUpdated

    static hasMany = [
       purchasedAlbums: Album,
       purchasedSongs: Song
    ]

    static constraints = {
        login (blank: false, size: 5..15, matches: /[\S]+/, unique: true)
        password (blank: false, size: 5..15, matches: /[\S]+/)
        firstName (blank: false)
        lastName (blank: false)
        email (email: true, blank: false, unique: true)
    }

}
