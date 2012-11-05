package scleme

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class Integer extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "0" -> 0,
    "1" -> 1,
    "-1" -> -1,
    "10" -> 10,
    "-10" -> -10,
    "2736" -> 2736,
    "-2736" -> -2736,
    "536870911" -> 536870911,
    "-536870912" -> -536870912)

  for ((input, output) <- inputOutput) {
    test("integer " + input + " -> " + output) {
      eval[Int](input) should be === output
    }
  }

}