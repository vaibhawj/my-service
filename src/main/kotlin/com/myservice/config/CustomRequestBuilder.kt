package com.myservice.config

import io.swagger.v3.oas.models.Operation
import org.springdoc.core.AbstractParameterBuilder
import org.springdoc.core.AbstractRequestBuilder
import org.springdoc.core.OperationBuilder
import org.springdoc.core.RequestBodyBuilder
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod

@Component
class CustomRequestBuilder(parameterBuilder: AbstractParameterBuilder, requestBodyBuilder: RequestBodyBuilder,
                     operationBuilder: OperationBuilder) : AbstractRequestBuilder(parameterBuilder, requestBodyBuilder, operationBuilder) {

    override fun isParamTypeToIgnore(paramType: Class<*>): Boolean {
        return false
    }

    override fun customiseOperation(operation: Operation, handlerMethod: HandlerMethod): Operation {
        return operation
    }
}