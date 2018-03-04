name := "spark01-twitter"

version := "1.0"

scalaVersion := "2.11.8"
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.5.2"
libraryDependencies += "log4j" % "log4j" % "1.2.17"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.5.2"
libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.6.1"

