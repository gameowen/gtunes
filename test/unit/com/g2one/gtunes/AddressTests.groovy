package com.g2one.gtunes

import grails.test.*

class AddressTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
   }

   void testNumberConstraints() {
      mockForConstraintsTests(Address)

      println "testing nullable number"
      def address = new Address()
      assertFalse address.validate()
      assertEquals 'nullable', address.errors.number

      println "testing blank number"
      address = new Address(number: '')
      assertFalse address.validate()
      assertEquals 'blank', address.errors.number

      println "testing to long number"
      address = new Address(number: String.format('%1$21s', 'number'))
      assertFalse address.validate()
      assertEquals 'maxSize', address.errors.number

      println "testing valid number"
      address = new Address(number: '1')
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('number')
   }

   void testStreetConstraints() {
      mockForConstraintsTests(Address)

      println "testing nullable street"
      def address = new Address()
      assertFalse address.validate()
      assertEquals 'nullable', address.errors.street

      println "testing blank street"
      address = new Address(street: '')
      assertFalse address.validate()
      assertEquals 'blank', address.errors.street

      println "testing to long street"
      address = new Address(street: String.format('%1$151s', 'street'))
      assertFalse address.validate()
      assertEquals 'maxSize', address.errors.street

      println "testing valid street"
      address = new Address(street: 'dummy')
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('street')
   }

   void testCityConstraints() {
      mockForConstraintsTests(Address)

      println "testing nullable city"
      def address = new Address()
      assertFalse address.validate()
      assertEquals 'nullable', address.errors.city

      println "testing blank city"
      address = new Address(city: '')
      assertFalse address.validate()
      assertEquals 'blank', address.errors.city

      println "testing to long city"
      address = new Address(city: String.format('%1$151s', 'city'))
      assertFalse address.validate()
      assertEquals 'maxSize', address.errors.city

      println "testing valid city"
      address = new Address(city: 'dummy')
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('city')
   }

   void testPostCodeConstraints() {
      mockForConstraintsTests(Address)

      println "testing nullable postCode"
      def address = new Address()
      assertFalse address.validate()
      assertEquals 'nullable', address.errors.postCode

      println "testing blank postCode"
      address = new Address(postCode: '')
      assertFalse address.validate()
      assertEquals 'blank', address.errors.postCode

      println "testing to long postCode"
      address = new Address(postCode: String.format('%1$21s', 'postCode'))
      assertFalse address.validate()
      assertEquals 'maxSize', address.errors.postCode

      println "testing valid postCode"
      address = new Address(postCode: 'dummy')
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('postCode')
   }

   void testCountryConstraints() {
      mockForConstraintsTests(Address)

      println "testing nullable country"
      def address = new Address()
      assertFalse address.validate()
      assertEquals 'nullable', address.errors.country

      println "testing blank country"
      address = new Address(country: '')
      assertFalse address.validate()
      assertEquals 'blank', address.errors.country

      println "testing to long country"
      address = new Address(country: String.format('%1$101s', 'country'))
      assertFalse address.validate()
      assertEquals 'maxSize', address.errors.country

      println "testing valid country"
      address = new Address(country: 'dummy')
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('country')
   }

   void testStateConstraints() {
      mockForConstraintsTests(Address)

      println "testing nullable state"
      def address = new Address()
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('state')

      println "testing blank state"
      address = new Address(state: '')
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('state')

      println "testing valid state"
      address = new Address(state: 'dummy')
      assertFalse address.validate()
      assertFalse address.errors.hasFieldErrors('state')
   }

}
