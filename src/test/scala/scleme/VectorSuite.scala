package scleme

import scleme.ast._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class VectorSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val inputOutput = Map(
    "[1 2 3]" -> Vector(1, 2, 3),
    "[(+ 1 2) (+ 3 4)]" -> Vector(3, 7),
    "[]" -> Vector(),
    "[[1] 2]" -> Vector(Vector(1), 2))

  for ((input, output) <- inputOutput) {
    test("vector " + input + " -> " + output) {
      eval[Vector[Int]](input) should equal(output)
    }
  }

}