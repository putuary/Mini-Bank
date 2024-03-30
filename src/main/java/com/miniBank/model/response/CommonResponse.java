package com.miniBank.model.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private String message;
    private int statusCode;
    private T data;
}
