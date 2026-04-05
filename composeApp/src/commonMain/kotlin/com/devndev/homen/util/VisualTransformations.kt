package com.devndev.homen.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * 숫자 1,000 단위 콤마 변환기 오브젝트
 */
object ThousandSeparatorTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        if (originalText.isEmpty()) return TransformedText(text, OffsetMapping.Identity)

        // 콤마가 추가된 텍스트 생성
        val formatted = originalText.reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 0
                val commas = (offset - 1) / 3
                return offset + commas
            }

            override fun transformedToOriginal(offset: Int): Int {
                val commas = offset / 4
                return offset - commas
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}
