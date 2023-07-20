package com.sat.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomResponseLoggingFilter implements Filter {

    private static final String NEW_LINE = System.getProperty("line.separator");

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        System.out.println(NEW_LINE + "<<<<<<<< 응답 <<<<<<<<");
        return response;
    }
}
