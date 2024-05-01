package com.stackroute.paymentservice.service;

import com.google.gson.Gson;
import com.razorpay.*;
import com.stackroute.paymentservice.dto.Customer;
import com.stackroute.paymentservice.dto.RazorPay;
import com.stackroute.paymentservice.dto.Response;
import com.stackroute.paymentservice.entity.Receipt;
import com.stackroute.paymentservice.repository.InvoiceRepository;
import com.stackroute.paymentservice.repository.ReceiptRepository;
import com.stackroute.paymentservice.util.Signature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SignatureException;
import java.util.*;

@SuppressWarnings("PMD")
@Service
public class PaymentService {
    
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    Signature sign;



    @Value("${razorpay.key}")
    private String key;
    @Value("${razorpay.secret}")
    private String secret;


    RazorpayClient client= new RazorpayClient(key,secret);


    private static Gson gson = new Gson();

    public PaymentService() throws RazorpayException {
    }

    public ResponseEntity<String> createOrder(Customer customer) throws RazorpayException {
        Order order = createRazorPayOrder(customer.getAmount());
        RazorPay razorPay = getRazorPay((String) order.get("id"), customer);
        return new ResponseEntity<String>(gson.toJson(getResponse(razorPay, 200)),
                HttpStatus.OK);
    }

    private Response getResponse(RazorPay razorPay, int statusCode) {
        Response response = new Response();
        response.setStatusCode(statusCode);
        response.setRazorPay(razorPay);
        return response;
    }

    private RazorPay getRazorPay(String orderId, Customer customer) {
        RazorPay razorPay = new RazorPay();
        razorPay.setApplicationFee(convertRupeeToPaise(customer.getAmount()));
        razorPay.setCustomerName(customer.getCustomerName());
        razorPay.setCustomerEmail(customer.getEmail());
        razorPay.setCustomerContact(customer.getPhoneNo());
        razorPay.setMerchantName("SPEEDYGO");
        razorPay.setPurchaseDescription("TEST PAYMENT");
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(key);
        razorPay.setImageURL("");
        razorPay.setTheme("#F37254");
        razorPay.setNotes("notes" + orderId);

        return razorPay;
    }

    private Order createRazorPayOrder(String amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", convertRupeeToPaise(amount));
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1);
        client = new RazorpayClient(key, secret);
        Order order =client.Orders.create(options);
        return order;

    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();

    }

    public ResponseEntity<?> createInvoice(Customer customer) throws RazorpayException {

        JSONObject invoiceRequest = new JSONObject();
        invoiceRequest.put("type", "invoice");
        invoiceRequest.put("description", "TEST INVOICE");

        JSONObject customerReq = new JSONObject();
        customerReq.put("name",customer.getCustomerName());
        customerReq.put("contact",customer.getPhoneNo());
        customerReq.put("email",customer.getEmail());
        invoiceRequest.put("customer",customerReq);
        invoiceRequest.put("currency","INR");

        List<Object> lines = new ArrayList<>();
        JSONObject lineItems = new JSONObject();
        lineItems.put("name","Room Shifting");
        lineItems.put("description","From Noida to Bengaluru");
        lineItems.put("amount",500000);
        lineItems.put("currency","INR");
        lineItems.put("quantity",1);
        lines.add(lineItems);
        invoiceRequest.put("line_items",new JSONArray(lines));

       client = new RazorpayClient(key, secret);

        Invoice invoice = client.Invoices.create(invoiceRequest);
        com.stackroute.paymentservice.entity.Invoice myInvoice = new com.stackroute.paymentservice.entity.Invoice();
        myInvoice.setCustomerName(customer.getCustomerName());
        myInvoice.setCurrency("INR");
        myInvoice.setAmount(customer.getAmount());
        myInvoice.setDescription(invoice.get("description"));
        myInvoice.setStatus(invoice.get("status"));
        myInvoice.setCustomerContact(customer.getPhoneNo());
        invoiceRepository.save(myInvoice);

        return new ResponseEntity<>(myInvoice,HttpStatus.OK);

    }

    public Receipt createReciept(String paymentId) throws RazorpayException {
        client = new RazorpayClient(key, secret);
        Payment payment = client.Payments.fetch(paymentId);
        Receipt receipt = new Receipt();
        receipt.setPaymentId(payment.get("id"));
        int price = payment.get("amount");
        receipt.setAmount(price/100);
        receipt.setCurrency(payment.get("currency"));
        receipt.setPaymentStatus(payment.get("status"));
        receipt.setMethod(payment.get("method"));
        receipt.setOrderId(payment.get("order_id"));
        receipt.setCaptured(payment.get("captured").toString());
        return receiptRepository.save(receipt);

    }
    public Boolean validateSignature(String paymentId, String orderId, String signature) throws SignatureException {
         String generated_signature = sign.calculateRFC2104HMAC(orderId + "|" + paymentId, secret);

        if (generated_signature == signature) {
            System.out.println("Payment is successful");
            return true;
        }
        else {
            return false;
        }
    }
}
