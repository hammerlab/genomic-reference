organization := "org.hammerlab.genomics"
name := "reference"
version := "1.1.1-SNAPSHOT"

// This branch builds against Scala 2.10.x only.

scala210Only

deps ++= Seq(
  libs.value('genomic_utils),
  libs.value('iterators)
)

addSparkDeps
publishTestJar
