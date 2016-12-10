organization := "org.hammerlab.genomics"
name := "reference"
version := "1.0.0"

libraryDependencies ++= Seq(
  libraries.value('kryo)
)

testDeps += libraries.value('test_utils)
