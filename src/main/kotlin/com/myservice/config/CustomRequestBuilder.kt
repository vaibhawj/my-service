package com.myservice.config

import com.google.gson.Gson
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
import org.springdoc.core.AbstractParameterBuilder
import org.springdoc.core.AbstractRequestBuilder
import org.springdoc.core.OperationBuilder
import org.springdoc.core.RequestBodyBuilder
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import java.io.FileReader
import java.nio.file.Files.isRegularFile
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import io.swagger.v3.oas.models.examples.Example as OpenApiExample


@Component
class CustomRequestBuilder(parameterBuilder: AbstractParameterBuilder, requestBodyBuilder: RequestBodyBuilder,
                           operationBuilder: OperationBuilder) : AbstractRequestBuilder(parameterBuilder, requestBodyBuilder, operationBuilder) {

    override fun isParamTypeToIgnore(paramType: Class<*>): Boolean {
        return false
    }

    override fun customiseOperation(operation: Operation, handlerMethod: HandlerMethod): Operation {
        val g = Gson()
        val path = Paths.get("build/extras/${operation.tags.first()}/${operation.operationId.split("_").first()}")

        if (Files.exists(path)) {
            Files.walk(path).use { paths ->
                paths
                        .filter { isRegularFile(it) }
                        .forEach {
                            try {
                                val fileReader = FileReader(it.toFile())
                                val example = g.fromJson(fileReader, Example::class.java)
                                fileReader.close()

                                populateRequestExample(example, operation)
                                populateResponseExample(operation, example)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
            }
        }
        return operation
    }

    private fun populateRequestExample(example: Example, operation: Operation) {
        operation.requestBody?.content?.clear()

        if (!example.requestBody.isNullOrBlank()) {
            operation.requestBody.content["application/json"] = MediaType().schema(Schema<Any>()).addExamples(example.exampleName, OpenApiExample().value(example.requestBody))
        }
    }

    private fun populateResponseExample(operation: Operation, example: Example) {
        operation.responses?.clear()
        val status = example.status.toString()

        if (!example.responseBody.isNullOrBlank()) {
            ApiResponse().description(status).content.clear()
            operation.responses[status] = ApiResponse().description(status).content(
                    Content().addMediaType("application/json", MediaType().schema(Schema<Any>())
                            .addExamples(example.exampleName, OpenApiExample().value(example.responseBody))))
        }

    }
}

data class Example(val exampleName: String,
                   val status: Int,
                   val requestBody: String? = null,
                   val responseBody: String? = null)