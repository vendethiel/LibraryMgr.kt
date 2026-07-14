package org.vendethiel.librarymgr.library.model

import org.hibernate.type.descriptor.WrapperOptions
import org.hibernate.type.descriptor.java.AbstractClassJavaType
import org.hibernate.type.descriptor.java.LongJavaType

@JvmInline
final value class BookId(val id: Long)

class BookIdJavaType : AbstractClassJavaType<BookId>(BookId::class.java) {

    override fun <X> unwrap(value: BookId?, type: Class<X?>?, options: WrapperOptions?): X? =
         LongJavaType.INSTANCE.unwrap(value?.id, type, options)

    override fun <X> wrap(value: X?, options: WrapperOptions?): BookId =
        BookId(LongJavaType.INSTANCE.wrap(value, options))

}