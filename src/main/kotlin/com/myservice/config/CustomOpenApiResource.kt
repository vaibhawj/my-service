package com.myservice.config

import org.springdoc.api.OpenApiCustomiser
import org.springdoc.api.OpenApiResource
import org.springdoc.core.AbstractResponseBuilder
import org.springdoc.core.OpenAPIBuilder
import org.springdoc.core.OperationBuilder
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping
import java.util.*

@RestController
class CustomOpenApiResource(openAPIBuilder: OpenAPIBuilder?, requestBuilder: CustomRequestBuilder?, responseBuilder: AbstractResponseBuilder?, operationParser: OperationBuilder?, requestMappingHandlerMapping: RequestMappingInfoHandlerMapping?, openApiCustomisers: Optional<MutableList<OpenApiCustomiser>>?) : OpenApiResource(openAPIBuilder, requestBuilder, responseBuilder, operationParser, requestMappingHandlerMapping, openApiCustomisers)