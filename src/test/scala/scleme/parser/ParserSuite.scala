package scleme.parser

import scleme.ast._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import scalaz.{ Success, Failure }

class ParserSuite extends FunSuite with ShouldMatchers {

  val inputOutput = Map(
    "0" -> NumExpr(0),
    "-1" -> NumExpr(-1),
    "1" -> NumExpr(1),
    "#t" -> BoolExpr(true),
    "#f" -> BoolExpr(false),
    "'(1 2 3)" -> ListExpr(List(SymbolExpr("quote"), ListExpr(List(NumExpr(1), NumExpr(2), NumExpr(3))))),
    "(1 2 3)" -> ListExpr(List(NumExpr(1), NumExpr(2), NumExpr(3))),
    "(fixnum? 1)" -> ListExpr(List(SymbolExpr("fixnum?"), NumExpr(1))),
    "[1 2 3]" -> VectorExpr(List(NumExpr(1), NumExpr(2), NumExpr(3))),
    """(let ([x 1]) 
         (let ([y 2]) 
           (+ x y)))""" -> ListExpr(List(
      SymbolExpr("let"),
      ListExpr(List(VectorExpr(List(SymbolExpr("x"), NumExpr(1))))),
      ListExpr(List(
        SymbolExpr("let"),
        ListExpr(List(VectorExpr(List(SymbolExpr("y"), NumExpr(2))))),
        ListExpr(List(SymbolExpr("+"), SymbolExpr("x"), SymbolExpr("y"))))))),

    """(define name
		  (lambda () 
		  	"THROW(\"RuntimeException\")"))
     """ -> ListExpr(
      List(SymbolExpr("define"),
        SymbolExpr("name"),
        ListExpr(
          List(SymbolExpr("lambda"),
            ListExpr(Nil),
            StringExpr("""THROW("RuntimeException")"""))))),

    "\"THROW\"" -> StringExpr("THROW"),
    "(scala \"throw\")" -> ListExpr(List(SymbolExpr("scala"), StringExpr("throw"))),
    "\t\"THROW\"\t" -> StringExpr("THROW"),
    "test0" -> SymbolExpr("test0"))

  def parse(input: String): Expr = Reader.apply(input) match {
    case Failure(msg) => throw new RuntimeException(msg)
    case Success(value) => value.head
  }

  for ((input, output) <- inputOutput) {
    test("parse " + input) {
      parse(input) should equal(output)
    }
  }

}