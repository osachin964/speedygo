package com.stackroute.transporterservice.service;

import com.stackroute.transporterservice.dto.DriverDto;
import com.stackroute.transporterservice.dto.TransporterDto;
import com.stackroute.transporterservice.entity.Transporter;
import com.stackroute.transporterservice.repository.DriverRepository;
import com.stackroute.transporterservice.repository.TransporterRepository;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransporterServiceImplTest {

    @Mock
    private TransporterRepository transporterRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private TransporterServiceImpl transporterService;

    private TransporterDto transporterDto, transporterDto1;

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
                .gstNo("GST740")
                .experience(12.0)
                .drivers(driversList).build();

        driverDto = DriverDto.builder()
                .driverId("DR567")
                .driverName("Manoj Shiva")
                .driverMobileNo("89089896754")
                .transporterEmail("SanjaySharma@gmail.com").build();

    }

    @DisplayName("JUnit test for saveTransporter method")
    @Test
    public void createTransporter() throws Exception{


        List<DriverDto> driversList1 = new ArrayList<>();

        driversList1.add(new DriverDto("DR500", "Kapil Shiva", "7708989675", "ManjuSharma@gmail.com"));

        transporterDto1 = TransporterDto.builder()
                .companyName("Mohan Transportation")
                .packagingService(true)
                .officeAddress("GK Shine Road, Nagpur")
                .city("Nagpur")
                .serviceType(Collections.singletonList("\"Home Shifting\", \"Office Shifting\", \"Car Shifting"))
                .gstNo("GST040")
                .experience(11.0)
                .drivers(driversList1).build();



        Transporter entity = transporterService.transporterDtoToEntity(transporterDto1);
        given(transporterRepository.save(entity)).willReturn(entity);


        TransporterDto savedTransporter = transporterService.createTransporter(transporterDto1);

        assertThat(savedTransporter).isNotNull();
   }

    @DisplayName("JUnit test for getTransporterByEmail method")
    @Test
    public void getTransporterByEmail() throws Exception{

        given(transporterRepository.findByTransporterEmail("SanjaySharma@gmail.com")).willReturn(Optional.ofNullable(transporterService.transporterDtoToEntity(transporterDto)));

        TransporterDto savedTransporter = transporterService.getTransporterByEmail(transporterDto.getTransporterEmail());

        assertThat(savedTransporter).isNotNull();

    }

    @DisplayName("JUnit test for getAllTransporter method")
    @Test
    void getAllTransporter() {

        List<DriverDto> driversList1 = new ArrayList<>();

        driversList1.add(new DriverDto("DR500", "Kapil Shiva", "7708989675", "ManjuSharma@gmail.com"));

        transporterDto1 = TransporterDto.builder()
                .transporterEmail("ManjuSharma@gmail.com")
                .userName("Manju Sharma")
                .companyName("Mohan Transportation")
                .packagingService(true)
                .officeAddress("GK Shine Road, Nagpur")
                .city("Nagpur")
                .serviceType(Collections.singletonList("\"Home Shifting\", \"Office Shifting\", \"Car Shifting"))
                .gstNo("GST040")
                .experience(11.0)
                .drivers(driversList1).build();

        List<Transporter> list = new ArrayList<>();
        list.add(transporterService.transporterDtoToEntity(transporterDto));
        list.add(transporterService.transporterDtoToEntity(transporterDto1));

        given(transporterRepository.findAll()).willReturn(list);
        List<Transporter> transporterDtoList= transporterService.getAllTransporter();

        AssertionsForInterfaceTypes.assertThat(transporterDtoList).isNotNull();
        assertThat(transporterDtoList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for updateTransporter method")
    @Test
    void updateTransporter() {

        List<DriverDto> driversList = new ArrayList<>();

        driversList.add(new DriverDto("DR567", "Manoj Shiva", "8908989675", "SanjaySharma@gmail.com"));

        transporterDto1 = TransporterDto.builder()
                .transporterEmail("SanjaySharma@gmail.com")
                .userName("Sanjay Sharma")
                .companyName("Roshan Transportation")
                .packagingService(true)
                .officeAddress("MK Shine Road, Pune")
                .city("Pune")
                .serviceType(Collections.singletonList("\"Home Shifting\", \"Office Shifting\", \"Car Shifting"))
                .gstNo("GST740")
                .experience(12.0)
                .drivers(driversList).build();

        List<DriverDto> driversList1 = new ArrayList<>();

        driversList1.add(new DriverDto("DR567", "Manoj Shiva", "8908989675", "SanjaySharma@gmail.com"));

        Transporter transporter = Transporter.builder()
                .transporterEmail("SanjaySharma@gmail.com")
                .userName("Sanjay ")
                .companyName("Roshan Transportation")
                .packagingService(true)
                .officeAddress("MK Shine Road, Pune")
                .city("Pune")
                .serviceType(Collections.singletonList("\"Home Shifting\", \"Office Shifting\", \"Car Shifting"))
                .gstNo("GST740")
                .experience(12.0)
                .drivers(driversList1).build();


        given(transporterRepository.findByTransporterEmail("SanjaySharma@gmail.com")).willReturn(Optional.ofNullable(transporterService.transporterDtoToEntity(transporterDto)));
        given(transporterRepository.save(any())).willReturn(transporter);
        assertEquals("SanjaySharma@gmail.com", transporterService.updateTransporter(transporterDto1, "SanjaySharma@gmail.com").getTransporterEmail());

    }


}