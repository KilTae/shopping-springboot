package com.example.shopping.controller.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.shopping.controller.res.QProductResponse is a Querydsl Projection type for ProductResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductResponse extends ConstructorExpression<ProductResponse> {

    private static final long serialVersionUID = -1166332443L;

    public QProductResponse(com.querydsl.core.types.Expression<? extends com.example.shopping.domain.Product> product) {
        super(ProductResponse.class, new Class<?>[]{com.example.shopping.domain.Product.class}, product);
    }

}

