package com.`fun`.myservice.dal

import com.`fun`.myservice.dal.dto.Person
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PersonRepository: ReactiveCassandraRepository<Person, UUID>