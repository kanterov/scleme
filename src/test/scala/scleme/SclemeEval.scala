package scleme

import com.twitter.util.Eval;
import java.io.ByteArrayInputStream

import scleme.ast._
import scleme.preprocessor._
import scleme.code._

import treehugger.forest._
import definitions._
import treehuggerDSL._

trait SclemeEval {

  def mkCode(input: String): String = {
    val tree = Reader(input).toOption.get
    val processedTree = Preprocessor(tree)
    val scalaTree = Generator.apply0(processedTree)

    treeToString(scalaTree)
  }

  def eval[T](input: String): T = {
    val code =
      "import scleme.code.Core\n" + mkCode(input)

    new Eval()(code)
  }

  def eval[T](input: List[String]): T = {
    val code =
      "import scleme.code.Core\n" +
        (input map mkCode mkString "\n")

    new Eval()(code)
  }

}