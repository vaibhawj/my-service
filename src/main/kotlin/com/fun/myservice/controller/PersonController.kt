package com.`fun`.myservice.controller

import com.`fun`.myservice.controller.dto.Person
import com.`fun`.myservice.service.PersonService
import com.github.fge.jsonpatch.JsonPatch
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/persons")
class PersonController {

    @Autowired
    lateinit var service: PersonService

    @GetMapping("/{id}")
    fun list(@PathVariable id: UUID): Publisher <Person> {
        return service.findPerson(id)
    }

    @PostMapping
    fun create(@RequestBody personInput: Publisher<Person>): Publisher<UUID> {
        return service.createPerson(personInput)
    }

    @PatchMapping("/{id}")
    fun patch(@PathVariable id: UUID, @RequestBody personInput: Publisher<Person>): Publisher<Person> {

        return service.updatePerson(id, personInput)

    }

}