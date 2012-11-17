package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class LambdaSuite extends FunSuite with ShouldMatchers with SclemeEval {
  val inputOutput = Map(
    "((lambda (x) (+ x x)) 4)" -> 8)

  for ((input, output) <- inputOutput) {
    test("lambda " + input + " -> " + output) {
      eval[Int](input) should equal(output)
    }
  }

}