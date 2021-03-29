package nl.avwie.dom

interface DomDocumentAdapter {
    interface Element {
        fun setAttribute(key: String, value: String)
        fun getAttribute(key: String): String?
        fun appendChild(element: Element)
        fun removeChild(element: Element)

        fun setText(text: String)
        fun setHTML(html: String)
    }

    fun createElement(name: String, namespace: String? = null): Element

    fun getElementById(id: String): Element?
    fun getElementsByTagName(name: String, namespace: String? = null): List<Element>
}