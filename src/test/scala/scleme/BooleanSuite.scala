package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class BooleanSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "#f" -> false,
    "#t" -> true)

  for ((input, output) <- inputOutput) {
    test("boolean " + input + " -> " + output) {
      eval[Boolean](input) should be === output
    }
  }

}