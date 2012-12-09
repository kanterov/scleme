package scleme.ast

import scala.util.parsing.combinator.RegexParsers
import scalaz.{ Validation, Success, Failure }
import scalaz.Scalaz.mkIdentity
import scleme.ast._
import org.apache.commons.lang3.StringEscapeUtils;

object Reader extends RegexParsers {

  override val skipWhitespace = false

  def space = """[ \t\n\r]+""".r

  def symbol: Parser[Expr] = """[a-zA-Z.!#$%&|*+/:<=>?@^_~-][0-9a-zA-Z!#$%&|*+/:<=>?@^_~-]*""".r ^^ {
    case "#t" => BoolExpr(true)
    case "#f" => BoolExpr(false)
    case sym => SymbolExpr(sym)
  }

  def string: Parser[StringExpr] = """"(?:[^"\\]|\\.)*"""".r ^^
    { s => StringExpr(StringEscapeUtils.unescapeJava(s.mkString.stripPrefix("\"").stripSuffix("\""))) }

  def number: Parser[NumExpr] = rep1("-?[0-9]+".r) ^^ { n => NumExpr(n.mkString.toInt) }

  def list: Parser[ListExpr] = "(" ~> repsep(expr, space) <~ ")" ^^ ListExpr

  def vector: Parser[VectorExpr] = "[" ~> repsep(expr, space) <~ "]" ^^ VectorExpr

  def quoted: Parser[ListExpr] = "'" ~> expr ^^ { e => ListExpr(List(SymbolExpr("quote"), e)) }

  def expr: Parser[Expr] = vector | list | number | symbol | string | quoted

  def file: Parser[List[Expr]] = rep(space) ~> repsep(expr, space) <~ rep(space)

  def apply(input: String): Validation[String, List[Expr]] =
    parseAll(file, input) match {
      case Success(res, _) => res.success
      case NoSuccess(msg, _) => msg.fail
    }

}