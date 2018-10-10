package com.`fun`.myservice.dal

import com.`fun`.myservice.dal.dto.Person
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PersonRepository: CassandraRepository<Person, UUID>