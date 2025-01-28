package com.shayan.ShayanTest.controller;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> extends RepresentationModel<ApiResponse<T>>{
    private T data;
    private String message;
}
