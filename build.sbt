import play.PlayJava

name := """course_project_db"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaCore,
  javaJdbc,
  javaJpa,
  cache,
  javaWs
)

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"

libraryDependencies += "org.hibernate" % "hibernate-core" % "3.6.9.Final"
