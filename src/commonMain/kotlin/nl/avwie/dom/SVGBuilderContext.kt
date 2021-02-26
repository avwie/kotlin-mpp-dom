package nl.avwie.dom

open class SVGBuilderContext(writer: Writer) : AbstractBuilderContext<SVGBuilderContext>(writer) {

    override fun invokeBlock(block: SVGBuilderContext.() -> Unit) {
        block.invoke(this)
    }

    override fun element(name: String, block: (SVGBuilderContext.() -> Unit)?) {
        elementNS("http://www.w3.org/2000/svg", name, block)
    }

    fun classes(vararg classes: String) {
        attr("class", classes.joinToString(" "))
    }
}