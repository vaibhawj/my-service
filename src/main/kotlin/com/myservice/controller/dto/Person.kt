package com.myservice.controller.dto

import java.util.*

data class Person(
    val id: UUID? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null,
    val contact: Contact? = null
)

data class Contact(
    val homePhone: String? = null,
    val mobilePhone: String? = null
)
