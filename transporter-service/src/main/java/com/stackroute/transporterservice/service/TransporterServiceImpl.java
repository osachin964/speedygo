package com.stackroute.transporterservice.service;

import com.stackroute.transporterservice.config.LoggerConfig;
import com.stackroute.transporterservice.dto.DriverDto;
import com.stackroute.transporterservice.dto.TransporterConsumerDto;
import com.stackroute.transporterservice.dto.TransporterDto;
import com.stackroute.transporterservice.entity.Driver;
import com.stackroute.transporterservice.entity.Transporter;
import com.stackroute.transporterservice.exception.DriverAlreadyExist;
import com.stackroute.transporterservice.exception.DriverNotFound;
import com.stackroute.transporterservice.exception.TransporterAlreadyExist;
import com.stackroute.transporterservice.exception.TransporterNotFound;
import com.stackroute.transporterservice.repository.DriverRepository;
import com.stackroute.transporterservice.repository.TransporterRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TransporterServiceImpl implements TransporterService{

    private Logger logger = LoggerConfig.getLogger(TransporterServiceImpl.class);

    @Autowired
   private TransporterRepository transporterRepository;

    @Autowired
    private DriverRepository driverRepository;


    TransporterConsumerDto transporterConsumerDto1 = new TransporterConsumerDto();


//    @RabbitListener(queues = AppConstants.TRANSPORTER_QUEUE)
//    public void getUserData(TransporterConsumerDto transporterConsumerDto) {
//        try {
//            System.out.println("Message received : " + transporterConsumerDto);
//            transporterConsumerDto1 = transporterConsumerDto;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    /*
	 * @Description : this method is used to create transporter
	 * @Param : It takes TransporterDto type as parameter
	 *@returns : It returns transporterDto object
	 */

    @Override
    public TransporterDto createTransporter(TransporterDto transporterDto) throws TransporterAlreadyExist, DriverAlreadyExist {

        String methodName = "createTransporter()";
        logger.info(methodName + " called");

//        transporterDto.setTransporterEmail(transporterConsumerDto1.getEmailId());
//        transporterDto.setUserName(transporterConsumerDto1.getUserName());

//        if(transporterRepository.findByTransporterEmail(transporterDto.getTransporterEmail()).isPresent()) {
//            throw new TransporterAlreadyExist();
//        }else{
//            List<DriverDto>  driverList = transporterDto.getDrivers();
//            for(DriverDto driver : driverList)
//            {
//                if(driverRepository.findByDriverId(driver.getDriverId()).isPresent())
//                {
//                    throw new DriverAlreadyExist();
//                }else {
//                    driverRepository.save(driverDtoToEntity(driver));
//                }
//            }

            Transporter transporter = transporterRepository.save(transporterDtoToEntity(transporterDto));
            return transporterEntityToDto(transporter);
        }




    /*
     * @Description : this method is used to create transporter
     * @Param : It takes emailID type as parameter
     * @returns : It returns transporterDto object
     */

    @Override
    public TransporterDto getTransporterByEmail(String transporterEmail) throws TransporterNotFound {

        String methodName = "getTransporterByEmail()";
        logger.info(methodName + " called");

        if(transporterRepository.findByTransporterEmail(transporterEmail).isPresent()) {
            Transporter transporter = transporterRepository.findByTransporterEmail(transporterEmail).get();
            return transporterEntityToDto(transporter);
        }else
        {
            throw new TransporterNotFound();
        }
    }

    /*
     * @Description : this method is used to getAllTransporter
     * @returns : It returns list of transporterDto objects
     */

    @Override
    public List<Transporter> getAllTransporter() {

        String methodName = "getAllTransporter()";
        logger.info(methodName + " called");

       // List<Transporter> transporterDtoList = new ArrayList<>();

        List<Transporter> transporters = transporterRepository.findAll();
        try {
            for (Transporter transporter : transporters) {
                String uri = "http://localhost:8080/reviewService/getRating/" + transporter.getTransporterEmail();
                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForObject(uri, String.class);
                transporter.setRating(Double.parseDouble(result));
            }
        } catch (Exception e) {
            logger.error("Error calling review service", e);
        }
        return transporters;
    }

    /*
     * @Description : this method is used to update transporter
     * @Param : It takes transporter emailID and transporterDTo type as parameter
     * @returns : It returns transporterDto object
     */

    @Override
    public TransporterDto updateTransporter(TransporterDto transporterDto, String transporterEmail) throws TransporterNotFound{

        String methodName = "updateTransporter()";
        logger.info(methodName + " called");




        if(transporterRepository.findByTransporterEmail(transporterEmail).isPresent()) {
            Transporter transporter = this.transporterRepository.findByTransporterEmail(transporterEmail).get();

            transporter.setTransporterEmail(transporterDto.getTransporterEmail());
            transporter.setUserName(transporterDto.getUserName());
            transporter.setCompanyName(transporterDto.getCompanyName());
            transporter.setPackagingService(transporterDto.getPackagingService());
            transporter.setOfficeAddress(transporterDto.getOfficeAddress());
            transporter.setCity(transporterDto.getCity());
            transporter.setGstNo(transporterDto.getGstNo());
            transporter.setExperience(transporterDto.getExperience());
            transporter.setDrivers(transporterDto.getDrivers());
            transporter.setPricePerKm(transporterDto.getPricePerKm());

            Transporter updatedTransporter = this.transporterRepository.save(transporter);
            TransporterDto transporterDto1 = this.transporterEntityToDto(updatedTransporter);

            List<DriverDto>  driverList = transporterDto.getDrivers();
            for(DriverDto driver : driverList)
            {
                driverRepository.save(driverDtoToEntity(driver));

            }

            return transporterDto1;
        }else {
            throw new TransporterNotFound();
        }
    }

    /*
     * @Description : this method is used to delete transporter
     * @Param : It takes transporter emailID type as parameter
     * @returns : It returns message
     */

    @Override
    public String deleteTransporter(String transporterEmail) {

        String methodName = "deleteTransporter()";
        logger.info(methodName + " called");

         Transporter transporter = transporterRepository.findByTransporterEmail(transporterEmail).orElseThrow(()->new TransporterNotFound());


        List<DriverDto>  driverList = transporter.getDrivers();
        for(DriverDto driver : driverList)
        {
            if(driverRepository.findByDriverId(driver.getDriverId()).isPresent())
            {
                driverRepository.deleteById(driver.getDriverId());
            }
        }
        transporterRepository.delete(transporter);

        return "Transporter Deleted Successfully";

    }

    /*
     * @Description : this method is used to create driver
     * @Param : It takes driverDto type as parameter
     * @returns : It returns message driverDto
     */


    @Override
    public DriverDto createDriver(DriverDto driverDto) throws DriverAlreadyExist{

        String methodName = "createDriver()";
        logger.info(methodName + " called");

        if(driverRepository.findByDriverId(driverDto.getDriverId()).isPresent()){
            throw new DriverAlreadyExist();
        }else
        {
            String transporterEmail = driverDto.getTransporterEmail();
            Transporter transporter = transporterRepository.findByTransporterEmail(transporterEmail).get();

            List<DriverDto> driverDtoList = transporter.getDrivers();
            driverDtoList.add(driverDto);
            transporter.setDrivers(driverDtoList);

            updateTransporter(transporterEntityToDto(transporter), transporter.getTransporterEmail());

            Driver driver = driverRepository.save(driverDtoToEntity(driverDto));

            return driverEntityToDto(driver);

        }


    }

    /*
     * @Description : this method is used to update driver
     * @Param : It takes driverDto and driverId type as parameter
     * @returns : It returns message driverDto
     */

    @Override
    public DriverDto updateDriver(DriverDto driverDto, String driverId) throws TransporterNotFound{

        String methodName = "updateDriver()";
        logger.info(methodName + " called");


        if(transporterRepository.findByTransporterEmail(driverDto.getTransporterEmail()).isPresent())
        {
                Driver driver = this.driverRepository.findByDriverId(driverId).get();
                driver.setDriverId(driverDto.getDriverId());
                driver.setDriverName(driverDto.getDriverName());
                driver.setDriverMobileNo(driverDto.getDriverMobileNo());

                Driver updatedDriver = this.driverRepository.save(driver);
                DriverDto driverDto1 = this.driverEntityToDto(updatedDriver);

            Transporter transporter = transporterRepository.findByTransporterEmail(driverDto.getTransporterEmail()).get();

            List<DriverDto> driverDtoList = transporter.getDrivers();
            driverDtoList.add(driverDto);
            transporter.setDrivers(driverDtoList);

            updateTransporter(transporterEntityToDto(transporter), transporter.getTransporterEmail());


            return driverDto1;

        }else {
            throw new TransporterNotFound();
        }
    }

    /*
     * @Description : this method is used to delete driver
     * @Param : It takes driverId type as parameter
     * @returns : It returns message success message
     */

    @Override
    public String deleteDriver(String driverId){

        String methodName = "deleteDriver()";
        logger.info(methodName + " called");


            Driver driver = driverRepository.findByDriverId(driverId).orElseThrow(()->new DriverNotFound());
            String transporterEmail = driver.getTransporterEmail();
            Transporter transporter = transporterRepository.findByTransporterEmail(transporterEmail).get();

            List<DriverDto> driverDtoList = transporter.getDrivers();
            driverDtoList.remove(driverEntityToDto(driver));
           transporter.setDrivers(driverDtoList);

            updateTransporter(transporterEntityToDto(transporter), transporter.getTransporterEmail());


            this.driverRepository.deleteById(driverId);
            return "Driver Deleted Successfully";

    }

    /*
     * @Description : this method is used to get driver by driverId
     * @Param : It takes driverId type as parameter
     * @returns : It returns driverDto
     */

    @Override
    public DriverDto getDriverById(String driverId) throws DriverNotFound {

        String methodName = "getDriverById()";
        logger.info(methodName + " called");


        if(driverRepository.findByDriverId(driverId).isPresent()) {
            Driver driver=  driverRepository.findByDriverId(driverId).get();
            return driverEntityToDto(driver);
        }else{
            throw new DriverNotFound();
        }
    }

    /*
     * @Description : this method is used to get list of all drivers
     * @returns : It returns list of driverDto
     */
    @Override
    public List<DriverDto> getAllDriver() {

        String methodName = "getAllDriver()";
        logger.info(methodName + " called");


        List<DriverDto> driverDtoList = new ArrayList<>();

        List<Driver> drivers= driverRepository.findAll();
        for(Driver driver : drivers){
            driverDtoList.add(driverEntityToDto(driver));
        }
        return driverDtoList;
    }

    @Override
    public List<TransporterDto> getTop3() {

        String methodName = "getTop3()";
        logger.info(methodName + " called");

        List<TransporterDto> transporterDtoList = new ArrayList<>();

        List<Transporter> transporters = transporterRepository.findTop3ByOrderByPricePerKmAsc();
        for(Transporter transporter : transporters){
            transporterDtoList.add(transporterEntityToDto(transporter));
        }
        return transporterDtoList;
    }

    @Override
    public DriverDto getRandomDriver(String transporterEmail) {

        Optional<Transporter> transporter = transporterRepository.findByTransporterEmail(transporterEmail);
        if(transporter.isPresent()) {
            List<DriverDto> driverDtoList = transporter.get().getDrivers();
            Random random = new Random();
            DriverDto driverDto = driverDtoList.get(random.nextInt(driverDtoList.size()));
            return driverDto;
        }
        else{
            return new DriverDto();
        }
    }


    Transporter transporterDtoToEntity(TransporterDto transporterDto)
    {
        return new ModelMapper().map(transporterDto, Transporter.class);
    }


    private TransporterDto transporterEntityToDto(Transporter transporter)
    {
        return new ModelMapper().map(transporter, TransporterDto.class);
    }

    Driver driverDtoToEntity(DriverDto driverDto)
    {
        return new ModelMapper().map(driverDto, Driver.class);
    }


    private DriverDto driverEntityToDto(Driver driver)
    {
        return new ModelMapper().map(driver, DriverDto.class);
    }


}
