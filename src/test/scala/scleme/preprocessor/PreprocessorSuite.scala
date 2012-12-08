package scleme.preprocessor

import scleme.ast._
import scleme.preprocessor._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

import treehugger.forest._
import definitions._
import treehuggerDSL._

class PreprocessorSuite extends FunSuite with ShouldMatchers {

  val inputOutput = Map(
    ListExpr(List(
      SymbolExpr("scala"),
      StringExpr("""OBJECTDEF("App") := BLOCK()""")))
      -> TreeExpr(OBJECTDEF("App") := BLOCK()),
    ListExpr(List(
      SymbolExpr("typed"),
      SymbolExpr("a"),
      SymbolExpr("Int")))
      -> TreeExpr(REF("a") DOT "asInstanceOf[Int]"))

  for ((input, output) <- inputOutput) {
    test("parse " + input) {
      // TODO: for some reason structure match doesn't work,
      // need to investigate this issue and remove toString
      // if it's possible
      Preprocessor(input).toString should equal(output.toString)
    }
  }

}