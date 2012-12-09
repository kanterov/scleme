package scleme

import scalaz.{ Validation, Success, Failure }
import scleme.code._
import scleme.ast._
import scleme.preprocessor._
import treehugger.forest._

object Scleme {

  def apply(input: String): Validation[String, String] = {
    Reader(input) match {
      case Failure(value) => Failure(value)
      case Success(value) => {
        val processedTree = value map (Preprocessor.apply(_))
        val scalaTree = Generator.apply0(processedTree)

        Success(scalaTree.map(treeToString(_)) mkString "\n")
      }
    }
  }

}