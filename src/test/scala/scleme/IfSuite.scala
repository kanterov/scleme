package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class IfSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "(if (fixnum? 1) 2 3)" -> 2,
    "(if #f 2 3)" -> 3,
    "(if (not #t) 2 3)" -> 3)

  for ((input, output) <- inputOutput) {
    test("if " + input + " -> " + output) {
      eval[Int](input) should be === output
    }
  }

}