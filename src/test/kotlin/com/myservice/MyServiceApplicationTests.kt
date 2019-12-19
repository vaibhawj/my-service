package com.myservice

import com.myservice.controller.dto.Person
import com.myservice.dal.PersonRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.*
import com.myservice.dal.dto.Person as PersonDB


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [MyServiceApplication::class])
@AutoConfigureWebTestClient
class MyServiceApplicationTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockkBean
    private lateinit var personRepository: PersonRepository

    @Test
    fun `post person`() {
        val id = "c89429aa-10be-11ea-8d71-362b9e155667"
        every { personRepository.save(any<PersonDB>()) }.returns(PersonDB(UUID.fromString(id)).toMono())

        webTestClient.post().uri("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(Person(firstName = "John", lastName = "Doe")), Person::class.java)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody().json("""
                    {
                        "id": $id
                    }
                """.trimIndent())
    }

    @Test
    fun `post person fails`() {
        every { personRepository.save(any<PersonDB>()) }.throws(RuntimeException("Server error"))

        webTestClient.post().uri("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(Person(firstName = "John", lastName = "Doe")), Person::class.java)
                .exchange()
                .expectStatus()
                .is5xxServerError
                .expectBody().json("""
                    {
                        "reason": "Unexpected error"
                    }
                """.trimIndent())
    }

    @Test
    fun `find a person successfully`() {
        val id = "c89429aa-10be-11ea-8d71-362b9e155667"
        every { personRepository.findById(UUID.fromString(id)) }.returns(PersonDB(UUID.fromString(id), "John", "Doe").toMono())

        webTestClient.get().uri("/persons/$id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody().json("""
                    {
                        "id": $id,
                        "firstName": "John",
                        "lastName": "Doe"
                    }
                """.trimIndent())
    }

    @Test
    fun `find a person fails`() {
        val id = "c89429aa-10be-11ea-8d71-362b9e155667"
        every { personRepository.findById(UUID.fromString(id)) }.returns(Mono.empty())

        webTestClient.get().uri("/persons/$id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound
                .expectBody().json("""
                    {
                        "reason": "User not found"
                    }
                """.trimIndent())
    }
}
