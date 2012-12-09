import sbt._
import Keys._

object SclemeBuild extends Build {
    lazy val sclemeSbt = Project(
        id = "scleme",
        base = file("."),

        settings = Defaults.defaultSettings ++ Seq(
            publishTo <<= (version) { (v: String) =>
                val repoSuffix = if (v.contains("-SNAPSHOT")) "snapshots" else "releases"
                val resolver = Resolver.file("gh-pages",
                    new File("/Users/kanterov/git/kanterov.github.com/repo", repoSuffix))
                Some(resolver)
            }
        )
    )
}
