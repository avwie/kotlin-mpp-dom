package nl.avwie.dom

class XMLBuilderContext(writer: Writer) : AbstractBuilderContext<XMLBuilderContext>(writer) {
    override fun invokeBlock(block: XMLBuilderContext.() -> Unit) {
        block(this)
    }
}