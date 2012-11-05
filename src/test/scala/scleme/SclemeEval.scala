package scleme

import com.twitter.util.Eval;
import java.io.ByteArrayInputStream

import scleme.ast._
import scleme.code._

import treehugger.forest._
import definitions._
import treehuggerDSL._

trait SclemeEval {

  def eval[T](input: String): T = {
    val code =
      "import scleme.code.Core\n" +
        treeToString(Generator.apply0(Reader.apply(input).toOption.get))

    new Eval()(code)
  }

}