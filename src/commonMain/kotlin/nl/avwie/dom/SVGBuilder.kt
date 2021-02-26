package nl.avwie.dom

class SVGBuilder(val width: Int? = null, val height: Int? = null, val incSvg: Boolean = false,  val fragment: Fragment<SVGBuilderContext>):
    Builder {

    constructor(width: Int, height: Int, block: SVGBuilderContext.() -> Unit) : this(width, height, true, Fragment(block))

    override fun build(writer: Writer) {
        val context = SVGBuilderContext(writer)
        context {
            if (incSvg) {
                "svg" {
                    "xmlns" to "http://www.w3.org/2000/svg"
                    if (width != null) {
                        "width" to width
                    }

                    if (height != null) {
                        "height" to height
                    }

                    if (width != null && height != null) {
                        "viewBox" to "0 0 $width $height"
                    }
                    fragment(this)
                }
            } else {
                fragment(this)
            }
        }
    }

    companion object {
        fun fragment(block: SVGBuilderContext.() -> Unit) = Fragment(block)
    }
}