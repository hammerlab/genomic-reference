group("org.hammerlab.genomics")
name := "reference"
github.repo("genomic-reference")

enableMacroParadise

dep(
  genomic_utils % "1.3.1",
      iterators % "2.0.0"
)

addSparkDeps
publishTestJar
