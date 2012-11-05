package scleme.code

import scleme.ast._

import treehugger.forest._
import definitions._
import treehuggerDSL._
import scala.tools.nsc.interpreter._
import scala.tools.nsc._

import java.io.PrintWriter;

object sample {
	val settings = {
		val settings0 = new Settings()
		settings0.usejavacp.tryToSet(List("true"))

		settings0
	}                                         //> settings  : scala.tools.nsc.Settings = Settings {
                                                  //|   -usejavacp = true
                                                  //|   -d = .
                                                  //| }
                                                  //| 

 	val writer = new java.io.StringWriter()   //> writer  : java.io.StringWriter = 
  val interpreter = new Interpreter(settings, new PrintWriter(writer))
                                                  //> interpreter  : scala.tools.nsc.Interpreter = scala.tools.nsc.Interpreter@3fb
                                                  //| 6101e
	
	val program = treeToString(Generator.apply(Reader.apply("123").toOption.get))
                                                  //> program  : String = object App {
                                                  //|   def main(args: Array[String]) = 123
                                                  //| }
                                                  
	
	interpreter.interpret(program)            //> res0: scala.tools.nsc.interpreter.package.IR.Result = Success
	interpreter.interpret("println(App.main(Array()))")
                                                  //> 123
                                                  //| res1: scala.tools.nsc.interpreter.package.IR.Result = Success

	//writer.getBuffer().toString()
	
}