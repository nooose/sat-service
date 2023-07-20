package com.sat.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomRequestLoggingFilter implements Filter {
    private static final String NEW_LINE = System.getProperty("line.separator");

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        System.out.println(NEW_LINE + ">>>>>>>> 요청 >>>>>>>>");
        return ctx.next(requestSpec, responseSpec);
    }
}
