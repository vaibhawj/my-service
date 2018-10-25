package com.`fun`.myservice.controller.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class JsonPatchMapping(val op: String)