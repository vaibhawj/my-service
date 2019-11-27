package com.myservice

import com.google.gson.Gson
import com.myservice.config.Example
import org.springframework.test.web.reactive.server.EntityExchangeResult
import java.io.File
import java.io.FileWriter

fun document(exchangeResult: EntityExchangeResult<ByteArray>, tag: String, operation: String, exampleName: String) {
    val requestBodyContent: ByteArray? = exchangeResult.requestBodyContent
    val requestBody = when (requestBodyContent) {
        null -> null
        else -> String(requestBodyContent)
    }

    val responseBodyContent: ByteArray? = exchangeResult.responseBodyContent
    val responseBody = when (responseBodyContent) {
        null -> null
        else -> String(responseBodyContent)
    }
    val status = exchangeResult.status.value()
    val example = Example(exampleName, status, requestBody, responseBody)
    try {
        val dir = File("build/extras/$tag/$operation/")
        dir.mkdirs()
        val fileWriter = FileWriter("build/extras/$tag/$operation/$exampleName.json")
        fileWriter.write(Gson().toJson(example))
        fileWriter.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}