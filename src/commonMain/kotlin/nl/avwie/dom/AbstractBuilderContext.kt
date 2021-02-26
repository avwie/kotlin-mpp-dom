package nl.avwie.dom

abstract class AbstractBuilderContext<Context : BuilderContext<Context>>(override val writer: Writer) :
    BuilderContext<Context> {

    abstract fun invokeBlock(block: Context.() -> Unit)

    override fun element(name: String, block: (Context.() -> Unit)?) {
        writer.beginElement(name)
        block?.also { invokeBlock(it) }
        writer.endElement()
    }

    override fun elementNS(namespace: String, name: String, block: (Context.() -> Unit)?) {
        writer.beginElementNS(namespace, name)
        block?.also { invokeBlock(it) }
        writer.endElement()
    }

    override fun attr(key: String, value: String) {
        writer.attr(key, value)
    }

    override fun text(text: String) {
        writer.text(text)
    }

    override fun html(html: String) {
        writer.html(html)
    }

    override fun include(fragment: Fragment<Context>) {
        invokeBlock(fragment.block)
    }
}