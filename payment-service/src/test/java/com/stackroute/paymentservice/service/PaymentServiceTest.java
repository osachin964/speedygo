package com.stackroute.paymentservice.service;
import com.razorpay.*;
import com.stackroute.paymentservice.dto.Customer;
import com.stackroute.paymentservice.entity.Receipt;
import com.stackroute.paymentservice.repository.InvoiceRepository;
import com.stackroute.paymentservice.repository.ReceiptRepository;
import com.stackroute.paymentservice.util.Signature;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import java.security.SignatureException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private ReceiptRepository receiptRepository;
    @Mock
    private Signature sign;

    public PaymentServiceTest() throws RazorpayException {
    }

    @Test
    public void testCreateOrder() throws RazorpayException, JSONException {
        final Customer customer = new Customer("Soham Jain", "soham20@gmail.com", "789654231", "500");
        ReflectionTestUtils.setField(paymentService, "key", "rzp_test_akArRL3ZRpmWSk");
        ReflectionTestUtils.setField(paymentService, "secret", "qP9cfY6msS7EpPesQ5Bzizml");
        final ResponseEntity<String> order = paymentService.createOrder(customer);
        assertNotNull(order);
        assertEquals(200, order.getStatusCodeValue());
        assertEquals("50000",new JSONObject(order.getBody()).getJSONObject("razorPay").get("applicationFee"));
        assertEquals("soham20@gmail.com",new JSONObject(order.getBody()).getJSONObject("razorPay").get("customerEmail"));
        assertEquals("Soham Jain",new JSONObject(order.getBody()).getJSONObject("razorPay").get("customerName"));
        assertEquals("SPEEDYGO",new JSONObject(order.getBody()).getJSONObject("razorPay").get("merchantName"));
    }
    @Test
    public void testCreateOrder_ThrowsRazorpayException() {
        final Customer customer = new Customer("Soham", "soham20@gmail.com", "789654231", "50");
        assertThrows(RazorpayException.class, () -> paymentService.createOrder(customer));
    }
    @Test
    public void testCreateReceipt() throws RazorpayException {
        Receipt mockReceipt = new Receipt();
        mockReceipt.setPaymentId("pay_L1NLoMPCyTO0FT");
        ReflectionTestUtils.setField(paymentService, "key", "rzp_test_akArRL3ZRpmWSk");
        ReflectionTestUtils.setField(paymentService, "secret", "qP9cfY6msS7EpPesQ5Bzizml");
        when(receiptRepository.save(any(Receipt.class))).thenReturn(mockReceipt);

       Receipt receipt1 = paymentService.createReciept("pay_L1NLoMPCyTO0FT");
       assertNotNull(receipt1);
       assertEquals("pay_L1NLoMPCyTO0FT",receipt1.getPaymentId());

    }
    @Test
    public void testCreateInvoice() throws RazorpayException {
        final Customer customer = new Customer("Soham Jain", "soham20@gmail.com", "789654231", "500");
        ReflectionTestUtils.setField(paymentService, "key", "rzp_test_akArRL3ZRpmWSk");
        ReflectionTestUtils.setField(paymentService, "secret", "qP9cfY6msS7EpPesQ5Bzizml");
        com.stackroute.paymentservice.entity.Invoice myinvoice =new com.stackroute.paymentservice.entity.Invoice();
        myinvoice.setCustomerName("Soham Jain");
        when(invoiceRepository.save(any(com.stackroute.paymentservice.entity.Invoice.class))).thenReturn(myinvoice);

        final ResponseEntity<?> invoice1 = paymentService.createInvoice(customer);
        assertNotNull(invoice1);

    }


    @Test
    public void testValidateSignature() throws SignatureException {
        ReflectionTestUtils.setField(paymentService, "key", "rzp_test_akArRL3ZRpmWSk");
        ReflectionTestUtils.setField(paymentService, "secret", "qP9cfY6msS7EpPesQ5Bzizml");
        when(sign.calculateRFC2104HMAC(anyString(),anyString())).thenReturn("abc");
       assertTrue(paymentService.validateSignature(anyString(),anyString(),"abc"));
    }


}
