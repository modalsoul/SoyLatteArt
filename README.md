SoyLatteArt
===

SoyLatteArt is [sbt](https://github.com/sbt/sbt) command for monitoring JavaVM using [JMX(Java Management eXtentions)](http://www.oracle.com/technetwork/articles/java/javamanagement-140525.html).
SoyLatteArt is Scala implementation of [LatteArt](https://github.com/tkajita/latteart) by [tkajita](https://github.com/tkajita).

Features
------------
* Collect JavaVM status.
* Output JavaVM status as [LTSV](http://ltsv.org/) format.

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

## Configuration
You can configure monitoring using json.
Default configuration file name is `monitor.json`.

### conf/monitor.json

Sample: Get memory info at five-minute intervals.

```monitor.json
{
  "queries" : [
    // Memory Pool
    {
      "query" : "java.lang:type=MemoryPool,*",
      "attributeNames" : [
        "Usage"
      ]
    }
  ]
}
```

For more details, see [LatteArt configuration](https://github.com/tkajita/latteart#confmonitorjson-ファイル).

## Usage

```bash
sbt
> latteart <pid>
```

`<pid>` is monitoring target JavaVM process ID.
You can find PID using [jps](http://docs.oracle.com/javase/7/docs/technotes/tools/share/jps.html) command.

### Options

| Option        | notes         |
| ------------- | ------------- |
| -h            | show help.    |
| -m            | show all MBean ObjectName |
| -a            | show all available attributes      |
| -c            | path to configuration file. You can use local file or http(s).|
| -i            | monitoring interval(second).|

## License

Copyright (C) 2015 Masatoshi Imae([@modal_soul](http://twitter.com/modal_soul))

Distributed under the MIT License.