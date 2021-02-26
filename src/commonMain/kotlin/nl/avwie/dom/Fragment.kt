package nl.avwie.dom

interface Fragment<Context : BuilderContext<Context>> {

    val block: Context.() -> Unit

    operator fun invoke(context: Context) {
        block(context)
    }

    companion object {
        operator fun <Context : BuilderContext<Context>> invoke(block: Context.() -> Unit) = object :
            Fragment<Context> {
            override val block: Context.() -> Unit = block
        }
    }
}