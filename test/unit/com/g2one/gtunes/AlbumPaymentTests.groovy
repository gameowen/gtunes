package com.g2one.gtunes

import grails.test.*

class AlbumPaymentTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
   }

   void testShippingAddressConstraints() {
      mockForConstraintsTests(AlbumPayment)

      println "testing nullable shippingAddress"
      def albumPayment = new AlbumPayment()
      assertFalse albumPayment.validate()
      assertFalse albumPayment.errors.hasFieldErrors('shippingAddress')

      println "testing valid shippingAddress"
      albumPayment = new AlbumPayment(shippingAddress: new Address())
      assertFalse albumPayment.validate()
      assertFalse albumPayment.errors.hasFieldErrors('shippingAddress')
   }

}
