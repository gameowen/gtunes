package com.g2one.gtunes

import grails.test.*

class SongTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
   }

  void testTitleConstraints() {
      mockForConstraintsTests(Song)

      println 'testing nullable title'
      def song = new Song()
      assertFalse song.validate()
      assertEquals "nullable", song.errors.title

      println 'testing blank title'
      song = new Song(title: "", duration: 0)
      assertFalse song.validate()
      assertEquals "blank", song.errors.title

      println 'testing valid title'
      song = new Song(title: "Dummy")
      song.validate()
      assertFalse song.errors.hasFieldErrors('title')
  }

  void testDurationConstraints() {
      mockForConstraintsTests(Song)

      println 'testing nullable duration'
      def song = new Song()
      assertFalse song.validate()
      assertEquals "nullable", song.errors.duration

      println 'testing to small duration'
      song = new Song(duration: 0)
      assertFalse song.validate()
      assertEquals "min", song.errors.duration

      println 'testing valid duration'
      song = new Song(duration: 2)
      song.validate()
      assertFalse song.errors.hasFieldErrors('duration')
  }

  void testYearConstraints() {
      mockForConstraintsTests(Song)

      println 'testing nullable year'
      def song = new Song()
      assertFalse song.validate()
      assertEquals "nullable", song.errors.year

      println 'testing to small year'
      song = new Song(year: 1899)
      assertFalse song.validate()
      assertEquals "range", song.errors.year

      println 'testing to big year'
      song = new Song(year: 2899)
      assertFalse song.validate()
      assertEquals "range", song.errors.year

      println 'testing valid year'
      song = new Song(year: 2000)
      song.validate()
      assertFalse song.errors.hasFieldErrors('year')
  }

  void testTrackNumberConstraints() {
      mockForConstraintsTests(Song)

      println 'testing nullable track number'
      def song = new Song()
      assertFalse song.validate()
      assertEquals "nullable", song.errors.duration

      println 'testing to small track number'
      song = new Song(trackNumber: 0)
      assertFalse song.validate()
      assertEquals "min", song.errors.trackNumber

      println 'testing valid trackNumber'
      song = new Song(trackNumber: 1)
      song.validate()
      assertFalse song.errors.hasFieldErrors('trackNumber')
  }

   void testToString() {
      def title = 'dummy'
      assertEquals title, new Song(title: title).toString()
   }

}
