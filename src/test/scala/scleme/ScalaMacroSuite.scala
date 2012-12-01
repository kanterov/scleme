package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class ScalaMacroSuite extends FunSuite with ShouldMatchers with SclemeEval {

  test("throw RuntimeException") {
    val input = """((lambda () (scala "THROW(\"java.lang.RuntimeException\")")))"""

    evaluating {
      eval[Any](input)
    } should produce[java.lang.RuntimeException]
  }

  test("throw NullPointerException") {
    val input = """((lambda () (scala "THROW(\"java.lang.NullPointerException\")")))"""

    evaluating {
      eval[Any](input)
    } should produce[java.lang.NullPointerException]
  }

}