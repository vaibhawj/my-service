package com.myservice.controller

import com.myservice.controller.dto.Person
import com.myservice.service.PersonService
import com.github.fge.jsonpatch.JsonPatch
import com.myservice.controller.dto.CreateResponse
import io.swagger.v3.oas.annotations.Operation
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/persons")
class PersonController {

    @Autowired
    lateinit var service: PersonService

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    @Operation(summary = "Find a person", description = "Finds a person with the id")
    fun find(@PathVariable id: UUID): Mono<Person> {
        return service.findPerson(id)
    }

    @GetMapping(produces = ["application/json"])
    @Operation(summary = "List persons", description = "List all the people")
    fun list(): Flux<Person> {
        return service.findAll()
    }

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @Operation(summary = "Create a person", description = "Creates a person with inputs")
    fun create(@RequestBody personInput:  Mono<Person>): Mono<CreateResponse> {
        return service.createPerson(personInput).map {
            CreateResponse(it)
        }
    }

    @PatchMapping(value = ["/{id}"], consumes = ["application/json"], produces = ["application/json"])
    @Operation(summary = "Update a person", description = "Updates a person using patch")
    fun patch(@PathVariable id: UUID, @RequestBody personPatch: JsonPatch): Mono<Person> {
        return service.patchPerson(id, personPatch)
    }

}