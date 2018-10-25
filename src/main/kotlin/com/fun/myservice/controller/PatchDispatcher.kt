package com.`fun`.myservice.controller

import com.github.fge.jsonpatch.JsonPatch
import org.reflections.Reflections
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*

@Component
class PatchDispatcher {
    fun dispatch(id: UUID, personPatch: JsonPatch) {
        val reflections = Reflections("com.fun")
        val patchControllers = reflections.getTypesAnnotatedWith(JsonPatchController::class.java)

        val jsonPatchMethods = mutableListOf<Method>()
        patchControllers.forEach {
            it.declaredMethods.filter { it.isAnnotationPresent(JsonPatchMapping::class.java) }.toCollection(jsonPatchMethods)
        }
        val op = "add"

        print(jsonPatchMethods)
    }
}
