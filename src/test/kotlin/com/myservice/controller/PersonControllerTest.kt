package com.myservice.controller

import com.myservice.service.PersonService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.toMono
import reactor.test.StepVerifier
import java.util.*

internal class PersonControllerTest {

    @Test
    fun find() {
        val service = mockk<PersonService>()
        val controller = PersonController()
        controller.service = service

        val id = UUID.randomUUID()
        every { service.findPerson(id) }.returns(com.myservice.controller.dto.Person(id = id, firstName = "John").toMono())

        StepVerifier.create(controller.find(id))
                .expectNextMatches { it.body?.firstName == "John" }
                .verifyComplete()
    }
}