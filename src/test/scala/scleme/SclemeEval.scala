package scleme

import com.twitter.util.Eval
import scleme.ast._
import scleme.preprocessor._
import scleme.code._
import treehugger.forest._
import definitions._
import treehuggerDSL._
import scalaz.{ Success, Failure }

trait SclemeEval {

  def mkCode(input: String): String = {
    Reader(input) match {
      case Failure(value) => throw new RuntimeException(value)
      case Success(value) => {
        val processedTree = value map (Preprocessor.apply(_))
        val scalaTree = Generator.apply0(processedTree)

        scalaTree.map(treeToString(_)) mkString "\n"
      }
    }
  }

  def eval[T](input: String): T = {
    val code =
      "import scleme.code.Core\n" + mkCode(input)

    new Eval()(code)
  }

}