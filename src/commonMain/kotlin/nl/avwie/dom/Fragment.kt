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

interface XMLFragment : Fragment<XMLBuilderContext> {
    companion object {
        operator fun invoke(block: XMLBuilderContext.() -> Unit) = object : XMLFragment {
            override val block: XMLBuilderContext.() -> Unit = block
        }
    }
}
interface SVGFragment : Fragment<SVGBuilderContext> {
    companion object {
        operator fun invoke(block: SVGBuilderContext.() -> Unit) = object : SVGFragment {
            override val block: SVGBuilderContext.() -> Unit = block
        }
    }
}