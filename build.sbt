organization := "org.hammerlab.genomics"
name := "reference"
version := "1.0.2-SNAPSHOT"

deps ++= Seq(
  libs.value('genomic_utils),
  libs.value('iterators)
)

addSparkDeps
publishTestJar
