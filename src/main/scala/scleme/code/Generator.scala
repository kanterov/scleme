package scleme.code

import scleme.ast._
import treehugger.forest._
import definitions._
import treehuggerDSL._

object Generator {

  def apply(expr: Expr): Tree = {
    OBJECTDEF("App") := BLOCK(
      (DEF("apply") := apply0(expr)),
      (DEF("main") withParams (PARAM("args", TYPE_ARRAY(StringClass))) := BLOCK()))
  }
  
  def isOp(x: String): Boolean = List("+", "-", "/", "%", "*").contains(x)

  def apply0(expr: List[Expr]): Seq[Tree] = expr.map(apply0)
  
  def apply0(expr: Expr*): Seq[Tree] = expr.map(apply0)
  
  def apply0(expr: Expr): Tree = {
    expr match {
      case NumExpr(i) => LIT(i)
      
      case BoolExpr(b) => LIT(b)
      
      case ListExpr(SymbolExpr(op) :: rest) if isOp(op) =>
          INFIX_CHAIN(op, apply0(rest))
          
      case ListExpr(SymbolExpr("=") :: a :: b :: Nil) =>
          INFIX_CHAIN("==", apply0(a, b))

      case ListExpr(SymbolExpr("char->fixnum") :: a :: Nil) =>
          (REF("Core") DOT "charToFixnum") APPLY (apply0(a))
          
      case ListExpr(SymbolExpr("fixnum->char") :: a :: Nil) =>
          (REF("Core") DOT "fixnumToChar") APPLY (apply0(a))
      
      case ListExpr(SymbolExpr("fixnum?") :: a :: Nil) =>
          (REF("Core") DOT "isFixnum") APPLY (apply0(a))
    }
  }

}