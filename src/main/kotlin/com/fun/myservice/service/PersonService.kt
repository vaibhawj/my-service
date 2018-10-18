package com.`fun`.myservice.service

import com.`fun`.myservice.controller.dto.Person
import com.`fun`.myservice.dal.PersonRepository
import com.fasterxml.jackson.databind.JsonNode
import com.github.fge.jsonpatch.JsonPatch
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.*
import com.fasterxml.jackson.databind.ObjectMapper



@Service
class PersonService {

    @Autowired private lateinit var personRepository: PersonRepository
    @Autowired private lateinit var objectMapper: ObjectMapper

    fun createPerson(personInput: Publisher<Person>): Publisher<UUID> {
        return personInput.toMono().flatMap { p ->
            val person = com.`fun`.myservice.dal.dto.Person(id = UUID.randomUUID(), firstName = p.firstName, age = p.age)
            person.lastName = p.lastName

            personRepository.save(person).flatMap {
                it.id.toMono()
            }
        }
    }

    fun findPerson(id: UUID): Mono<Person> {
        return personRepository.findById(id).map { p -> val out = Person(firstName = p.firstName, age = p.age)
            out.id = p.id
            out.lastName = p.lastName
            out
        }

    }

    fun patchPerson(id: UUID, personPatch: JsonPatch): Publisher<Person> {
        val personStored = personRepository.findById(id)

        val personUpdated = personStored.flatMap { personInDB ->
            val originalNode = objectMapper.convertValue(personInDB, JsonNode::class.java)

            val patchedNode = personPatch.apply(originalNode)
            val newPerson = objectMapper.treeToValue(patchedNode, com.`fun`.myservice.dal.dto.Person::class.java)
            personRepository.save(newPerson)
        }

        return personUpdated.flatMap { t -> val personOutput =  Person(t.firstName, t.age, t.id, t.lastName )
            personOutput.toMono()
        }
    }
}