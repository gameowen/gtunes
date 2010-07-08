package com.g2one.gtunes

class Address implements Serializable {

   String number
   String street
   String city
   String state
   String postCode
   String country

    static constraints = {
       number (blank: false, maxSize: 20)
       street (blank: false, maxSize: 150)
       city (blank: false, maxSize: 150)
       state (nullable: true)
       postCode (blank:false, maxSize: 20)
       country (blank: false, maxSize: 100)
    }
}
