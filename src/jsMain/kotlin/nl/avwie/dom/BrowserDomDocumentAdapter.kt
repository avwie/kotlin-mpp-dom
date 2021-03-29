package nl.avwie.dom

import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.get

class BrowserDomDocumentAdapter : DomDocumentAdapter {

    class BrowserDomElement(val inner: Element) : DomDocumentAdapter.Element {
        override fun setAttribute(key: String, value: String) {
            inner.setAttribute(key, value)
        }

        override fun getAttribute(key: String): String? {
            return inner.getAttribute(key)
        }

        override fun appendChild(element: DomDocumentAdapter.Element) {
            inner.appendChild((element as BrowserDomElement).inner)
        }

        override fun setText(text: String) {
            inner.textContent = text
        }

        override fun setHTML(html: String) {
            inner.innerHTML = html
        }

        override fun removeChild(element: DomDocumentAdapter.Element) {
            inner.removeChild((element as BrowserDomElement).inner)
        }
    }

    override fun createElement(name: String, namespace: String?): DomDocumentAdapter.Element {
        val element = namespace?.let { document.createElementNS(namespace, name) } ?: document.createElement(name)
        return BrowserDomElement(element)
    }

    override fun getElementById(id: String): DomDocumentAdapter.Element? {
        return document.getElementById(id)?.let { BrowserDomElement(it) }
    }

    override fun getElementsByTagName(name: String, namespace: String?): List<DomDocumentAdapter.Element> {
        val nodes = namespace?.let { document.getElementsByTagNameNS(namespace, name )} ?: document.getElementsByTagName(name)
        val elements = mutableListOf<BrowserDomElement>()
        for (i in 0 until nodes.length) {
            elements.add(BrowserDomElement(nodes.get(i)!!))
        }
        return elements
    }
}