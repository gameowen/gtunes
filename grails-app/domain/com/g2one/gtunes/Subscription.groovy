package com.g2one.gtunes

class Subscription {

   SubscriptionType type
   User user

    static constraints = {
    }

   static mapping ={
      user fetch: 'join'
   }
}

enum SubscriptionType {
   NEWSLETTER
}
