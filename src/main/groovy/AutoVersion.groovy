import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoVersion implements Plugin<Project> {

    @Override
    void apply(Project project) {

    }

    private bumpVersionInGradleFile(String theVersion) {
        def myFile = new File('version.gradle')
        def output = ""
        myFile.eachLine { line ->
            output += line.replaceAll('^version\\s*=\\s*.*$', "version='" + theVersion + "'")
            output += "\n"
        }
        def outFile = new File("version.gradle")
        outFile.write(output)
    }

    private String incrementMinorVersion(final String theVersion) {
        versionArray = theVersion.split("\\.")
        major = versionArray[0].toInteger()
        minor = versionArray[1].toInteger() + 1
        patch = 0

        return major + "." + minor + "." + patch;
    }


    private String trimSnapshot(final String theVersion) {
        if (theVersion.endsWith("-SNAPSHOT")) {
            return theVersion.substring(0, theVersion.length()-9)
        } else {
            return theVersion;
        }
    }
}
