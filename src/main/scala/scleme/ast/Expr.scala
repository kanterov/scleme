package scleme.ast

class Expr

case class SymbolExpr(name: String) extends Expr {
  override def toString = "Symbol(" + name + ")"
}

case class ListExpr(elems: List[Expr]) extends Expr {
  override def toString = "ListExpr(" + elems.map(_.toString).mkString(" ") + ")"
}

case class NumExpr(i: Int) extends Expr {
  override def toString = "NumExpr(" + i.toString + ")"
}

case class StringExpr(s: String) extends Expr {
  override def toString = "StringExpr(" + s + ")"
}

case class BoolExpr(b: Boolean) extends Expr {
  override def toString = if (b) "BoolExpr(true)" else "BoolExpr(false)"
}

case class VectorExpr(elems: List[Expr]) extends Expr {
  override def toString = "VectorExpr(" + elems.map(_.toString).mkString(" ") + ")"
}