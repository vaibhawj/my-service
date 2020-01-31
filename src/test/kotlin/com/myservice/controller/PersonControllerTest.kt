package com.myservice.controller

import com.myservice.MyServiceApplication
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [MyServiceApplication::class])
@AutoConfigureWebTestClient
internal class PersonControllerTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun test() {
        client.get().uri("/persons/${UUID.randomUUID()}")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound
    }
}