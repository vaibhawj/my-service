package com.`fun`.myservice.controller

import com.`fun`.myservice.controller.annotations.JsonPatchController
import com.`fun`.myservice.controller.annotations.JsonPatchMapping
import com.`fun`.myservice.controller.dto.Person
import com.`fun`.myservice.service.PersonService
import com.github.fge.jsonpatch.JsonPatch
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@JsonPatchController
class PersonPatchController {

    @Autowired
    lateinit var service: PersonService

    @JsonPatchMapping(op = "add")
    fun add(id: UUID, personPatch: JsonPatch): Publisher<Person> {
        return service.patchPerson(id, personPatch)
    }
}