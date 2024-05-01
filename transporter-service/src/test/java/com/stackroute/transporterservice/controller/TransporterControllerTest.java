package com.stackroute.transporterservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.transporterservice.dto.DriverDto;
import com.stackroute.transporterservice.dto.TransporterDto;
import com.stackroute.transporterservice.entity.Transporter;
import com.stackroute.transporterservice.service.TransporterService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransporterController.class)
class TransporterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransporterService transporterService;

    @Autowired
    private static ObjectMapper mapper = new ObjectMapper();

    private TransporterDto transporterDto;

    private DriverDto driverDto;


    @BeforeEach
    public void setup() {
        List<DriverDto> driversList = new ArrayList<>();

        driversList.add(new DriverDto("DR567", "Manoj Shiva", "8908989675", "SanjaySharma@gmail.com"));

        transporterDto = TransporterDto.builder()
                .transporterEmail("SanjaySharma@gmail.com")
                .userName("Sanjay Sharma")
                .companyName("Roshan Transportation")
                .packagingService(true)
                .officeAddress("MK Shine Road, Pune")
                .city("Pune")
                .serviceType(Collections.singletonList("\"Home Shifting\", \"Office Shifting\", \"Car Shifting"))
                .gstNo("560040")
                .experience(12.0)
                .drivers(driversList).build();

        driverDto = DriverDto.builder()
                .driverId("DR567")
                .driverName("Manoj Shiva")
                .driverMobileNo("8908989675")
                .transporterEmail("SanjaySharma@gmail.com").build();

    }

    @Test
    public void createTransporter() throws Exception{


        given(transporterService.createTransporter(any(TransporterDto.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/transporterService/saveTransporter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transporterDto)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName",
                        CoreMatchers.is(transporterDto.getUserName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transporterEmail",
                        CoreMatchers.is(transporterDto.getTransporterEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.officeAddress",
                        CoreMatchers.is(transporterDto.getOfficeAddress())));
    }


    @Test
    public void getTransporterByEmail() throws Exception{

        String transporterEmail = "SanjaySharma@gmail.com";
        given(transporterService.getTransporterByEmail(transporterEmail)).willReturn(transporterDto);

        ResultActions response = mockMvc.perform(get("/transporterService/getTransporter/{transporterEmail}", transporterEmail));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", CoreMatchers.is(transporterDto.getUserName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transporterEmail", CoreMatchers.is(transporterDto.getTransporterEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName", CoreMatchers.is(transporterDto.getCompanyName())));

    }

    @Test
    public void getAllTransporterTest() throws Exception{

        List<Transporter> transporterDtoList = new ArrayList<>();

        given(transporterService.getAllTransporter()).willReturn(transporterDtoList);

        ResultActions response = mockMvc.perform(get("/transporterService/getAllTransporters"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(transporterDtoList.size())));
    }

    @Test
    public void updateTransporter() throws Exception{

        String transporterEmail = "SanjaySharma@gmail.com";

        List<DriverDto> driversList = new ArrayList<>();

        driversList.add(new DriverDto("TY9090","Manoj Shiva","89089896754","SanjaySharma@gmail.com"));

        TransporterDto updateTransporterDto = TransporterDto.builder()
                .transporterEmail("SanjaySharma@gmail.com")
                .userName("Sanjay Sharma")
                .companyName("Roshan Transportation")
                .packagingService(true)
                .officeAddress("MK Shine Road, Pune")
                .city("Pune")
                .serviceType(Collections.singletonList("\"Home Shifting\", \"Office Shifting\", \"Car Shifting"))
                .gstNo("560040")
                .experience(12.0)
                .drivers(driversList).build();

        given(transporterService.updateTransporter(any(TransporterDto.class), anyString())).
                willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/transporterService/updateTransporter/{transporterEmail}", transporterEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transporterDto)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", CoreMatchers.is(updateTransporterDto.getUserName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transporterEmail", CoreMatchers.is(updateTransporterDto.getTransporterEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName", CoreMatchers.is(updateTransporterDto.getCompanyName())));

    }

    @Test
    public void deleteTransporter() throws Exception{

        // given - precondition or setup
        String transporterEmail = "RajaSharma@gmail.com";

        given(transporterService.deleteTransporter(transporterEmail)).willReturn(transporterEmail);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/transporterService/deleteTransporter/{transporterEmail}", transporterEmail));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createDriver() throws Exception{

        String driverId ="DR567";

        given(transporterService.createDriver(any(DriverDto.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/transporterService/saveDriver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(driverDto)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.driverId", CoreMatchers.is(driverDto.getDriverId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.driverName", CoreMatchers.is(driverDto.getDriverName())));


    }

    @Test
    public void getDriverById() throws Exception{

        String driverId = "DR567";
        given(transporterService.getDriverById(driverId)).willReturn(driverDto);

        ResultActions response = mockMvc.perform(get("/transporterService/getDriver/{driverId}", driverId));

    }

    @Test
    public void getAllDriver() throws Exception{

        List<DriverDto> driverDtoList = new ArrayList<>();

        given(transporterService.getAllDriver()).willReturn(driverDtoList);

        ResultActions response = mockMvc.perform(get("/transporterService/getAllDrivers"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(driverDtoList.size())));
    }

    @Test
    public void updateDriver() throws Exception{

        String driverId ="DR567";

        DriverDto updateDriverDto = DriverDto.builder()
                .driverId("DR567")
                .driverName("Manoj Shiva")
                .driverMobileNo("89089896754")
                .transporterEmail("SanjaySharma@gmail.com").build();

        given(transporterService.updateDriver(any(DriverDto.class), anyString())).
                willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/transporterService/updateDriver/{driverId}", driverId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(driverDto)));





    }

    @Test
    public void deleteDriver() throws Exception{

        // given - precondition or setup
        String driverId = "DR567";

        given(transporterService.deleteDriver(driverId)).willReturn(driverId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/transporterService/deleteDriver/{driverId}", driverId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}