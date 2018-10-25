package com.`fun`.myservice.controller

import com.`fun`.myservice.controller.annotations.JsonPatchController
import com.`fun`.myservice.controller.annotations.JsonPatchMapping

@JsonPatchController
class AnotherPatchController {

    @JsonPatchMapping(op = "remove")
    fun add(){
        print("inside add of AnotherPatchController")
    }


}