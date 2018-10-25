package com.`fun`.myservice.controller

@JsonPatchController
class AnotherPatchController {

    @JsonPatchMapping(op = "add")
    fun add(){
        print("inside add of AnotherPatchController")
    }


}