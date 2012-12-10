package scleme

import scalaz.{ Validation, Success, Failure }
import scleme.code._
import scleme.ast._
import scleme.preprocessor._
import treehugger.forest._
import org.apache.commons.lang3.StringUtils

object Scleme {

  val templatePath = "/scleme/Template.scala.tmpl"
  private val templateStream = Scleme.getClass().getResourceAsStream(templatePath)
  val template = new java.util.Scanner(templateStream).useDelimiter("\\Z").next()

  val corePath = "/scleme/Core.scala"
  private val coreStream = Scleme.getClass().getResourceAsStream(corePath + ".tmpl")
  val core = new java.util.Scanner(coreStream).useDelimiter("\\Z").next()

  val packageDefTag = "{{packageDef}}"
  val objectNameTag = "{{objectName}}"
  val innerCodeTag = "{{innerCode}}"

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

  def apply(packageName: String, objectName: String, input: String): Validation[String, String] = {
    apply(input) match {
      case Failure(value) => Failure(value)
      case Success(innerCode) => {
        // simple templating engine
        val packageDef =
          if (StringUtils.isEmpty(packageName)) ""
          else "package %s".format(packageName)

        Success(template
          .replace(packageDefTag, packageDef)
          .replace(objectNameTag, objectName)
          .replace(innerCodeTag, innerCode))
      }
    }

  }

}