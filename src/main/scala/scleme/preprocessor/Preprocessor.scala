package scleme.preprocessor

import scleme.ast._
import com.twitter.util.Eval;

import treehugger.forest._
import definitions._
import treehuggerDSL._

object Preprocessor {

  def apply(expr: Expr) = apply0(expr)

  def apply0(expr: Expr): Expr = expr match {
    case ListExpr(SymbolExpr("scala") :: StringExpr(code) :: Nil) =>
      TreeExpr(eval(code))

    case ListExpr(SymbolExpr("typed") :: SymbolExpr(x) :: SymbolExpr(t) :: Nil) =>
      TreeExpr(REF(x) DOT "asInstanceOf[%s]".format(t))

    case ListExpr(SymbolExpr("typed") :: SymbolExpr(x) :: StringExpr(t) :: Nil) =>
      TreeExpr(REF(x) DOT "asInstanceOf[%s]".format(t))

    case ListExpr(elems) => ListExpr(elems.map(apply0))

    case VectorExpr(elems) => VectorExpr(elems.map(apply0))

    case a: Expr => a
  }

  def eval(code: String): Tree = {
    val imports = """
      import treehugger.forest._
      import definitions._
      import treehuggerDSL._
      
      val tree: Tree =
    """
    val postfix = "\ntree"

    new Eval()(imports + code + postfix)
  }

}