package com.myservice.controller

import com.github.fge.jsonpatch.JsonPatch
import com.myservice.controller.dto.CreateResponse
import com.myservice.controller.dto.Person
import com.myservice.service.PersonService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/persons")
class PersonController {

    @Autowired
    lateinit var service: PersonService

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    @Operation(summary = "Find a person", description = "Finds a person with the id")
    fun find(@PathVariable id: UUID): Mono<ResponseEntity<Person>> {
        return service.findPerson(id).map { ResponseEntity.ok(it) }
    }

    @GetMapping(produces = ["application/json"])
    @Operation(summary = "List persons", description = "List all the people")
    fun list(): Mono<ResponseEntity<List<Person>>> {
        return service.findAll().collectList().map { ResponseEntity.ok(it) }
    }

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @Operation(summary = "Create a person", description = "Creates a person with inputs")
    fun create(@RequestBody personInput:  Mono<Person>): Mono<ResponseEntity<CreateResponse>> {
        return service.createPerson(personInput).map {
            ResponseEntity.ok(CreateResponse(it))
        }
    }

    @PatchMapping(value = ["/{id}"], consumes = ["application/json"], produces = ["application/json"])
    @Operation(summary = "Update a person", description = "Updates a person using patch")
    fun patch(@PathVariable id: UUID, @RequestBody personPatch: JsonPatch): Mono<ResponseEntity<Person>> {
        return service.patchPerson(id, personPatch).map { ResponseEntity.ok(it) }
    }

}