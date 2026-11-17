package com.lev2.common.exception.handler;

import com.lev2.common.dto.ApiResponse;
import com.lev2.common.exception.InvalidOperationException;
import com.lev2.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
   private ResponseEntity<ApiResponse<Void>> getResponseEntity(ApiResponse<Void> apiResponse){
       return ResponseEntity.status(apiResponse.status()).body(apiResponse);
   }
    private ResponseEntity<ApiResponse<Void>> getResponseEntity(HttpStatus status , String message){
       ApiResponse<Void> apiResponse = ApiResponse.error(status , message);
       return getResponseEntity(apiResponse);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exc){
        return getResponseEntity(HttpStatus.BAD_REQUEST , exc.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleException(InvalidOperationException exc){
        return getResponseEntity(HttpStatus.CONFLICT , exc.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException exc){
        return getResponseEntity(HttpStatus.NOT_FOUND , exc.getMessage());
    }


}
