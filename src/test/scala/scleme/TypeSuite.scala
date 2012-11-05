package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class Type extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "(fixnum? 0)" -> true,
    "(fixnum? 1)" -> true,
    "(fixnum? -1)" -> true,
    "(fixnum? 37287)" -> true,
    "(fixnum? -23873)" -> true,
    "(fixnum? 536870911)" -> true,
    "(fixnum? -536870912)" -> true,
    "(fixnum? #t)" -> false,
    "(fixnum? #f)" -> false,
    "(fixnum? ())" -> false,
    "(fixnum? #\\Q)" -> false,
    "(fixnum? (fixnum? 12))" -> false,
    "(fixnum? (fixnum? #f))" -> false,
    "(fixnum? (fixnum? #\\A))" -> false,
    "(fixnum? ($char->fixnum #\\r))" -> true,
    "(fixnum? ($fixnum->char 12))" -> false)
    
  for ((input, output) <- inputOutput) {
    test("type " + input + " -> " + output) {
      eval[Boolean](input) === output
    }
  }
    

}