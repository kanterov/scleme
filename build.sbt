name := "scleme"

version := "0.1-SNAPSHOT"

organization := "com.github.kanterov"

scalaVersion := "2.9.2"

resolvers ++= Seq(
    "snapshots" at "http://scala-tools.org/repo-snapshots", 
    "releases"  at "http://scala-tools.org/repo-releases",
    "sonatype" at "https://oss.sonatype.org/content/groups/public",
    "twitter-repo" at "http://maven.twttr.com/")

libraryDependencies ++= Seq(
    "com.eed3si9n" %% "treehugger" % "0.2.1" withSources(),
    "org.scala-lang" % "scala-compiler" % "2.9.2" withSources(),
    "org.scalatest" %% "scalatest" % "2.0.M4" % "test" withSources(),
    "org.scalaz" %% "scalaz-core" % "6.0.3" withSources(),
    "com.twitter" % "util-eval" % "5.3.13" withSources(),
    "org.apache.commons" % "commons-lang3" % "3.0" withSources())

