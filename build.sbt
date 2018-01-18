group("org.hammerlab.genomics")
name := "reference"
r"1.4.0"
github.repo("genomic-reference")

enableMacroParadise

dep(
  genomic_utils % "1.3.1",
      iterators % "2.0.0"
)

addSparkDeps
publishTestJar
