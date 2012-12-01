package scleme.preprocessor

import scleme.ast._
import treehugger.forest._

case class TreeExpr(tree: Tree) extends Expr {
  override def toString = "TreeExpr(" + treeToString(tree) + ")"
}
