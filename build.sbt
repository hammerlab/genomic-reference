organization := "org.hammerlab.genomics"
name := "reference"
version := "1.0.2-SNAPSHOT"

// This branch only builds against Scala 2.11.x.

scala211Only

deps ++= Seq(
  libs.value('genomic_utils),
  libs.value('iterators)
)

addSparkDeps
publishTestJar
