package com.`fun`.myservice.controller.dto

import java.util.UUID

data class Person(var firstName: String, var age: Int) {

    var id: UUID? = null

    var lastName: String? = null

}
