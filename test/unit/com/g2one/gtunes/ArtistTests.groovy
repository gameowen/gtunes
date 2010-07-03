package com.g2one.gtunes

import grails.test.*

class ArtistTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
   }

   void testNameConstraints() {
      mockForConstraintsTests(Artist)

      println "testing nullable name"
      def artist = new Artist()
      assertFalse artist.validate()
      assertEquals "nullable", artist.errors.name

      println "testing blank name"
      artist = new Artist(name: "")
      assertFalse artist.validate()
      assertEquals "blank", artist.errors.name

      println "testing valid name"
      artist = new Artist(name: 'dummy')
      assertTrue artist.validate()
   }

   void testToString() {
      def name = 'dummy'
      assertEquals name, new Artist(name: name).toString()
   }

}
