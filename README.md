# gradle-auto-version
Gradle plugin to read the projects version number from `version.gradle` file. 
Also provides tasks to increment versions directly in the file. 


## Usage
Include the plugin in your `build.gradle` like this:

    buildscript {
      repositories {
        maven {
          url "https://plugins.gradle.org/m2/"
        }
      }
      dependencies {
        classpath "gradle.plugin.com.github.mazzeb:auto-version:0.2.0"
      }
    }
    
    apply plugin: "com.github.mazzeb.auto-version"

Now remove the version from your build gradle and create a `version.json` file:

    {
        "major": 1,
        "minor": 3,
        "patch": 0,
        "label": "snapshot"
    }

When applied, these information are used to set the projects version. To avoid confusion you get an error if 
the version is also specified in `build.gradle`.

The plugin also provides the following additional tasks:

* `nextMajor` - increment major release and set minor and patch to 0.
* `nextMinor` - increment minor and set patch to 0
* `nextPatch` - increment patch 

