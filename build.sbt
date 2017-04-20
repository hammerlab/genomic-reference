organization := "org.hammerlab.genomics"
name := "reference"
version := "1.3.0-SNAPSHOT"

enableMacroParadise

deps ++= Seq(
  libs.value('genomic_utils),
  libs.value('iterators)
)

addSparkDeps
publishTestJar
