ThisBuild / scalaVersion := "2.12.12"

lazy val clickstreamAnalytics = (project in file("."))
  .settings(
    name := "ClickstreamAnalytics",
    libraryDependencies += "org.apache.spark"  %% "spark-core" % "3.0.1",
    libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.12" % "3.0.1",
    libraryDependencies += "org.apache.spark" % "spark-streaming_2.12" % "3.0.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
  )
