package scleme

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.matchers.ShouldMatchers

class BooleanOpSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "(and #t #t)" -> true,
    "(and #t #f)" -> false,
    "(and #f #f)" -> false,
    "(and #t #t #f)" -> false,
    "(or #t #t)" -> true,
    "(or #t #f)" -> true,
    "(or #f #f)" -> false,
    "(or #f #f #t)" -> true)

  for ((input, output) <- inputOutput) {
    test("booleanOp " + input + " -> " + output) {
      eval[Boolean](input) should equal(output)
    }
  }

}