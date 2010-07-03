package gtunes



class StorePageWebTests extends grails.util.WebTest {

   // Unlike unit tests, functional tests are sometimes sequence dependent.
   // Methods starting with 'test' will be run automatically in alphabetical order.
   // If you require a specific sequence, prefix the method name (following 'test') with a sequence
   // e.g. test001XclassNameXListNewDelete

   void testDefaultRedirect() {
      invoke '/', description: 'Go to default page'
      verifyTitle 'gTunes Store'
   }

}
