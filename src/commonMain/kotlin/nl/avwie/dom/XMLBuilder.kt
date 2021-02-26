package nl.avwie.dom

class XMLBuilder(val root: String? = null, val fragment: Fragment<XMLBuilderContext>) : Builder {

    constructor(root: String? = null, block: XMLBuilderContext.() -> Unit) : this(root, Fragment(block))
    constructor(block: XMLBuilderContext.() -> Unit) : this(null, Fragment(block))

    override fun build(writer: Writer) {
        val context = XMLBuilderContext(writer)

        if (root != null) {
            writer.beginElement(root)
        }
        fragment(context)

        if (root != null) {
            writer.endElement()
        }
    }

    companion object {
        fun fragment(block: XMLBuilderContext.() -> Unit) = Fragment(block)
    }
}