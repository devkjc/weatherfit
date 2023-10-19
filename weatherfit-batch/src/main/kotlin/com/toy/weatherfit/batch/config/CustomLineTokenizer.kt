package com.toy.weatherfit.batch.config

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer

/**
 * 기상청에서 간격을 유지하기 위한 공백을 사용해 그를 제거하고 ,를 구분자로 사용하는 CustomTokenizer
 */
class CustomLineTokenizer : DelimitedLineTokenizer(",") {

    override fun doTokenize(line: String): MutableList<String> {
        val modifiedLine = line.replace(Regex("\\s+"), ",")
        return super.doTokenize(modifiedLine)
    }
}