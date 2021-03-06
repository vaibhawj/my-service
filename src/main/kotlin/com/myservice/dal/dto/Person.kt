package com.myservice.dal.dto

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table
data class Person(@PrimaryKey val id: UUID,
                  @Column val firstName: String? = null,
                  @Column val lastName: String? = null,
                  @Column val age: Int? = null,
                  @Column val contact: Map<String, String>? = mapOf())

data class PersonPatch(
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val contact: Map<String, String>?
)