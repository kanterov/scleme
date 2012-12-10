package scleme

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import scalaz.Failure
import scalaz.Success
import org.scalatest.matchers.Matchers

class SclemeSuite extends FunSuite with ShouldMatchers with Matchers {

  test("generate code with package") {
    val code = Scleme.apply(packageName = "hello", objectName = "World", input = "")
    val text = code.toOption.get

    text should include("object World")
    text should include("package hello")
  }

  test("generate code without package") {
    val code = Scleme.apply(packageName = "", objectName = "World", input = "")
    val text = code.toOption.get

    text should include("object World")
    text should not include ("package")
  }

  test("generate code with package = null") {
    val code = Scleme.apply(packageName = null, objectName = "World", input = "")
    val text = code.toOption.get

    text should include("object World")
    text should not include ("package")
  }

  test("get Core code") {
    Scleme.core should include("object Core")
  }

}