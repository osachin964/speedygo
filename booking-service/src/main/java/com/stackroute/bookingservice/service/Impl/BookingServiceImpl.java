package com.stackroute.bookingservice.service.Impl;

import com.stackroute.bookingservice.config.SequenceGeneratorListener;
import com.stackroute.bookingservice.dto.*;
import com.stackroute.bookingservice.entity.Booking;
import com.stackroute.bookingservice.exceptions.BookingIdNotFound;
import com.stackroute.bookingservice.repository.BookingRepository;
import com.stackroute.bookingservice.service.BookingService;
import com.stackroute.bookingservice.util.AppConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    SequenceGeneratorListener listener;
    //    @Autowired
    private RestTemplate restTemplate;
    @Value("${map.url}")
    String mapsUrl;

    public ResponseEntity<String> createBooking(BookingDto bookingDto) {

        String uri = "http://localhost:8080/paymentService/createPayment";
        RestTemplate restTemplate = new RestTemplate();
      Double totalPrice = getTotalPriceforTransporter(bookingDto.getDropLocation(), bookingDto.getPickupLocation(), bookingDto.getTransporterId());
        String result = restTemplate.postForObject(uri, Map.ofEntries(
                Map.entry("customerName", bookingDto.getCustomerName()),
                Map.entry("amount", totalPrice),
                Map.entry("phoneNumber", bookingDto.getCustomerMobileNo()),
                Map.entry("email", bookingDto.getCustomerId())
        ), String.class);
        JSONObject json = new JSONObject(result);
        Booking booking = bookingDtoToEntity(bookingDto);
        booking.setBookingId(listener.generateSequence(Booking.SEQUENCE_NAME));
        booking.setStatus("Created");
        json.put("bookingId",booking.getBookingId());
        booking.setTotalPrice(totalPrice);
        DriverDto driver = getDriver(booking.getTransporterId());

        //Sending queue to email service
        EmailDto emailDto = new EmailDto();
        emailDto.setBookingId(booking.getBookingId());
        emailDto.setCustomerId(booking.getCustomerId());
        emailDto.setCustomerName(booking.getCustomerName());
        emailDto.setPickupLocation(booking.getPickupAddress()+", "+booking.getPickupLocation());
        emailDto.setDropLocation(booking.getDropAddress()+", "+booking.getDropLocation());
        emailDto.setTotalPrice(booking.getTotalPrice());
        emailDto.setServiceType(booking.getServiceType());
//        emailDto.setDriverName("Harish Gupta");
//        emailDto.setDriverPhone("7965432109");
        emailDto.setDriverName(driver.getDriverName());
        emailDto.setDriverPhone(driver.getDriverMobileNo());
        rabbitTemplate.convertAndSend(AppConstants.EXCHANGE, AppConstants.EMAIL_QUEUE_ROUTING_KEY, emailDto);
        //sending queue to review service
        ReviewDto reviewDto =new ReviewDto();
        reviewDto.setBookingId(booking.getBookingId());
        reviewDto.setCustomerId(booking.getCustomerId());
        reviewDto.setTransporterId(booking.getTransporterId());
        reviewDto.setCustomerName(booking.getCustomerName());
        rabbitTemplate.convertAndSend(AppConstants.EXCHANGE, AppConstants.REVIEW_QUEUE_ROUTING_KEY, reviewDto);

        bookingRepository.save(booking);
      //  return ResponseEntity.ok("ABC");
       return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    public DriverDto getDriver(String transporterId){
        String uri = "http://localhost:8090/transporterService/getRandomDriver/" + transporterId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONObject json = new JSONObject(result);
        DriverDto driverDto = new DriverDto();
        driverDto.setDriverId(json.getString("driverId"));
        driverDto.setDriverName(json.getString("driverName"));
        driverDto.setDriverMobileNo(json.getString("driverMobileNo"));
        return driverDto;
    }
    public Double getTotalPriceforTransporter(String dropLocation, String pickupLocation, String transporterId) {
        DistanceRequest dr = new DistanceRequest(Long.parseLong(dropLocation), Long.parseLong(pickupLocation));
        Double distance = calculateDistance(dr);
        Integer pricePerKm = getPricePerKmForTransporterId(transporterId);
        return distance * pricePerKm;
    }

    public Integer getPricePerKmForTransporterId(String transporterId) {
        String uri = "http://localhost:8090/transporterService/getTransporter/" + transporterId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONObject json = new JSONObject(result);
        return json.getInt("pricePerKm");
    }

    @Override
    public ResponseEntity<?> getAllTransporters() {
        String uri = "http://localhost:8080/transporterService/getAllTransporters";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return ResponseEntity.ok(result);
    }

    //    public List<Booking> getBookings(BookingSearchRequest request) {
//        if (request.getType().equalsIgnoreCase("transporter"))
//            return bookingRepository.findAllByTransporterId(request.getId());
//        else if (request.getType().equalsIgnoreCase("customer"))
//            return bookingRepository.findAllByCustomerId(request.getId());
//        else if (request.getType().equalsIgnoreCase("booking")) {
//            Optional<Booking> bookingOptional = bookingRepository.findById(request.getId());
//            if (bookingOptional.isPresent()) {
//                return List.of(bookingOptional.get());
//            } else return Collections.EMPTY_LIST;
//        } else if (request.getType().equalsIgnoreCase("all"))
//            return bookingRepository.findAll();
//        else
//            return Collections.EMPTY_LIST;
//    }
    @Override
    public List<TransporterDetails> generateQuotation(DistanceRequest request) {
        List<TransporterDetails> quoteList = new ArrayList<>();
        String uri = "http://localhost:8090/transporterService/getTop3";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONArray json = new JSONArray(result);
        for (int i = 0; i < json.length(); i++) {
            JSONObject transporter = json.getJSONObject(i);
            TransporterDetails transporterDetails = new TransporterDetails();
            transporterDetails.setTransporterEmail(transporter.getString("transporterEmail"));
            transporterDetails.setCompanyName(transporter.getString("companyName"));
            transporterDetails.setExperience(String.valueOf(transporter.getDouble("experience")));
            transporterDetails.setOfficeAddress(transporter.getString("officeAddress"));
            transporterDetails.setCalculatedPrice(calculateDistance(request) * transporter.getDouble("pricePerKm"));
            JSONArray jsonArray = transporter.getJSONArray("serviceType");
            List list = new ArrayList();
            for (int j = 0; j < jsonArray.length(); j++) {
                list.add(jsonArray.getString(j));
            }
            transporterDetails.setServiceType(list);
            quoteList.add(transporterDetails);
        }
        return quoteList;
    }

    @Override
    public Booking updateBooking(BookingDto bookingDto, String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BookingIdNotFound::new);
        booking.setCustomerId(bookingDto.getCustomerId());
        booking.setServiceType(bookingDto.getServiceType());
        booking.setCustomerName(bookingDto.getCustomerName());
        booking.setDropLocation(bookingDto.getDropLocation());
        booking.setPickupLocation(bookingDto.getPickupLocation());
        booking.setCustomerMobileNo(bookingDto.getCustomerMobileNo());
        booking.setTransporterId(bookingDto.getTransporterId());

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByCustomer(String customerId) {
        return bookingRepository.findAllByCustomerId(customerId);

    }

    @Override
    public List<Booking> getBookingByTransporter(String transporterId) {
        return bookingRepository.findAllByTransporterId(transporterId);
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(BookingIdNotFound::new);
    }

    @Override
    public Booking updateStatus(String bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BookingIdNotFound::new);
        booking.setStatus(status);

        StatusEmailDto statusEmailDto = new StatusEmailDto();
        statusEmailDto.setBookingId(booking.getBookingId());
        statusEmailDto.setStatus(booking.getStatus());
        statusEmailDto.setCustomerId(booking.getCustomerId());
        rabbitTemplate.convertAndSend(AppConstants.EXCHANGE, AppConstants.STATUS_KEY, statusEmailDto);

        return bookingRepository.save(booking);
    }

    @Override
    public String viewStatus(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BookingIdNotFound::new);
        return booking.getStatus();
    }

    @Override
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingRepository.findAll());
    }

    @Override
    public Double calculateDistance(DistanceRequest request) {
        restTemplate = new RestTemplate();

        String hereIdTo = getHereIdForPinCode(request.getTo());
        String hereIdFrom = getHereIdForPinCode(request.getFrom());

        String positionTo = getLatLongFromHereId(hereIdTo).replace("lng", "lon");
        String positionFrom = getLatLongFromHereId(hereIdFrom).replace("lng", "lon");

        return getFinalDistance(positionTo, positionFrom);

    }

    private String getHereIdForPinCode(Long pinCode) {
        String url = "https://distancecalculator.globefeed.com/here/geohe/geoauto.asp?cc=IN&a3=IND&q=" + pinCode;

        String response = restTemplate.getForObject(url, String.class);

        return new JSONArray(response).getJSONObject(0).getString("here_id");
    }

    private String getLatLongFromHereId(String hereId) {
        String url = "https://lookup.search.hereapi.com/v1/lookup?apiKey=5TS1MuGOBQu8P3241B4wp4fhzRPnFRRmC_Ueie1mu4A&id=" + hereId;

        String response = restTemplate.getForObject(url, String.class);

        return new JSONObject(response).getJSONObject("position").toString();
    }

    private Double getFinalDistance(String positionTo, String positionFrom) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.stadiamaps.com/route")
                .queryParam("api_key", "b865256e-4b0a-448c-943d-69ab7fd56f8a")
                .queryParam("json", "{\"costing\":\"auto\",\"locations\":[" + positionTo + "," + positionFrom + "]}");
        String ur = builder.toUriString();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ur))
                .header("referer", "https://distancecalculator.globefeed.com")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body()).getJSONObject("trip").getJSONObject("summary").getDouble("length");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking bookingDtoToEntity(BookingDto bookingDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(bookingDto, Booking.class);
    }

    public BookingDto bookingEntityToDto(Booking booking) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(booking, BookingDto.class);
    }

}