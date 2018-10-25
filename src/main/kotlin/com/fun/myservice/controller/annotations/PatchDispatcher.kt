package com.`fun`.myservice.controller.annotations

import com.github.fge.jsonpatch.JsonPatch
import org.reflections.Reflections
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*


@Component
class PatchDispatcher {

    @Autowired
    lateinit var appContext: ApplicationContext

    fun dispatch(id: UUID, personPatch: JsonPatch): Any? {
        val reflections = Reflections("com.fun")
        val op = "add"

        val jsonPatchMethods = mutableListOf<Method>()
        reflections.getTypesAnnotatedWith(JsonPatchController::class.java).forEach {
            it.declaredMethods.filter { it.isAnnotationPresent(JsonPatchMapping::class.java) &&
                    it.getAnnotation(JsonPatchMapping::class.java).op == op }.toCollection(jsonPatchMethods)
        }

        when {
            jsonPatchMethods.size == 0 -> throw RuntimeException("No method found for operation $op")
            jsonPatchMethods.size > 1 -> throw RuntimeException("${jsonPatchMethods.size}  operations found! Only one allowed")
            appContext.getBeansOfType(jsonPatchMethods[0].declaringClass).size > 1 -> throw RuntimeException("More than one bean found for " +
                    "${jsonPatchMethods[0].declaringClass}")
        }

        return jsonPatchMethods[0].invoke(appContext.getBeansOfType(jsonPatchMethods[0].declaringClass).values.elementAt(0),
            id, personPatch)

    }
}
