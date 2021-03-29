package nl.avwie.dom

import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class JvmDomDocumentAdapter(val document: Document) : DomDocumentAdapter {

    class JvmDomElement(val inner: Element) : DomDocumentAdapter.Element {
        override fun setAttribute(key: String, value: String) {
            inner.setAttribute(key, value)
        }

        override fun getAttribute(key: String): String? {
            return inner.getAttribute(key)
        }

        override fun appendChild(element: DomDocumentAdapter.Element) {
            inner.appendChild((element as JvmDomElement).inner)
        }

        override fun setText(text: String) {
            inner.textContent = text
        }

        override fun setHTML(html: String) {
            inner.textContent = html
        }

        override fun removeChild(element: DomDocumentAdapter.Element) {
            inner.removeChild((element as JvmDomElement).inner)
        }
    }

    override fun createElement(name: String, namespace: String?): DomDocumentAdapter.Element {
        val element = namespace?.let { document.createElementNS(namespace, name) } ?: document.createElement(name)
        return JvmDomElement(element)
    }

    override fun getElementById(id: String): DomDocumentAdapter.Element? {
        return document.getElementById(id)?.let { JvmDomElement(it) }
    }

    override  fun getElementsByTagName(name: String, namespace: String?): List<DomDocumentAdapter.Element> {
        val nodes = namespace?.let { document.getElementsByTagNameNS(namespace, name) } ?: document.getElementsByTagName(name)
        val elements = mutableListOf<JvmDomElement>()
        for (i in 0 until nodes.length) {
            elements.add(JvmDomElement(nodes.item(i) as Element))
        }
        return elements
    }

    companion object {
        fun create(): JvmDomDocumentAdapter {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc = builder.newDocument()
            return JvmDomDocumentAdapter(doc)
        }
    }
}