name := "spark01-twitter"

version := "1.0"

scalaVersion := "2.11.8"
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.2"
//libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.1"
libraryDependencies += "log4j" % "log4j" % "1.2.17"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.5.2"
libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.6.1"
//libraryDependencies += "org.postgresql" % "postgresql" % "42.1.1"
libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"
