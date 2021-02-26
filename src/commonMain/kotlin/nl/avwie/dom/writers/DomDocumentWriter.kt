package nl.avwie.dom.writers

import nl.avwie.common.datastructures.Deque
import nl.avwie.dom.DomDocumentAdapter
import nl.avwie.dom.Writer

class DomDocumentWriter(val document: DomDocumentAdapter) : Writer {

    private val stack: Deque<DomDocumentAdapter.Element> = Deque()

    var root: DomDocumentAdapter.Element? = null
        private set

    override fun beginElement(name: String) {
        document.createElement(name).also { element ->
            stack.prepend(element)
            if (stack.size == 1) {
                root = element
            }
        }
    }

    override fun beginElementNS(namespace: String, name: String) {
        document.createElementNS(namespace, name).also { element ->
            stack.prepend(element)
            if (stack.size == 1) {
                root = element
            }
        }
    }

    override fun endElement() {
        stack.popFront()?.also { element ->
            stack.peekFront()?.also { parent ->
                parent.appendChild(element)
            }
        }
    }

    override fun attr(key: String, value: String) {
        stack.peekFront()?.also { element ->
            element.setAttribute(key, value)
        }
    }

    override fun text(text: String) {
        stack.peekFront()?.also { element ->
            element.setText(text)
        }
    }

    override fun html(html: String) {
        stack.peekFront()?.also { element ->
            element.setHTML(html)
        }
    }
}