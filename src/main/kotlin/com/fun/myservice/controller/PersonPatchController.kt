package com.`fun`.myservice.controller

@JsonPatchController
class PersonPatchController {

    @JsonPatchMapping(op = "add")
    fun add(){
        print("inside add of PersonPatchController")
    }
}