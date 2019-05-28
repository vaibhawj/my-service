package com.myservice.controller

import com.myservice.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class PersonControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handlePersonNotFound() {

    }
}