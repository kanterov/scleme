package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class StringSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "\"hello\"" -> "hello",
    "\"Hello World\"" -> "Hello World")

  for ((input, output) <- inputOutput) {
    test("string " + input + " -> " + output) {
      eval[Boolean](input) should equal(output)
    }
  }

}