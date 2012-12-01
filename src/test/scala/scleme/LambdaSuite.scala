package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class LambdaSuite extends FunSuite with ShouldMatchers with SclemeEval {
  // temporary use list, while we don't parse >1 s-expr
  val inputOutput = Map(
    "((lambda (x) (+ x x)) 4)" -> 8,

    """
	    (define reverse-subtract (lambda (x y) (- y x)))
	    (reverse-subtract 7 10)
    """ -> 3,

    """
    (define fact
				(lambda (n)
				(if (= n 0)
				1
				(* n (fact (- n 1))))))	
    (fact 3)
    """ -> 6)

  for ((input, output) <- inputOutput) {
    test("lambda " + input + " -> " + output) {
      eval[Int](input) should equal(output)
    }
  }

}