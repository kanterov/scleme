package scleme

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.matchers.ShouldMatchers

class IntegerOpSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "(+ 0 1)" -> 1,
    "(+ 1 2)" -> 3,
    "(* 1 0)" -> 0,
    "(* 3 2)" -> 6,
    "(/ 4 2)" -> 2,
    "(/ 6 1)" -> 6,
    "(= 1 1)" -> true,
    "(= 2 1)" -> false,
    "(= (+ 1 2) 3)" -> true)

  for ((input, output) <- inputOutput) {
    test("integerOp " + input + " -> " + output) {
      eval[Int](input) === output
    }
  }

}