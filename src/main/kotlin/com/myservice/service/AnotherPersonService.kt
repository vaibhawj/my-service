package com.myservice.service

import com.github.fge.jsonpatch.JsonPatch
import com.myservice.controller.dto.Person
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service("anotherService")
class AnotherPersonService : IPersonService {
    override fun createPerson(personInput: Mono<Person>): Mono<UUID> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findPerson(id: UUID): Mono<Person> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun patchPerson(id: UUID, personPatchRequest: JsonPatch): Mono<Person> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Flux<Person> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}