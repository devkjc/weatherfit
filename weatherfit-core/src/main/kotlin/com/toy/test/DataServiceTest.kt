package com.toy.test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@ExtendWith(DataSweepExtension::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
annotation class DataServiceTest
