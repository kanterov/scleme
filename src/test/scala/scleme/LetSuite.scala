
package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class LetSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "(let ([x 5]) x)" -> 5,
    "(let ([x (+ 1 2)]) x)" -> 3,

    """(let ([x (+ 1 2)]) 
	 (let ([y (+ 3 4)])
	   (+ x y)))""" -> 10,

    """(let ([x (+ 1 2)]) 
	 (let ([y (+ 3 4)])
       (- y x)))""" -> 4)

  for ((input, output) <- inputOutput) {
    test(input + " -> " + output) {
      eval[Int](input) should be === output
    }
  }

}