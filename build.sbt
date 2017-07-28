organization := "org.hammerlab.genomics"
name := "reference"
version := "1.4.0-SNAPSHOT"

enableMacroParadise

deps ++= Seq(
  genomic_utils % "1.3.0-SNAPSHOT",
  iterators % "1.3.0-SNAPSHOT"
)

addSparkDeps
publishTestJar
