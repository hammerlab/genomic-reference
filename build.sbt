group("org.hammerlab.genomics")
name := "reference"
v"1.4.3"

enableMacroParadise

val conversions = hammerlab("macros", "conversions")

dep(
     conversions % "1.0.0" tests,
  genomics.utils % "1.3.1",
       iterators % "2.1.0"
)

addSparkDeps
publishTestJar
github.repo("genomic-reference")
