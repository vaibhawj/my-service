package com.myservice.service

import com.myservice.controller.dto.Contact
import com.myservice.controller.dto.Person
import com.myservice.dal.PersonRepository
import com.myservice.dal.dto.PersonPatch
import com.myservice.exception.NotFoundException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatch
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.*


@Service("oldService")
@Primary
class PersonService : IPersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun createPerson(personInput: Mono<Person>): Mono<UUID> {
        return personInput.flatMap { p ->
            var contact = mutableMapOf<String, String>()
            p.contact?.homePhone?.let { contact.put("homePhone", it) }
            p.contact?.mobilePhone?.let { contact.put("mobilePhone", it) }

            val person = com.myservice.dal.dto.Person(
                    id = UUID.randomUUID(),
                    firstName = p.firstName,
                    lastName = p.lastName,
                    age = p.age,
                    contact = contact
            )

            personRepository.save(person).flatMap {
                it.id.toMono()
            }
        }
    }

    override fun findPerson(id: UUID): Mono<Person> {
        return personRepository.findById(id).switchIfEmpty(Mono.error(NotFoundException())).map { p ->
            Person(
                    id = p.id,
                    firstName = p.firstName,
                    lastName = p.lastName,
                    age = p.age,
                    contact = Contact(p.contact?.get("homePhone"), p.contact?.get("mobilePhone"))
            )
        }

    }

    override fun patchPerson(id: UUID, personPatchRequest: JsonPatch): Mono<Person> {
        val personStored = personRepository.findById(id)

        val personUpdated = personStored.map { p ->
            val personOriginal =
                    PersonPatch(firstName = p.firstName, lastName = p.lastName, age = p.age, contact = p.contact)

            val personPatched = objectMapper.treeToValue(
                    personPatchRequest.apply(objectMapper.convertValue(personOriginal, JsonNode::class.java)),
                    PersonPatch::class.java
            )
            val personToSave = com.myservice.dal.dto.Person(
                    id = p.id,
                    firstName = personPatched.firstName,
                    lastName = personPatched.lastName,
                    age = personPatched.age,
                    contact = personPatched.contact
            )
            personRepository.save(personToSave)
        }

        return personUpdated.flatMap { personMono ->
            personMono.flatMap { p ->
                val personOutput = Person(
                        id = p.id,
                        firstName = p.firstName,
                        lastName = p.lastName,
                        age = p.age,
                        contact = Contact(p.contact?.get("homePhone"), p.contact?.get("mobilePhone"))
                )
                personOutput.toMono()
            }

        }
    }

    override fun findAll(): Flux<Person> {
        return personRepository.findAll()
                .switchIfEmpty { it.onError(NotFoundException()) }
                .map {
                    Person(
                            id = it.id,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            age = it.age,
                            contact = Contact(it.contact?.get("homePhone"), it.contact?.get("mobilePhone"))
                    )
                }
    }
}