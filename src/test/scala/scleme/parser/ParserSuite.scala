package scleme.parser

import scleme.ast._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class ParserSuite extends FunSuite with ShouldMatchers {

  val inputOutput = Map(
    "0" -> NumExpr(0),
    "-1" -> NumExpr(-1),
    "1" -> NumExpr(1),
    "#t" -> BoolExpr(true),
    "#f" -> BoolExpr(false),
    "'(1 2 3)" -> ListExpr(List(SymbolExpr("quote"), ListExpr(List(NumExpr(1), NumExpr(2), NumExpr(3))))),
    "(1 2 3)" -> ListExpr(List(NumExpr(1), NumExpr(2), NumExpr(3))))

  def parse(input: String): Expr = Reader.apply(input).toOption.get

  for ((input, output) <- inputOutput) {
    test("parse " + input) {
      parse(input) should equal(output)
    }
  }

}