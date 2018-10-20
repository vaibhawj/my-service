package com.`fun`.myservice.dal.dto

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table
data class Person(@PrimaryKey val id: UUID,
                  @Column val firstName: String?,
                  @Column val lastName: String?,
                  @Column val age: Int?,
                  @Column val contact: Map<String, String>?)

data class PersonPatch(
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val contact: Map<String, String>?
)