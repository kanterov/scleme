package scleme.ast

import scala.util.parsing.combinator.RegexParsers
import scalaz._
import scalaz.Scalaz._
import scleme.ast._

object Reader extends RegexParsers {

  override val skipWhitespace = false

  def space = rep1(" ")

  def symbol: Parser[Expr] = """[a-zA-Z!#$%&|*+/:<=>?@^_~-]\w*""".r ^^ {
    case "#t" => BoolExpr(true)
    case "#f" => BoolExpr(false)
    case sym => SymbolExpr(sym)
  }

  def string: Parser[StringExpr] = "\"" ~> rep("[^\"]".r) <~ "\"" ^^ { s => StringExpr(s.mkString) }

  def number: Parser[NumExpr] = rep1("-?[0-9]+".r) ^^ { n => NumExpr(n.mkString.toInt) }

  def list: Parser[ListExpr] = "(" ~> repsep(exp, space) <~ ")" ^^ { l => ListExpr(l) }

  def quoted: Parser[ListExpr] = "'" ~> exp ^^ { e => ListExpr(List(SymbolExpr("quote"), e)) }

  def exp: Parser[Expr] = list | number | symbol | string | quoted

  def apply(input: String): Validation[String, Expr] =
    parse(exp, input) match {
      case Success(res, _) => res.success
      case NoSuccess(msg, _) => msg.fail
    }

}