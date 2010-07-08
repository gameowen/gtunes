package com.g2one.gtunes

import grails.test.*

class AlbumTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
   }

   void testTitleConstraints() {
      mockForConstraintsTests(Album)
      
      println "testing nullable title"
      def album = new Album()
      assertFalse album.validate()
      assertEquals "nullable", album.errors.title

      println "testing blank title"
      album = new Album(title: "")
      assertFalse album.validate()
      assertEquals "blank", album.errors.title
      
      println "testing valid title"
      album = new Album(title: 'dummy')
      album.validate()
      assertFalse album.errors.hasFieldErrors('title')
   }

   void testYearConstraints() {
      mockForConstraintsTests(Album)

      println "testing below range year"
      def album = new Album(year: 1877)
      assertFalse album.validate()
      assertEquals "range", album.errors.year

      println "testing above range year"
      album = new Album(year: 2277)
      assertFalse album.validate()
      assertEquals "range", album.errors.year

      println "testing valid year"
      album = new Album(year: 2010)
      album.validate()
      assertFalse album.errors.hasFieldErrors('year')
   }

   void testPriceConstraints() {
      mockForConstraintsTests(Album)

      println "testing nullable price"
      def album = new Album()
      assertFalse album.validate()
      assertEquals "nullable", album.errors.price


      println "testing invalid price"
      // no error messages
      album = new Album(price: 1.987)
      assertFalse album.validate()
      assertEquals null, album.errors.price
      assertFalse 1.99 - album.price > 0.01

      println "testing valid price"
      album = new Album(price: 1.98)
      assertFalse album.validate()
      assertFalse album.errors.hasFieldErrors('price')
   }

   void testToString() {
      def title = 'dummy'
      assertEquals title, new Album(title: title).toString()
   }

}
