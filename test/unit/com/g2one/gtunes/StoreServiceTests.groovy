package com.g2one.gtunes

import grails.test.*
import groovy.mock.interceptor.*

class StoreServiceTests extends GrailsUnitTestCase {

   // http://grails-groovy.blogspot.com/2009/02/unit-testing-testing-service-in-3_07.html

   // http://svn.codehaus.org/groovy/branches/GROOVY_1_7_X/src/main/groovy/mock/interceptor/

   def storeService

   protected void setUp() {
      super.setUp()
        
      // initialize service
      // there is no dynamic injection
      storeService = new StoreService()
   }

   protected void tearDown() {
      super.tearDown()
//      GroovySystem.metaClassRegistry.removeMetaClass(CreditCardCommand)
   }

   void testInvalidCreditCard() {
      CreditCardCommand.metaClass.validate {
         false
      }
      shouldFail(IllegalStateException) {
         storeService.purchaseAlbum(new User(), new CreditCardCommand(), [])
      }
   }

   void testInvalidAlbumPayment() {
      CreditCardCommand.metaClass.validate {
         true
      }
      AlbumPayment.metaClass.validate {
         false
      }
      shouldFail(IllegalStateException) {
         storeService.purchaseAlbum(new User(), new CreditCardCommand(), [ new AlbumPayment() ])
      }
   }

   void testPaymentSaveFailed() {
      CreditCardCommand.metaClass.validate {
         true
      }
      AlbumPayment.metaClass.validate {
         true
      }
      Payment.metaClass.save {
         false
      }
      shouldFail(IllegalStateException) {
         storeService.purchaseAlbum(new User(), new CreditCardCommand(), [ new AlbumPayment() ])
      }
   }

   void testUserSaveFailed() {
      CreditCardCommand.metaClass.validate {
         true
      }
      AlbumPayment.metaClass.validate {
         true
      }
      Payment.metaClass.save {
         true
      }
      shouldFail(IllegalStateException) {
         storeService.purchaseAlbum(new User(), new CreditCardCommand(), [ new AlbumPayment() ])
      }
   }

   void testSuccessfullPayment() {
      // mock for credit card
      // which always validates
      def ccMock = new MockFor(CreditCardCommand)
      ccMock.demand.validate {
         true
      }

      // mock for album
      // demanding getting all songs exactly ones
      def albumMock = new MockFor(Album)
      def songs = [ new Song(), new Song() ]
      albumMock.demand.getSongs { songs }
      def album = albumMock.proxyInstance()

      // mock for album payment
      // which always validates
      // demanding settin user instance
      // and getting album property twice
      def apMock = new MockFor(AlbumPayment)
      apMock.demand.with {
         setUser {}
         validate {
            true
         }
         getAlbum(2) { album }
      }

      // mock for user
      // demanding getting id (for invoiceNumber)
      // adding purchased songs (as many as there is in album - exactly)
      // adding purchased album
      // and saving to datastore with always successfull result
      def userMock = new MockFor(User)
      userMock.demand.with {
         getId { 1 }
         addToPurchasedSongs(songs.size()) {}
         addToPurchasedAlbums {}
         save { true }
      }
      def user = userMock.proxyInstance()

      // mock for payment
      // demanding creating of new payment instance
      // adding album payments
      // and saving to data store with always successfull result
      def paymentMock = new MockFor(Payment, true)
      def payment = new Payment(user: user, invoiceNumber: 'INV-1-123123')
      paymentMock.demand.with {
         Payment() { payment }
         addToAlbumPayments { }
         save { true }
      }
      paymentMock.use {
         def p = storeService.purchaseAlbum(user, ccMock.proxyInstance(), [ apMock.proxyInstance() ])
         assertEquals payment, p
      }
      
   }

}
