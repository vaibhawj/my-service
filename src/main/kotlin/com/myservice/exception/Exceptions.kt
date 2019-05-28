package com.myservice.exception

import java.lang.RuntimeException

class NotFoundException: RuntimeException("Person(s) not found")
