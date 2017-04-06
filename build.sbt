organization := "org.hammerlab.genomics"
name := "reference"
version := "1.2.4-SNAPSHOT"

enableMacroParadise

deps ++= Seq(
  libs.value('genomic_utils),
  libs.value('iterators)
)

addSparkDeps
publishTestJar
