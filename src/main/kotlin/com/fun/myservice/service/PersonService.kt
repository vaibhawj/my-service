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

    fun findPerson(id: UUID): Mono<Person> {
        return personRepository.findById(id).map { p -> val out = Person(firstName = p.firstName, age = p.age)
            out.id = p.id
            out.lastName = p.lastName
            out
        }

    }

    fun updatePerson(id: UUID, personInput: Publisher<Person>): Publisher<Person> {
        val personStored = personRepository.findById(id)
        val personUpdated = personInput.toMono().flatMap { input ->
            personStored.flatMap { personInDB ->
                Optional.ofNullable(input.age).ifPresent { age -> personInDB.age = age }
                Optional.ofNullable(input.firstName).ifPresent { firstName -> personInDB.firstName = firstName }
                Optional.ofNullable(input.lastName).ifPresent { lastName -> personInDB.lastName = lastName }
                personRepository.save(personInDB)
            }
        }

        return personUpdated.flatMap { t -> val personOutput =  Person(t.firstName, t.age)
            personOutput.lastName = t.lastName
            personOutput.id = t.id
            personOutput.toMono()
        }
    }
}