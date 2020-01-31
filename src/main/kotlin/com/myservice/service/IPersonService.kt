package com.myservice.service

import com.github.fge.jsonpatch.JsonPatch
import com.myservice.controller.dto.Person
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface IPersonService {
    fun createPerson(personInput: Mono<Person>): Mono<UUID>
    fun findPerson(id: UUID): Mono<Person>
    fun patchPerson(id: UUID, personPatchRequest: JsonPatch): Mono<Person>
    fun findAll(): Flux<Person>
}