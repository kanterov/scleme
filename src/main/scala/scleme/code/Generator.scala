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

  def is2Op(x: String): Boolean = List("<=", "<", ">", ">=").contains(x)

  def apply0(expr: List[Expr]): Seq[Tree] = expr.map(apply0)

  def apply0(expr: Expr*): Seq[Tree] = expr.map(apply0)

  // TODO: 
  // 1. do proper replacement
  // 2. move to preprocessor phase
  def prepareName(name: String): String =
    name.replace('-', '_')

  // TODO:
  // 1. support of call-by-name parameters
  def mkParams(args: List[Expr], body: Expr) =
    args map { case SymbolExpr(name) => PARAM(name, AnyClass).mkTree(EmptyTree) }

  def apply0(expr: Expr): Tree = {
    expr match {
      case NumExpr(i) => LIT(i)

      case BoolExpr(b) => LIT(b)

      case SymbolExpr(op) if isOp(op) =>
        (REF("Core") DOT op)

      case ListExpr(SymbolExpr("define") :: SymbolExpr(name) ::
        ListExpr(SymbolExpr("lambda") :: ListExpr(args) :: body :: Nil) :: Nil) => {
        DEF(prepareName(name)) withType (AnyClass) withParams (mkParams(args, body)) := apply0(body)
      }

      case ListExpr(SymbolExpr("lambda") :: ListExpr(args) :: body :: Nil) => {
        LAMBDA(mkParams(args, body)) ==> apply0(body)
      }

      case ListExpr(SymbolExpr("define") :: SymbolExpr(name) :: value :: Nil) =>
        VAL(prepareName(name)) := apply0(value)

      case ListExpr((x @ SymbolExpr(op)) :: rest) if isOp(op) =>
        apply0(x) APPLY (apply0(rest))

      case ListExpr(SymbolExpr("=") :: a :: b :: Nil) =>
        INFIX_CHAIN("==", apply0(a, b))

      case ListExpr(SymbolExpr("char->fixnum") :: a :: Nil) =>
        (REF("Core") DOT "charToFixnum") APPLY (apply0(a))

      case ListExpr(SymbolExpr("fixnum->char") :: a :: Nil) =>
        (REF("Core") DOT "fixnumToChar") APPLY (apply0(a))

      case ListExpr(SymbolExpr("fixnum?") :: a :: Nil) =>
        (REF("Core") DOT "isFixnum") APPLY (apply0(a))

      case ListExpr(SymbolExpr("and") :: rest) =>
        INFIX_CHAIN("&&", apply0(rest))

      case ListExpr(SymbolExpr("or") :: rest) =>
        INFIX_CHAIN("||", apply0(rest))

      case ListExpr(SymbolExpr("not") :: a :: Nil) =>
        (NOT(apply0(a)))

      case ListExpr(SymbolExpr(op) :: a :: b :: Nil) if is2Op(op) =>
        INFIX_CHAIN(op, apply0(a, b))

      case VectorExpr(v) =>
        VECTOR(apply0(v))

      case ListExpr(SymbolExpr("let") :: ListExpr(List(VectorExpr(v))) :: expr :: Nil) if v.size % 2 == 0 =>
        BLOCK(
          v.grouped(2).map {
            case SymbolExpr(x) :: value :: Nil =>
              VAL(x) := apply0(value)

          }.toList ++ List(EmptyTree, apply0(expr)))

      case ListExpr(SymbolExpr("if") :: cond :: then :: elze :: Nil) =>
        IF(apply0(cond)) THEN apply0(then) ELSE apply0(elze)

      case SymbolExpr(name) => REF(prepareName(name))

      case ListExpr(x :: rest) =>
        PAREN(apply0(x)) APPLY (apply0(rest))
    }
  }

}