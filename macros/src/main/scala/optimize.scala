
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context


object optimize {

  def impl(c: Context)(tree: c.Tree) = {
    import c.universe._
    println(showRaw(tree))
    tree
  }

  def apply[T](tree: => T): T = macro impl
}
