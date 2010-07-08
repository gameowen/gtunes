package com.g2one.gtunes

import grails.test.*

class PaymentTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testInvoiceNumberConstraints() {
       mockForConstraintsTests(Payment)

      println "testing nullable invoiceNumber"
      def payment = new Payment()
      assertFalse payment.validate()
      assertEquals 'nullable', payment.errors.invoiceNumber

      println "testing blank invoiceNumber"
      payment = new Payment(invoiceNumber: '')
      assertFalse payment.validate()
      assertEquals 'blank', payment.errors.invoiceNumber

      println "testing invalid invoiceNumber"
      payment = new Payment(invoiceNumber: 'IN--')
      assertFalse payment.validate()
      assertEquals 'matches', payment.errors.invoiceNumber

      println "testing valid invoiceNumber"
      payment = new Payment(invoiceNumber: 'INV-1-1')
      assertFalse payment.validate()
      assertFalse payment.errors.hasFieldErrors('invoiceNumber')
    }
}
