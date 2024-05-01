package com.stackroute.transporterservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerConfig {

  public static Logger getLogger(Class className){
      return LoggerFactory.getLogger(className);
  }
}
