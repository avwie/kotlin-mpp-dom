package nl.avwie.dom

import nl.avwie.common.Base64.encodeToBase64

interface BuilderContext<Context : BuilderContext<Context>> {

    val writer: Writer

    fun element(name: String, block: (Context.() -> Unit)? = null)
    fun elementNS(namespace: String, name: String, block: (Context.() -> Unit)? = null)
    fun attr(key: String, value: String)
    fun text(text: String)
    fun html(html: String)
    fun include(fragment: Fragment<Context>)

    fun attr(name: String, value: Float) = attr(name, value.toString())
    fun attr(name: String, value: Double) = attr(name, value.toString())
    fun attr(name: String, value: Int) = attr(name, value.toString())
    fun attr(name: String, value: Long) = attr(name, value.toString())
    fun attr(name: String, value: ByteArray) = attr(name, value.encodeToBase64())

    infix fun String.to(value: String) { attr(this, value) }
    infix fun String.to(value: Float) { attr(this, value) }
    infix fun String.to(value: Double) { attr(this, value) }
    infix fun String.to(value: Int) { attr(this, value) }
    infix fun String.to(value: Long) { attr(this, value) }
    infix fun String.to(value: ByteArray) { attr(this, value) }

    operator fun Fragment<Context>.unaryPlus() { include(this) }

    operator fun invoke(block: (Context.() -> Unit)) { include(Fragment(block)) }

    operator fun String.invoke(block: (Context.() -> Unit)? = null) {
        element(this, block)
    }

    operator fun Map<String, String>.unaryPlus() {
        this.entries.forEach { (key, value) -> attr(key, value) }
    }
}