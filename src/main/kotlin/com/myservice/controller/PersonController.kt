package com.myservice.controller

import com.myservice.controller.dto.Person
import com.myservice.service.PersonService
import com.github.fge.jsonpatch.JsonPatch
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import io.swagger.annotations.Example
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/persons")
class PersonController {

    @Autowired
    lateinit var service: PersonService

    @GetMapping("/{id}")
    fun find(@PathVariable id: UUID): Publisher <Person> {
        return service.findPerson(id)
    }

    @GetMapping
    fun list(): Publisher <Person> {
        return service.findAll()
    }

    @PostMapping
    fun create(@RequestBody personInput: Publisher<Person>): Publisher<UUID> {
        return service.createPerson(personInput)
    }

    @PatchMapping("/{id}")
    fun patch(@PathVariable id: UUID, @RequestBody personPatch: JsonPatch): Publisher<Person> {
        return service.patchPerson(id, personPatch)
    }

}