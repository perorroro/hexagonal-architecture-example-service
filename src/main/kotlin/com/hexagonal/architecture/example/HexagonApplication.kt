package com.hexagonal.architecture.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HexagonApplication

fun main(args: Array<String>) {
	runApplication<HexagonApplication>(*args)
}
