package com.stackroute.transporterservice.util;

import com.stackroute.transporterservice.dto.DriverDto;
import com.stackroute.transporterservice.dto.TransporterDto;
import com.stackroute.transporterservice.entity.Transporter;
import com.stackroute.transporterservice.repository.DriverRepository;
import com.stackroute.transporterservice.repository.TransporterRepository;
import com.stackroute.transporterservice.service.TransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataFeeder implements CommandLineRunner {
    @Autowired
    TransporterRepository transporterRepository;

    @Autowired
    TransporterService transporterService;
    @Autowired
    private DriverRepository driverRepository;

    @Override
    public void run(String... args) throws Exception {
        driverRepository.deleteAll();
        transporterRepository.deleteAll();
        List<String> service = new ArrayList<>();
        service.add("Car shift");
        service.add("Room shift");

        List<DriverDto> transporter1 = new ArrayList<>();
        DriverDto driver1 = new DriverDto("DR999","Ramu Singh","6876567890","expressway@gmail.com");
        DriverDto driver2 = new DriverDto("DR888","Monu Singh","8976543456","expressway@gmail.com");
        transporter1.add(driver1);
        transporter1.add(driver2);

        List<DriverDto> transporter2 = new ArrayList<>();
        DriverDto driver3 = new DriverDto("DR768","Dobu Rao","9996567890","quickliners@gmail.com");
        DriverDto driver4 = new DriverDto("DR764","Raju Babu","9896543456","quickliners@gmail.com");
        transporter2.add(driver3);
        transporter2.add(driver4);

        List<DriverDto> transporter3 = new ArrayList<>();
        DriverDto driver5 = new DriverDto("DR345","Amog Rana","7896567890","localmovers@gmail.com");
        DriverDto driver6 = new DriverDto("DR789","Anuj Rana","8886543456","localmovers@gmail.com");
        transporter3.add(driver5);
        transporter3.add(driver6);

        List<DriverDto> transporter4 = new ArrayList<>();
        DriverDto driver7 = new DriverDto("DR345","Ganesh Prasad","7865432345","shiftkaro@gmail.com");
        DriverDto driver8 = new DriverDto("DR787","Suraj Prasad","8989543456","shiftkaro@gmail.com");
        transporter4.add(driver7);
        transporter4.add(driver8);

        List<DriverDto> transporter5 = new ArrayList<>();
        DriverDto driver9 = new DriverDto("DR999","Ramu Lal","6876567890","bluedart@gmail.com");
        DriverDto driver10 = new DriverDto("DR888","Maan Singh","8976543456","bluedart@gmail.com");
        transporter5.add(driver9);
        transporter5.add(driver10);

        List<DriverDto> transporter6 = new ArrayList<>();
        DriverDto driver11 = new DriverDto("DR999","Ramu Singh","6876567890","ramtransporter@gmail.com");
        DriverDto driver12 = new DriverDto("DR888","Monu Singh","8976543456","ramtransporter@gmail.com");
        transporter6.add(driver11);
        transporter6.add(driver12);

        List<DriverDto> transporter7 = new ArrayList<>();
        DriverDto driver13= new DriverDto("DR999","Bholu Singh","6876567890","jmd@gmail.com");
        DriverDto driver14 = new DriverDto("DR888","Monu Singh","8976543456","jmd@gmail.com");
        transporter7.add(driver13);
        transporter7.add(driver14);

        List<DriverDto> transporter8 = new ArrayList<>();
        DriverDto driver15= new DriverDto("DR999","Motu Singh","6876567890","quickmovers@gmail.com");
        DriverDto driver16 = new DriverDto("DR888","Shyam Singh","8976543456","quickmovers@gmail.com");
        transporter8.add(driver15);
        transporter8.add(driver16);

        List<DriverDto> transporter9 = new ArrayList<>();
        DriverDto driver17= new DriverDto("DR999","Shri Singh","6876567890","gltransporters@gmail.com");
        DriverDto driver18 = new DriverDto("DR888","Monu Singh","8976543456","gltransporters@gmail.com");
        transporter9.add(driver17);
        transporter9.add(driver18);






        transporterRepository.save(Transporter.builder().transporterEmail("bluedart@gmail.com").companyName("Blue Dart").experience(10).pricePerKm(130).officeAddress("Balaji Layout, Nallurhalli").serviceType(service).drivers(transporter5).gstNo("GST2212").packagingService(true).build());
        transporterRepository.save(Transporter.builder().transporterEmail("quickliners@gmail.com").companyName("Quick Liners").experience(13).pricePerKm(110).officeAddress("Kalpana Nagar, Bangalore").serviceType(service).drivers(transporter2).gstNo("GST2312").packagingService(true).build());
        transporterRepository.save(Transporter.builder().transporterEmail("localmovers@gmail.com").companyName("Local Movers").experience(16).pricePerKm(100).officeAddress("MP Nagar, Bangalore").serviceType(service).drivers(transporter3).gstNo("GST5613").packagingService(true).build());
        transporterRepository.save(Transporter.builder().transporterEmail("shiftkaro@gmail.com").companyName("Shift Karo").experience(12).pricePerKm(190).officeAddress("Narayan Nagar, Kundanhalli").serviceType(service).drivers(transporter4).gstNo("GST1212").packagingService(true).build());
        transporterRepository.save(Transporter.builder().transporterEmail("ramtransporter@gmail.com").companyName("Ram Transporters").experience(7).pricePerKm(120).officeAddress("JP Nagar, Bangalore").serviceType(service).drivers(transporter6).gstNo("GST7202").packagingService(true).build());
        transporterRepository.save(Transporter.builder().transporterEmail("jmd@gmail.com").companyName("JMD Couriers").experience(15).pricePerKm(60).officeAddress("Shyama Nagar, Chinnahalli").serviceType(service).drivers(transporter7).gstNo("GST8253").packagingService(true).build());
        transporterRepository.save(Transporter.builder().transporterEmail("quickmovers@gmail.com").companyName("Quick Movers").experience(14).pricePerKm(120).officeAddress("Sai Layout, Bangalore").serviceType(service).drivers(transporter8).gstNo("GST2008").packagingService(true).build());
        transporterRepository.save(Transporter.builder().transporterEmail("gltransporters@gmail.com").companyName("GL Transporters").experience(13).pricePerKm(80).officeAddress("Bellandur, Bangalore").serviceType(service).drivers(transporter9).gstNo("GST1965").packagingService(true).build());
//        transporterRepository.save(Transporter.builder().transporterEmail("expressway@gmail.com").companyName("Express Way").experience(20).pricePerKm(70).officeAddress("Airport road, Bangalore").serviceType(service).drivers(transporter1).gstNo("GST2085").packagingService(true).build());
//        transporterRepository.save(Transporter.builder().transporterEmail("quickmovers@gmail.com").companyName("Quick movers").experience(1).pricePerKm(21).officeAddress("Nallurahalli").serviceType(service).build());
        TransporterDto transporterDto=new TransporterDto("expressway@gmail.com","Express","Express Way",true,"Airport road, Bangalore","Bangalore",service,"GST2085",20,200,transporter1);
        transporterService.updateTransporter(transporterDto,"expressway@gmail.com");
    }
}