package com.myservice.dal

import com.myservice.dal.dto.Person
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PersonRepository: ReactiveCassandraRepository<Person, UUID>