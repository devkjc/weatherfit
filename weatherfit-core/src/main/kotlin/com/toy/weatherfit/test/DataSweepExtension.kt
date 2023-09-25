package com.toy.weatherfit.test

import org.apache.logging.log4j.ThreadContext.clearAll
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.support.TransactionTemplate

class DataSweepExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        context?.let { SpringExtension.getApplicationContext(it) }
            ?.getBean("transactionTemplate")
            ?.let { it as TransactionTemplate }
            ?.run { clearAll() }
    }
}