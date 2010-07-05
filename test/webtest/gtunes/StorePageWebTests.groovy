package gtunes



class StorePageWebTests extends grails.util.WebTest {

   // Unlike unit tests, functional tests are sometimes sequence dependent.
   // Methods starting with 'test' will be run automatically in alphabetical order.
   // If you require a specific sequence, prefix the method name (following 'test') with a sequence
   // e.g. test001XclassNameXListNewDelete

   void test001DefaultRedirect() {
      invoke '/', description: 'Go to default page'
      verifyTitle 'gTunes Store'
   }

   void test002LoginNonExsistingUser() {
      invoke '/', description: 'Try to log in as a non exsisting user'
      setInputField name: 'login', value: 'dummy'
      setInputField name: 'password', value: 'dummy'
      clickButton name: 'loginButton'
      //verifyXPath xpath: "//div[2]/div/ul/li", text: 'User not found!'
   }

}
