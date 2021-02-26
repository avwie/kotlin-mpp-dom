package nl.avwie.dom.writers

import nl.avwie.common.datastructures.Deque
import nl.avwie.dom.Writer

class AppendableWriter(
    private val out: Appendable,
    private val prettyPrint: Boolean = false,
    private val prettyPrintIndent: String = "\t"
): Writer {

    private var inOpeningTag = false
    private var inText = false
    private var level = -1
    private val stack = Deque<String>()

    override fun beginElement(name: String) {
        require(!inText) { "Already added text in node '${stack.peekFront()}', so no new child element allowed"}
        if (inOpeningTag) {
            out.append(">")
            if (prettyPrint) out.append("\n")
        }

        inOpeningTag = true
        level += 1
        stack.prepend(name)

        if (prettyPrint) out.append(prettyPrintIndent.repeat(level))
        out.append("<")
        out.append(name)
    }

    override fun beginElementNS(namespace: String, name: String) {
        beginElement(name)
    }

    override fun attr(key: String, value: String) {
        require(inOpeningTag) { "Adding attributes not allowed anymore, since opening tag '${stack.peekFront()}' has already been generated." }
        out.append(" ")
        out.append(key)
        out.append("=\"")
        out.append(value)
        out.append("\"")
    }

    override fun text(text: String) {
        require(!inText) { "Already added text in node '${stack.peekFront()}', so no new text is allowed"}
        if (inOpeningTag) {
            out.append(">")
        }
        inText = true
        inOpeningTag = false
        out.append(text)
    }

    override fun html(html: String) {
        text(html)
    }

    override fun endElement() {
        if (inOpeningTag) {
            out.append(" />")
            if (prettyPrint) out.append("\n")
            stack.popFront()
        } else {
            if (prettyPrint && !inText) {
                out.append(prettyPrintIndent.repeat(level))
            }
            out.append("</")
            out.append(stack.popFront()!!)
            out.append(">")
            if (prettyPrint) {
                out.append("\n")
            }
        }
        inText = false
        inOpeningTag = false
        level -= 1
    }
}