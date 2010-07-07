package com.g2one.gtunes

import grails.test.*
import com.amazon.advertising.api.AmazonPAAClient


class AlbumArtServiceTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
      // fix for http://jira.codehaus.org/browse/GRAILS-3666
      mockLogging(AlbumArtService.class, true)
   }

   protected void tearDown() {
      super.tearDown()
      GroovySystem.metaClassRegistry.removeMetaClass(AmazonPAAClient)
   }

   void testNoAccessKey() {
      def albumArtService = new AlbumArtService()
      assertEquals AlbumArtService.DEFAULT_ALBUM_ART_IMAGE, albumArtService.getAlbumArt("foo", "bar")
   }

   void testAmazonException() {
      AmazonPAAClient.metaClass.getAlbumArtUrl = {
         String album, String artist ->
            throw new Exception('Amazon exception')
      }
      def albumArtService = new AlbumArtService()
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'
      assertEquals AlbumArtService.DEFAULT_ALBUM_ART_IMAGE, albumArtService.getAlbumArt("RadioHead", "The Bends")
   }

   void testAmazonResponse() {
      AmazonPAAClient.metaClass.getAlbumArtUrl = {
         String album, String artist ->
            '/mock/url/album.jpg'
      }
      def albumArtService = new AlbumArtService()
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'
      assertEquals '/mock/url/album.jpg', albumArtService.getAlbumArt("RadioHead", "The Bends")
   }

   void testAlbumArtNotFoundAtAmazon() {
      AmazonPAAClient.metaClass.getAlbumArtUrl = {
         String album, String artist ->
            return null
      }
      def albumArtService = new AlbumArtService()
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'
      assertEquals AlbumArtService.DEFAULT_ALBUM_ART_IMAGE, albumArtService.getAlbumArt("RadioHead", "The Bends")
   }
}
