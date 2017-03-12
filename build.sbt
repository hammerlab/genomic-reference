organization := "org.hammerlab.genomics"
name := "reference"
version := "1.2.2"

// This branch only builds against Scala 2.11.x.

scala211Only

enableMacroParadise

deps ++= Seq(
  libs.value('genomic_utils),
  libs.value('iterators)
)

addSparkDeps
publishTestJar
