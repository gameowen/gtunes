package com.g2one.gtunes

import grails.test.*

class UserControllerTests extends ControllerUnitTestCase {
   protected void setUp() {
      super.setUp()
   }

   protected void tearDown() {
      super.tearDown()
   }

   void testPasswordDontMatch() {
      mockRequest.method = "POST"
      mockDomain(User)

      mockParams.login = "joebloggs"
      mockParams.password = "password"
      mockParams.confirm = "different"
      mockParams.firstName = "Joe"
      mockParams.lastName = "Bloggs"

      def model = controller.register()

      assertNotNull model?.user
      def user = model.user
      assertTrue user.hasErrors()
      assertEquals "user.password.dontmatch", user.errors.password
   }

   void testRegistrationFailed() {
      mockRequest.method = "POST"
      mockDomain(User)

      mockParams.login = ""
      def model = controller.register()

      assertNull mockSession.user
      assertNotNull model
      def user = model.user
      assertNotNull user
      assertTrue user.hasErrors()
      assertEquals "blank", user.errors.login
      assertEquals "nullable", user.errors.password
      assertEquals "nullable", user.errors.firstName
      assertEquals "nullable", user.errors.lastName
   }

   void testRegistrationSuccess() {
      mockRequest.method = "POST"
      mockDomain(User)

      mockParams.login = "joebloggs"
      mockParams.password = "password"
      mockParams.confirm = "password"
      mockParams.firstName = "Joe"
      mockParams.lastName = "Bloggs"

      def model = controller.register()
      assertEquals 'store', redirectArgs.controller
      assertNotNull mockSession.user
   }

   void testLoginUserNotFound() {
      mockRequest.method = 'POST'
      mockDomain(User)
      MockUtils.prepareForConstraintsTests(LoginCommand)
      def cmd = new LoginCommand(login: 'fred', password: 'letmein')
      cmd.validate()
      controller.login(cmd)
      assertTrue cmd.hasErrors()
      assertEquals "user.not.found", cmd.errors.login
      assertEquals "/store/index", renderArgs.view
   }

   void testLoginPasswordInvalid() {
      mockRequest.method = 'POST'
      mockDomain(User, [ new User(login: 'fred', password: 'realpass') ])
      MockUtils.prepareForConstraintsTests(LoginCommand)
      def cmd = new LoginCommand(login: 'fred', password: 'letmein')
      cmd.validate()
      controller.login(cmd)
      assertTrue cmd.hasErrors()
      assertEquals "user.password.invalid", cmd.errors.password
      assertEquals "/store/index", renderArgs.view
   }

   void testLoginSuccess() {
      mockRequest.method = 'POST'
      mockDomain(User, [ new User(login: 'fred', password: 'letmein') ])
      MockUtils.prepareForConstraintsTests(LoginCommand)
      def cmd = new LoginCommand(login: 'fred', password: 'letmein')
      cmd.validate()
      controller.login(cmd)
      assertFalse cmd.hasErrors()
      assertNotNull mockSession.user
      assertEquals 'store', redirectArgs.controller
   }

   void testLogout() {
      def user = new User(login: 'fred', password: 'letmein');
      mockSession.user = user
      controller.logout()
      assertNull mockSession.user
      assertEquals 'store', redirectArgs.controller
   }
}
