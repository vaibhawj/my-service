package com.`fun`.myservice.controller

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class JsonPatchMapping(val op: String)