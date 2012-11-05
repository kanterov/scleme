package scleme.ast

object parse {
  Reader.apply("(hello world)")                   //> res0: scalaz.Validation[String,scleme.ast.Expr] = Success((hello world))
  Reader.apply("123")                             //> res1: scalaz.Validation[String,scleme.ast.Expr] = Success(123)
}