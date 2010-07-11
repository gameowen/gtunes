package com.g2one.gtunes

import grails.test.*
import groovy.mock.interceptor.*

class StoreServiceTests extends GrailsUnitTestCase {

   // http://grails-groovy.blogspot.com/2009/02/unit-testing-testing-service-in-3_07.html

   // http://www.jarvana.com/jarvana/view/org/codehaus/groovy/groovy/1.7.2/groovy-1.7.2-sources.jar!/groovy/mock/interceptor/MockFor.groovy

   def storeService

   protected void setUp() {
      super.setUp()
        
      // initialize service
      // there is no dynamic injection
      storeService = new StoreService()
   }

   protected void tearDown() {
      super.tearDown()
   }

   void testInvalidCreditCard() {
      // create mock for CreditCardCommand class
      def ccMock = new MockFor(CreditCardCommand)

      // demand the validate method exactly once,
      // and programming it to return false
      ccMock.demand.validate {
         false
      }

      ccMock.use{
         // validation will fail
         // expecting exception to be thrown
         shouldFail(IllegalStateException) {
            storeService.purchaseAlbum(new User(), new CreditCardCommand(), [])
         }
      }
   }

   void testInvalidAlbumPayment() {
      def ccMock = new MockFor(CreditCardCommand)
      ccMock.demand.validate {
         true
      }

      def apMock = new MockFor(AlbumPayment)
      apMock.demand.with {
         setUser {}
         validate {
            false
         }
      }

      ccMock.use{
         // validation will fail
         // expecting exception to be thrown
         shouldFail(IllegalStateException) {
            storeService.purchaseAlbum(new User(), new CreditCardCommand(), [ apMock.proxyInstance() ])
         }
      }
   }

}
