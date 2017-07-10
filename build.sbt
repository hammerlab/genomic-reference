organization := "org.hammerlab.genomics"
name := "reference"
version := "1.3.1-SNAPSHOT"

enableMacroParadise

deps ++= Seq(
  genomic_utils % "1.2.3",
  iterators % "1.3.0-SNAPSHOT"
)

addSparkDeps
publishTestJar
