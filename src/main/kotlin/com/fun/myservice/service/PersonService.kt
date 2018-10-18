package com.`fun`.myservice.service

import com.`fun`.myservice.controller.dto.Person
import com.`fun`.myservice.dal.PersonRepository
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatch
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.*


@Service
class PersonService {

    @Autowired private lateinit var personRepository: PersonRepository
    @Autowired private lateinit var objectMapper: ObjectMapper

    fun createPerson(personInput: Publisher<Person>): Publisher<UUID> {
        return personInput.toMono().flatMap { p ->
            val person = com.`fun`.myservice.dal.dto.Person(id = UUID.randomUUID(), firstName = p.firstName, lastName = p.lastName, age = p.age)

            personRepository.save(person).flatMap {
                it.id.toMono()
            }
        }
    }

    fun findPerson(id: UUID): Mono<Person> {
        return personRepository.findById(id).map { p ->
            Person(id = p.id, firstName = p.firstName, lastName = p.lastName, age = p.age)
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

        return personUpdated.flatMap { t -> val personOutput =  Person(id= t.id, firstName = t.firstName, lastName =  t.lastName, age = t.age)
            personOutput.toMono()
        }
    }
}