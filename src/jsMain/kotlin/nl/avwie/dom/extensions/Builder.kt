package nl.avwie.dom.extensions

import nl.avwie.dom.*
import nl.avwie.dom.writers.DomDocumentWriter
import org.w3c.dom.Element

fun Builder.toElement(): Element {
    val writer = DomDocumentWriter(BrowserDomDocumentAdapter())
    this.build(writer)
    return (writer.root as BrowserDomDocumentAdapter.BrowserDomElement).inner
}

fun Fragment<SVGBuilderContext>.toElement(): Element {
    val builder = SVGBuilder(fragment = this)
    return builder.toElement()
}