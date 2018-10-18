package com.`fun`.myservice.controller.dto

import java.util.UUID

data class Person(var firstName: String?,
                  var age: Int?,
                  var id: UUID?,
                  var lastName: String?) {
    constructor(firstName: String?, age: Int?) : this(firstName, age, null, null)
}
