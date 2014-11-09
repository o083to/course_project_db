name := """course_project_db"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final"

libraryDependencies += "org.hibernate" % "hibernate-core" % "4.3.7.Final"
