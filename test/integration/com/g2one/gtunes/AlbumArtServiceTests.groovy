package com.g2one.gtunes

import grails.test.*
import com.amazonaws.a2s.model.*
import com.amazonaws.a2s.*


class AlbumArtServiceTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
      // fix for http://jira.codehaus.org/browse/GRAILS-3666
      mockLogging(AlbumArtService.class, true)
   }

   protected void tearDown() {
      super.tearDown()
      GroovySystem.metaClassRegistry.removeMetaClass(AmazonA2SClient)
   }

   void testNoAccessKey() {
      def albumArtService = new AlbumArtService()
      assertEquals AlbumArtService.DEFAULT_ALBUM_ART_IMAGE, albumArtService.getAlbumArt("foo", "bar")
   }

   void testAmazonException() {
      AmazonA2SClient.metaClass.itemSearch = {
         ItemSearchRequest request ->
            throw new Exception('Amazon exception')
      }
      def albumArtService = new AlbumArtService()
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'
      assertEquals AlbumArtService.DEFAULT_ALBUM_ART_IMAGE, albumArtService.getAlbumArt("RadioHead", "The Bends")
   }

   void testAmazonResponse() {
      AmazonA2SClient.metaClass.itemSearch = {
         ItemSearchRequest request ->
            [items:[[item:[[largeImage:[URL:'/mock/url/album.jpg']]]]]]
      }
      def albumArtService = new AlbumArtService()
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'
      assertEquals '/mock/url/album.jpg', albumArtService.getAlbumArt("RadioHead", "The Bends")
   }
}
