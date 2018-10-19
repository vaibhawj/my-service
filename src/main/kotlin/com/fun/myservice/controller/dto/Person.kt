package com.`fun`.myservice.controller.dto

import java.util.*

data class Person(var id: UUID?,
                  var firstName: String?,
                  var lastName: String?,
                  var age: Int?,
                  var contact: Contact?)

data class Contact(var homePhone: String?,
                   var mobilePhone: String?)