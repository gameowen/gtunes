package com.g2one.gtunes


class NewsLetterJob {

   def mailService

   def name ='newsletter'
   def group = 'subscriptions'
   
   // fire at 7:15am on last Thursday of every month
   def cronExpression = '0 15 7 ? * 5L'

   def execute() {
      def emails = Subscription.withCriteria {
         projections {
            user {
               property 'email'
            }
         }
         eq ('type', SubscriptionType.NEWSLETTER)
      }
      def now = new Date()
      def albumList = Album.findAllByDateCreatedBetween(
         now-7,
         now,
         [ sort: 'dateCreated' ],
         max: 10,
         order: 'desc'
      )
      def month = new GregorianCalendar().get(Calendar.MONTH)
      for (email in emails) {
         mailService.sendMail {
            from 'newsletter@gtunes.com'
            to email
            title "The gTunes Monthly News Letter - ${month}"
            body view: 'emails/newsletter', model: [
               latetestAlbums: albumList,
               month: month
            ]
         }
      }
   }
}
