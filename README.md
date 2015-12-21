SoyLatteArt
===

SoyLatteArt is [sbt](https://github.com/sbt/sbt) command for monitoring JavaVM.
SoyLatteArt is Scala implementation of [LatteArt](https://github.com/tkajita/latteart) by [tkajita](https://github.com/tkajita).

Requirements
------------

* [sbt](https://github.com/sbt/sbt) 0.13.x

## Installation

### Using Plugin on GitHub

Add lines to your `project/plugins.sbt`

```scala
lazy val root = project.in(file(".")).dependsOn(githubRepo)

lazy val githubRepo = uri("git://github.com/modalsoul/SoyLatteArt.git")

addSbtPlugin("jp.modal.soul" % "soylatteart" % "0.1-SNAPSHOT")
```

### Using Plugin on local ivy.

Clone this Repo and compile, publishLocal.

```bash
git clone git@github.com:modalsoul/SoyLatteArt.git

sbt compile publishLocal
```

Add lines to your `project/plugins.sbt`

```scala
addSbtPlugin("jp.modal.soul" % "soylatteart" % "0.1-SNAPSHOT")
```


## Usage

```bash
sbt
> latteart <pid>
```

### Options

| Option        |               |
| ------------- | ------------- |
| -h            | show help.    |
| -m            | show all MBean ObjectName |
| -a            | show all available attributes      |
| -c            | path to configuration file. You can use local file or http(s).|
| -i            | monitoring interval(second).|

## License

Copyright (C) 2015 Masatoshi Imae([@modal_soul](http://twitter.com/modal_soul))

Distributed under the MIT License.