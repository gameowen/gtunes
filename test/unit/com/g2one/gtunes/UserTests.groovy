package com.g2one.gtunes

import grails.test.*

class UserTests extends GrailsUnitTestCase {
   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
   }

   void testLoginConstraints() {
      def  existingUser = new User(login: 'first', password: 'tajne', firstName: 'first', lastName: 'last')
      mockForConstraintsTests(User, [ existingUser ])

      // nullable login
      def user = new User()
      assertFalse user.validate()
      assertEquals "nullable", user.errors.login

      println 'testing blank login'
      user = new User(login: '')
      assertFalse user.validate()
      assertEquals "blank", user.errors.login

      println 'testing too short login'
      user = new User(login: 'a')
      assertFalse user.validate()
      assertEquals "size", user.errors.login

      println 'testing too long login'
      user = new User(login: "aaaaaaaaaaaaaaaa")
      assertFalse user.validate()
      assertEquals "size", user.errors.login

      println 'testing invalid char in login'
      user = new User(login: "a1aaa\taaaa")
      assertFalse user.validate()
      assertEquals "matches", user.errors.login

      println 'testing not unique login'
      user = new User(login: "first")
      assertFalse user.validate()
      assertEquals "unique", user.errors.login

      println 'testing valid login'
      assertTrue existingUser.validate()
   }

   void testPasswordConstraints() {
      mockForConstraintsTests(User)

      println "testing nullable password"
      def user = new User()
      assertFalse user.validate()
      assertEquals "nullable", user.errors.password

      println "testing blank password"
      user = new User(password: "")
      assertFalse user.validate()
      assertEquals "blank", user.errors.password

      println "testing too short password"
      user = new User(password: "a")
      assertFalse user.validate()
      assertEquals "size", user.errors.password

      println "testing too long password"
      user = new User(password: "aaaaaaaaaaaaaaaa")
      assertFalse user.validate()
      assertEquals "size", user.errors.password

      println "testing invalid char in password"
      user = new User(password: "aaaa\na1")
      assertFalse user.validate()
      assertEquals "matches", user.errors.password

      println "testing valid password"
      user = new User(login: 'first', password: 'tajne', firstName: 'first', lastName: 'last')
      assertTrue user.validate()
   }

   void testFirstNameConstraints() {
      mockForConstraintsTests(User)

      println "testing nullable firstName"
      def user = new User()
      assertFalse user.validate()
      assertEquals "nullable", user.errors.firstName

      println "testing blank firstName"
      user = new User(firstName: "")
      assertFalse user.validate()
      assertEquals "blank", user.errors.firstName

      println "testing valid firstName"
      user = new User(login: 'first', password: 'tajne', firstName: 'first', lastName: 'last')
      assertTrue user.validate()
   }

   void testLastNameConstraints() {
      mockForConstraintsTests(User)

      println "testing nullable lastName"
      def user = new User()
      assertFalse user.validate()
      assertEquals "nullable", user.errors.lastName

      println "testing blank lastName"
      user = new User(lastName: "")
      assertFalse user.validate()
      assertEquals "blank", user.errors.lastName

      println "testing valid lastName"
      user = new User(login: 'first', password: 'tajne', firstName: 'first', lastName: 'last')
      assertTrue user.validate()
   }

}
