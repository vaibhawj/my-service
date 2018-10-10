package com.`fun`.myservice.dal.dto

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

@Table
data class Person(@PrimaryKey var id: UUID, @Column var firstName: String, @Column var age: Int) {

    @Column
    var lastName: String? = null

}
