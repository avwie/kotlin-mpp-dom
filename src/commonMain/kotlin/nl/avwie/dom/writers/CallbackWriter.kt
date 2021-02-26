package nl.avwie.dom.writers

import nl.avwie.common.datastructures.Deque
import nl.avwie.dom.Writer

class CallbackWriter(val inner: Writer) : Writer {

    var onBeginElement : (String) -> Unit = {}
    var onBeginElementNS : (String, String) -> Unit = { _, _-> }
    var onEndElement : (String) -> Unit = {}
    var onAttr : (String, String) -> Unit = { _, _ -> }
    var onText : (String) -> Unit = {}
    var onHtml : (String) -> Unit = {}

    private val stack = Deque<String>()

    override fun beginElement(name: String) {
        stack.append(name)
        onBeginElement(name)
        inner.beginElement(name)
    }

    override fun beginElementNS(namespace: String, name: String) {
        stack.append(name)
        onBeginElementNS(namespace, name)
        inner.beginElementNS(namespace, name)
    }

    override fun endElement() {
        onEndElement(stack.popFront()!!)
        inner.endElement()
    }

    override fun attr(key: String, value: String) {
        onAttr(key, value)
        inner.attr(key, value)
    }

    override fun text(text: String) {
        onText(text)
        inner.text(text)
    }

    override fun html(html: String) {
        onHtml(html)
        inner.html(html)
    }
}