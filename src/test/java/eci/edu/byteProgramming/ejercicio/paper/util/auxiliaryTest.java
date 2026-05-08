package eci.edu.byteProgramming.ejercicio.paper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class auxiliaryTest {

    // ─── CreditCardFactory ───────────────────────────────────────────────────

    @Test
    void creditCard_createPaymentMethod_returnsNewInstance() {
        CreditCardFactory factory = new CreditCardFactory(100.0, "C001", "Test",
                "4111111111111111", "Juan Melo", "12/26", "123", "Calle 100");
        PaymentMethod payment = factory.createPaymentMethod(200.0, "C002", "New");
        assertNotNull(payment);
        assertEquals(200.0, payment.getAmount());
    }

    @Test
    void creditCard_validatePaymentMethod_validData_returnsTrue() {
        CreditCardFactory factory = new CreditCardFactory(100.0, "C001", "Test",
                "4111111111111111", "Juan Melo", "12/26", "123", "Calle 100");
        assertTrue(factory.validatePaymentMethod());
    }

    @Test
    void creditCard_validatePaymentMethod_shortCardNumber_returnsFalse() {
        CreditCardFactory factory = new CreditCardFactory(100.0, "C001", "Test",
                "411", "Juan Melo", "12/26", "123", "Calle 100");
        assertFalse(factory.validatePaymentMethod());
    }

    @Test
    void creditCard_validatePaymentMethod_shortCVV_returnsFalse() {
        CreditCardFactory factory = new CreditCardFactory(100.0, "C001", "Test",
                "4111111111111111", "Juan Melo", "12/26", "12", "Calle 100");
        assertFalse(factory.validatePaymentMethod());
    }

    @Test
    void creditCard_validatePaymentMethod_invalidExpirationDate_returnsFalse() {
        CreditCardFactory factory = new CreditCardFactory(100.0, "C001", "Test",
                "4111111111111111", "Juan Melo", "1226", "123", "Calle 100");
        assertFalse(factory.validatePaymentMethod());
    }

    @Test
    void creditCard_getPaymentMethod_returnsCorrectType() {
        CreditCardFactory factory = new CreditCardFactory(100.0, "C001", "Test",
                "4111111111111111", "Juan Melo", "12/26", "123", "Calle 100");
        assertEquals("CREDIT_CARD", factory.getPaymentMethod());
    }

    @Test
    void creditCard_maskCardNumber_masksCorrectly() {
        CreditCardFactory factory = new CreditCardFactory(100.0, "C001", "Test",
                "4111111111111111", "Juan Melo", "12/26", "123", "Calle 100");
        assertEquals("**** **** **** 1111", factory.maskCardNumber());
    }

    // ─── PaypalFactory ───────────────────────────────────────────────────────

    @Test
    void paypal_createPaymentMethod_returnsNewInstance() {
        PaypalFactory factory = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");
        PaymentMethod payment = factory.createPaymentMethod(150.0, "C002", "PayPal");
        assertNotNull(payment);
        assertEquals(150.0, payment.getAmount());
    }

    @Test
    void paypal_validatePaymentMethod_validCredentials_returnsTrue() {
        PaypalFactory factory = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");
        assertTrue(factory.validatePaymentMethod());
    }

    @Test
    void paypal_validatePaymentMethod_invalidEmail_returnsFalse() {
        PaypalFactory factory = new PaypalFactory(100.0, "C001", "Test",
                "invalidemail", "validtoken12345");
        assertFalse(factory.validatePaymentMethod());
    }

    @Test
    void paypal_validatePaymentMethod_shortToken_returnsFalse() {
        PaypalFactory factory = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "short");
        assertFalse(factory.validatePaymentMethod());
    }

    @Test
    void paypal_getPaymentMethod_returnsCorrectType() {
        PaypalFactory factory = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");
        assertEquals("PAYPAL", factory.getPaymentMethod());
    }

    @Test
    void paypal_processPayment_validCredentials_returnsTrue() {
        PaypalFactory factory = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");
        assertTrue(factory.processPayment());
        assertEquals(PaymentStatus.COMPLETED, factory.getStatus());
    }

    @Test
    void paypal_processPayment_invalidCredentials_returnsFalse() {
        PaypalFactory factory = new PaypalFactory(100.0, "C001", "Test",
                "invalidemail", "short");
        assertFalse(factory.processPayment());
        assertEquals(PaymentStatus.FAILED, factory.getStatus());
    }

    // ─── CryptoFactory ───────────────────────────────────────────────────────

    @Test
    void crypto_createPaymentMethod_returnsNewInstance() {
        CryptoFactory factory = new CryptoFactory(100.0, "C001", "Test",
                "1A2B3C4D5E6F7G8H9I0J1K2L3M4N5O6P", "BTC", 500.0);
        PaymentMethod payment = factory.createPaymentMethod(100.0, "C002", "Crypto");
        assertNotNull(payment);
        assertEquals(100.0, payment.getAmount());
    }

    @Test
    void crypto_validatePaymentMethod_sufficientBalance_returnsTrue() {
        CryptoFactory factory = new CryptoFactory(100.0, "C001", "Test",
                "1A2B3C4D5E6F7G8H9I0J1K2L3M4N5O6P", "BTC", 500.0);
        assertTrue(factory.validatePaymentMethod());
    }

    @Test
    void crypto_validatePaymentMethod_insufficientBalance_returnsFalse() {
        CryptoFactory factory = new CryptoFactory(500.0, "C001", "Test",
                "1A2B3C4D5E6F7G8H9I0J1K2L3M4N5O6P", "BTC", 50.0);
        assertFalse(factory.validatePaymentMethod());
    }

    @Test
    void crypto_validatePaymentMethod_shortWalletAddress_returnsFalse() {
        CryptoFactory factory = new CryptoFactory(100.0, "C001", "Test",
                "shortaddress", "BTC", 500.0);
        assertFalse(factory.validatePaymentMethod());
    }

    @Test
    void crypto_getPaymentMethod_returnsCorrectType() {
        CryptoFactory factory = new CryptoFactory(100.0, "C001", "Test",
                "1A2B3C4D5E6F7G8H9I0J1K2L3M4N5O6P", "BTC", 500.0);
        assertEquals("CRYPTOCURRENCY", factory.getPaymentMethod());
    }

    // ─── Inventory ───────────────────────────────────────────────────────────

    @Test
    void inventory_discountProduct_sufficientStock_returnsTrue() {
        Inventory inventory = new Inventory();
        assertTrue(inventory.discountProduct("LAPTOP001", 2));
        assertEquals(3, inventory.getStock("LAPTOP001"));
    }

    @Test
    void inventory_discountProduct_insufficientStock_returnsFalse() {
        Inventory inventory = new Inventory();
        assertFalse(inventory.discountProduct("LAPTOP001", 100));
    }

    @Test
    void inventory_getProduct_existingProduct_returnsProduct() {
        Inventory inventory = new Inventory();
        Product product = inventory.getProduct("LAPTOP001");
        assertNotNull(product);
        assertEquals("Gaming Laptop", product.getName());
    }

    @Test
    void inventory_getStock_nonExistentProduct_returnsZero() {
        Inventory inventory = new Inventory();
        assertEquals(0, inventory.getStock("NONEXISTENT"));
    }

    // ─── Facturation ─────────────────────────────────────────────────────────

    @Test
    void facturation_calculateTax_returnsCorrectAmount() {
        Facturation facturation = new Facturation();
        assertEquals(19.0, facturation.calculateTax(100.0), 0.001);
    }

    @Test
    void facturation_calculateTotal_returnsCorrectAmount() {
        Facturation facturation = new Facturation();
        assertEquals(119.0, facturation.calculateTotal(100.0), 0.001);
    }

    @Test
    void facturation_getNextInvoiceNumber_returnsFormattedString() {
        Facturation facturation = new Facturation();
        assertTrue(facturation.getNextInvoiceNumber().startsWith("INV-"));
    }

    @Test
    void facturation_generateInvoice_doesNotThrow() {
        Facturation facturation = new Facturation();
        PaypalFactory payment = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");
        assertDoesNotThrow(() -> facturation.generateInvoice(payment, "Juan Melo", "Laptop"));
    }

    // ─── Notification ────────────────────────────────────────────────────────

    @Test
    void notification_sendConfirmationEmail_doesNotThrow() {
        Notification notification = new Notification();
        PaypalFactory payment = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");
        assertDoesNotThrow(() ->
                notification.sendConfirmationEmail("user@example.com", "Juan Melo", payment));
    }

    @Test
    void notification_sendFailureNotification_doesNotThrow() {
        Notification notification = new Notification();
        PaypalFactory payment = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");
        assertDoesNotThrow(() ->
                notification.sendFailureNotification(payment, "user@example.com"));
    }

    // ─── Product ─────────────────────────────────────────────────────────────

    @Test
    void product_getters_returnCorrectValues() {
        Product product = new Product("P001", "Laptop", 1200.0, "Electronics");
        assertEquals("P001", product.getProductId());
        assertEquals("Laptop", product.getName());
        assertEquals(1200.0, product.getPrice());
        assertEquals("Electronics", product.getCategory());
    }

    // ─── PaymentStatus ───────────────────────────────────────────────────────

    @Test
    void paymentStatus_getName_returnsCorrectLabels() {
        assertEquals("Pendiente", PaymentStatus.PENDING.getName());
        assertEquals("Procesando", PaymentStatus.PROCESSING.getName());
        assertEquals("Completado", PaymentStatus.COMPLETED.getName());
        assertEquals("Fallido", PaymentStatus.FAILED.getName());
        assertEquals("Cancelado", PaymentStatus.CANCELED.getName());
    }

    // ─── ECIPayment ──────────────────────────────────────────────────────────

    @Test
    void eCIPayment_addAndRemoveObserver_doesNotThrow() {
        ECIPayment eCIPayment = new ECIPayment();
        PaymentObserver mockObserver = mock(PaymentObserver.class);
        assertDoesNotThrow(() -> {
            eCIPayment.addObserver(mockObserver);
            eCIPayment.removeObserver(mockObserver);
        });
    }

    @Test
    void eCIPayment_processPayment_paypalSuccess_notifiesObserver() {
        ECIPayment eCIPayment = new ECIPayment();
        PaymentObserver mockObserver = mock(PaymentObserver.class);
        eCIPayment.addObserver(mockObserver);

        PaypalFactory factory = new PaypalFactory(50.0, "C001", "Test",
                "user@example.com", "validtoken12345");

        boolean result = eCIPayment.processPayment(factory, 50.0, "C001",
                "Test purchase", "Juan Melo", "juan@example.com", "BOOK001");

        assertTrue(result);
        verify(mockObserver).onPaymentSuccess(
                any(PaymentMethod.class), eq("Juan Melo"), eq("juan@example.com"), eq("BOOK001"));
    }

    @Test
    void eCIPayment_processPayment_invalidPaypal_notifiesFailure() {
        ECIPayment eCIPayment = new ECIPayment();
        PaymentObserver mockObserver = mock(PaymentObserver.class);
        eCIPayment.addObserver(mockObserver);

        PaypalFactory factory = new PaypalFactory(50.0, "C001", "Test",
                "invalidemail", "short");

        boolean result = eCIPayment.processPayment(factory, 50.0, "C001",
                "Test purchase", "Juan Melo", "juan@example.com", "BOOK001");

        assertFalse(result);
        verify(mockObserver).onPaymentFailed(any(PaymentMethod.class), eq("juan@example.com"));
    }

    // ─── PaymentEventObserver ────────────────────────────────────────────────

    @Test
    void paymentEventObserver_onPaymentSuccess_doesNotThrow() {
        Inventory inventory = new Inventory();
        Facturation facturation = new Facturation();
        Notification notification = new Notification();
        PaymentEventObserver observer = new PaymentEventObserver(inventory, facturation, notification);

        PaypalFactory payment = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");

        assertDoesNotThrow(() ->
                observer.onPaymentSuccess(payment, "Juan Melo", "juan@example.com", "LAPTOP001"));
    }

    @Test
    void paymentEventObserver_onPaymentFailed_doesNotThrow() {
        Inventory inventory = new Inventory();
        Facturation facturation = new Facturation();
        Notification notification = new Notification();
        PaymentEventObserver observer = new PaymentEventObserver(inventory, facturation, notification);

        PaypalFactory payment = new PaypalFactory(100.0, "C001", "Test",
                "user@example.com", "validtoken12345");

        assertDoesNotThrow(() ->
                observer.onPaymentFailed(payment, "juan@example.com"));
    }
}
