package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class ScalaInteropSuite extends FunSuite with ShouldMatchers with SclemeEval {

  test("seq.head") {
    val input = """
  (lambda (seq0)
    (let
      ([seq (typed seq0 "Seq[_]")])
      (.head seq)))"""

    val output = eval[Function1[Seq[Int], Int]](input)(List(1, 2, 3))

    output shouldBe (1)
  }

  test("seq.drop(1)") {
    val input = """
  (lambda (seq0)
    (let
      ([seq (typed seq0 "Seq[_]")])
      (.drop seq 1)))"""

    val output = eval[Function1[Seq[Int], Seq[Int]]](input)(List(1, 2, 3))

    output should equal(List(2, 3))
  }

  test("seq.drop(1).take(2)") {
    val input = """
  (lambda (seq0)
    (let
      ([seq (typed seq0 "Seq[_]")])
      (.take (.drop seq 1) 2)))"""

    val output = eval[Function1[Seq[Int], Seq[Int]]](input)(List(1, 2, 3, 4))

    output should equal(List(2, 3))
  }

}