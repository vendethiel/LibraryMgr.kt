package org.vendethiel.librarymgr.library.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

object IdConverters {

    @Converter(autoApply = true)
    class BookIdConverter : AttributeConverter<BookId, Long> {
        override fun convertToDatabaseColumn(attribute: BookId?): Long? {
            return attribute?.id
        }

        override fun convertToEntityAttribute(dbData: Long?): BookId? {
            return dbData?.let { BookId(it) }
        }

    }
}