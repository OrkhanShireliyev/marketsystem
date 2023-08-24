package com.example.marketsystempro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ResponseDto {
    private Long id;

    private String statusString;
    private int statusInt;
    private String errorMessage;
    private String successfullyMessage;
    private Object object;

    public ResponseDto(int statusInt, Object object) {
        this.statusInt = statusInt;
        this.object = object;
    }

    public ResponseDto(int statusInt, String errorMessage, Object object) {
        this.statusInt = statusInt;
        this.errorMessage = errorMessage;
        this.object = object;
    }

    public ResponseDto(String successfullyMessage, int statusInt, Object object) {
        this.successfullyMessage = successfullyMessage;
        this.statusInt = statusInt;
        this.object = object;
    }


    public static ResponseDto of(int status, Object object) {
        return new ResponseDto(status, object);
    }

    public static ResponseDto of(int status, String errorMessage, Object object) {
        return new ResponseDto(status, errorMessage, object);
    }

    public static ResponseDto of(String successfullyMessage, int status, Object object) {
        return new ResponseDto(successfullyMessage, status, object);
    }

}
