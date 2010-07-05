package com.g2one.gtunes

import grails.test.*
import org.springframework.core.io.FileSystemResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver


class UrlMappingsTests extends GrailsUrlMappingsTestCase {
   protected void setUp() {
      super.setUp()
      // fix for http://jira.codehaus.org/browse/GRAILS-4244
      patternResolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader())

   }

   protected void tearDown() {
      super.tearDown()
   }

   void testRootMappings() {
      assertUrlMapping('/', controller: 'store', action: 'index')
   }

   void testGenreMappings() {
      assertUrlMapping('/genre/rock', controller: 'store', action: 'genre') {
         name = 'rock'
      }
   }

   void testErrorMappings() {
      assertUrlMapping(500, view: '/error')
   }
}

