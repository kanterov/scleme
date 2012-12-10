package scleme

import com.twitter.util.Eval
import scalaz.{ Success, Failure }

trait SclemeEval {

  val coreCode = new java.util.Scanner(
    this.getClass().getResourceAsStream("/scleme/Core.scala.tmpl"))
    .useDelimiter("\\Z")
    .next()
    // can't eval package
    .replace("package scleme", "")

  def mkCode(input: String): String = {
    Scleme(input) match {
      case Failure(value) => throw new RuntimeException(value)
      case Success(value) => value
    }
  }

  def eval[T](input: String): T = {
    val code = coreCode + "\n" + mkCode(input)

    new Eval()(code)
  }

}