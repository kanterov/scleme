package scleme

import com.twitter.util.Eval;
import java.io.ByteArrayInputStream

import scleme.ast._
import scleme.code._

import treehugger.forest._
import definitions._
import treehuggerDSL._

trait SclemeEval {

  def mkCode(input: String): String =
    treeToString(Generator.apply0(Reader.apply(input).toOption.get))
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