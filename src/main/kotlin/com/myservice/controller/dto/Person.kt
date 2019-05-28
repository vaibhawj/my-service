package com.myservice.controller.dto

import java.util.*

data class Person(
    val id: UUID?,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val contact: Contact?
)

data class Contact(
    val homePhone: String?,
    val mobilePhone: String?
)
