group("org.hammerlab.genomics")
name := "reference"
github.repo("genomic-reference")

enableMacroParadise

val conversions = hammerlab("macros", "conversions")

dep(
     conversions % "1.0.0" tests,
  genomics.utils % "1.3.1",
       iterators % "2.1.0"
)

addSparkDeps
publishTestJar
