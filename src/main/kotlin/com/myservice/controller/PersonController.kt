package com.myservice.controller

import com.myservice.controller.dto.Person
import com.myservice.service.PersonService
import com.github.fge.jsonpatch.JsonPatch
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
    fun find(@PathVariable id: UUID): Mono<Person> {
        return service.findPerson(id)
    }

    @GetMapping(produces = ["application/json"])
    fun list(): Flux<Person> {
        return service.findAll()
    }

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun create(@RequestBody personInput:  Mono<Person>): Mono<UUID> {
        return service.createPerson(personInput)
    }

    @PatchMapping(value = ["/{id}"], consumes = ["application/json"], produces = ["application/json"])
    fun patch(@PathVariable id: UUID, @RequestBody personPatch: JsonPatch): Mono<Person> {
        return service.patchPerson(id, personPatch)
    }

}