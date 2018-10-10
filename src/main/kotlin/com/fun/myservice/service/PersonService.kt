package com.`fun`.myservice.service

import com.`fun`.myservice.controller.dto.Person
import com.`fun`.myservice.dal.PersonRepository
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.*

@Service
class PersonService {

    @Autowired lateinit var personRepository: PersonRepository

    fun createPerson(personInput: Publisher<Person>): Publisher<UUID> {
        return personInput.toMono().flatMap { p ->
            val person = com.`fun`.myservice.dal.dto.Person(id = UUID.randomUUID(), firstName = p.firstName, age = p.age)
            person.lastName = p.lastName

            personRepository.save(person).flatMap {
                it.id.toMono()
            }
        }
    }

    fun findPerson(id: UUID): Mono<com.`fun`.myservice.dal.dto.Person> {
        return personRepository.findById(id)
    }

}