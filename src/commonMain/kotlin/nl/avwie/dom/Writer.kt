package nl.avwie.dom

interface Writer {
    fun beginElement(name: String)
    fun beginElementNS(namespace: String, name: String)

    fun endElement()

    fun attr(key: String, value: String)
    fun text(text: String)
    fun html(html: String)
}