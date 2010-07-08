package com.g2one.gtunes

class Payment implements Serializable {

   String invoiceNumber

   User user

    static constraints = {
       invoiceNumber (blank: false, matches: /INV-\d+?-\d+/)
    }

   static hasMany = [ albumPayments: AlbumPayment ]

}
