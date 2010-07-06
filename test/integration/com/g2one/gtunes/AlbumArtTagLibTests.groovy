package com.g2one.gtunes

import grails.test.*
import com.amazonaws.a2s.model.*
import com.amazonaws.a2s.*


class AlbumArtTagLibTests extends GroovyPagesTestCase {

   def albumArtService

   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
      GroovySystem.metaClassRegistry.removeMetaClass(AmazonA2SClient)
   }

   void testMissingArgs() {
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'

      println 'testing no artist nor album'
      def template = '<music:albumArt />'
      assertOutputEquals '', template

      println 'testing no album'
      template = '<music:albumArt artist="U2" />'
      assertOutputEquals '', template

      println 'testing no artist'
      template = '<music:albumArt album="Never Mind" />'
      assertOutputEquals '', template
   }

   void testExceptionFromAmazon() {
      AmazonA2SClient.metaClass.itemSearch = {
         ItemSearchRequest request ->
         throw new Exception('amazon exception')
      }
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'

      def template = '<music:albumArt artist="Radiohead" album="The Bends" />'
      // expect extra space before '>' and after last argument
      def expected = "<img width=\"200\" src=\"${AlbumArtService.DEFAULT_ALBUM_ART_IMAGE}\" border=\"0\" ></img>"
      assertOutputEquals expected, template
   }

   void testAlbumArtFromAmazon() {
      AmazonA2SClient.metaClass.itemSearch = {
         ItemSearchRequest request ->
         [items:[[item:[[largeImage:[URL:'/mock/url/album.jpg']]]]]]
      }
      albumArtService.accessKeyId = 'AKIAIYQNVUS6VP5E7IPQ'

      def template = '<music:albumArt artist="Radiohead" album="The Bends" />'
      // expect extra space before '>' and after last argument
      def expected = '<img width="200" src="/mock/url/album.jpg" border="0" ></img>'
      assertOutputEquals expected, template

      template = '<music:albumArt artist="Radiohead" album="The Bends" width="100"/>'
      // expect extra space before '>' and after last argument
      expected = '<img width="100" src="/mock/url/album.jpg" border="0" ></img>'
      assertOutputEquals expected, template
   }
}
