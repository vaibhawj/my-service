package com.`fun`.myservice.controller

import com.`fun`.myservice.controller.dto.Person
import com.`fun`.myservice.service.PersonService
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class PersonController {

    @Autowired
    lateinit var service: PersonService

    @GetMapping("/persons/{id}")
    fun list(@PathVariable id: UUID): Optional<com.`fun`.myservice.dal.dto.Person> {
        return service.findPerson(id)
    }

    @PostMapping("/persons")
    fun create(@RequestBody personInput: Publisher<Person>): Publisher<UUID> {
        return service.createPerson(personInput)
    }

}