package com.g2one.gtunes

import grails.test.*

class GtunesServiceTests extends GrailsUnitTestCase {

   def gtunesService

    protected void setUp() {
        super.setUp()
        gtunesService = new GtunesService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNumberOfAlbums() {
      assertEquals Album.count(), gtunesService.getNumberOfAlbums()
    }

   void testNumberOfAlbumsForGenre() {
      def genre = 'Alternative'
      assertEquals Album.countByGenre(genre), gtunesService.getNumberOfAlbumsForGenre(genre)
   }
}
