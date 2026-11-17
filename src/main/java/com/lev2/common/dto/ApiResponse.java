package com.lev2.common.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(int status, String message , T data ) {

    public static <T> ApiResponse success(T data){
        return new ApiResponse(HttpStatus.OK.value(), "Success" , data);
    }
    public static <T> ApiResponse success(String message){
        return new ApiResponse(HttpStatus.OK.value() , message ,null);
    }
    public static <T> ApiResponse noContent(){
        return new ApiResponse(HttpStatus.NO_CONTENT.value() , null ,null);
    }
    public static <T> ApiResponse created(String message , T data){
        return new ApiResponse(HttpStatus.CREATED.value() , message ,data);
    }

    public static <T> ApiResponse error(HttpStatus status , String message){
        return new ApiResponse(status.value() , message ,null);
    }


}
