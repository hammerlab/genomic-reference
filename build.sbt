organization := "org.hammerlab.genomics"
name := "reference"
version := "1.4.0"

enableMacroParadise

deps ++= Seq(
  genomic_utils % "1.3.1",
  iterators % "1.3.0"
)

addSparkDeps
publishTestJar
