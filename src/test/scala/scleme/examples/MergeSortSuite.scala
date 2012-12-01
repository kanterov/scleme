package scleme.examples

import org.scalatest.matchers.ShouldMatchers
import scleme.SclemeEval
import org.scalatest.FunSuite
import java.util.Scanner

class MergeSortSuite extends FunSuite with ShouldMatchers with SclemeEval {

  val code = new Scanner(
    this.getClass().getResourceAsStream("/scleme/examples/mergeSort.scl"))
    .useDelimiter("\\Z").next()

  test("merge sort") {
    eval[List[Int]](code) should equal(List(1, 2, 3))
  }

}