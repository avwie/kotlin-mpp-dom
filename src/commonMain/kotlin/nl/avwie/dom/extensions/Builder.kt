package nl.avwie.dom.extensions

import nl.avwie.dom.*
import nl.avwie.common.Base64.encodeToBase64
import nl.avwie.dom.*
import nl.avwie.dom.writers.AppendableWriter

fun Builder.toText(prettyPrint: Boolean = false): String {
    val appendable = StringBuilder()
    val writer = AppendableWriter(appendable, prettyPrint)
    build(writer)
    return appendable.toString()
}


fun Builder.toDataURI(): String {
    val text = this.toText()
    val base64 = text.encodeToByteArray().encodeToBase64()
    return "data:image/svg+xml;base64,$base64"
}

fun Fragment<XMLBuilderContext>.toXMLText(prettyPrint: Boolean): String {
    val builder = XMLBuilder(root = null, this)
    return builder.toText(prettyPrint)
}

fun Fragment<SVGBuilderContext>.toSVGText(prettyPrint: Boolean): String {
    val builder = SVGBuilder(incSvg = false, fragment = this)
    return builder.toText(prettyPrint)
}