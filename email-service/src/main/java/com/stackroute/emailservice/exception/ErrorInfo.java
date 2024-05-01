package com.stackroute.emailservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {

    private String errorMessage;
    private String httpStatus;
    private LocalDateTime localDateTime;

}
