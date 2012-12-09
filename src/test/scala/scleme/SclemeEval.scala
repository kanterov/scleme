package scleme

import com.twitter.util.Eval
import scalaz.{ Success, Failure }

trait SclemeEval {

  def mkCode(input: String): String = {
    Scleme(input) match {
      case Failure(value) => throw new RuntimeException(value)
      case Success(value) => value
    }
  }

  def eval[T](input: String): T = {
    val code =
      "import scleme.code.Core\n" + mkCode(input)

    new Eval()(code)
  }

}