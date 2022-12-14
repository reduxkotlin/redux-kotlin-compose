import org.jetbrains.kotlin.konan.target.HostManager
import util.Git

plugins {
  id("convention.common")
  id("org.jetbrains.dokka")
  `maven-publish`
  signing
}

tasks {
  register<Jar>("javadocJar") {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.get().outputDirectory)
  }
  withType<Jar> {
    manifest {
      attributes += sortedMapOf(
        "Built-By" to System.getProperty("user.name"),
        "Build-Jdk" to System.getProperty("java.version"),
        "Implementation-Version" to project.version,
        "Created-By" to "${GradleVersion.current()}",
        "Created-From" to "${Git.headCommitHash}"
      )
    }
  }
  val cleanMavenLocal by registering {
    group = "build"
    doLast {
      val m2Repo = file("${System.getProperty("user.home")}/.m2/repository")
      val groupRepo = file("$m2Repo/${project.group.toString().replace(".", "/")}")
      publishing.publications.filterIsInstance<MavenPublication>().forEach {
        groupRepo.resolve(it.artifactId).deleteRecursively()
      }
    }
  }
  named("clean") {
    dependsOn(cleanMavenLocal)
  }
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  if (signingKey != null) {
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
  }
}

val isMainHost = HostManager.simpleOsName().equals("${project.properties["project.mainOS"]}", true)

publishing {
  publications {
    val ghOwnerId: String = project.properties["gh.owner.id"]!!.toString()
    val ghOwnerName: String = project.properties["gh.owner.name"]!!.toString()
    val ghOwnerOrganization: String = project.properties["gh.owner.organization"]!!.toString()
    val ghOwnerOrganizationUrl: String = project.properties["gh.owner.organization.url"]!!.toString()
    repositories {
      maven("https://maven.pkg.github.com/$ghOwnerId/${rootProject.name}") {
        name = "GitHub"
        credentials {
          username = System.getenv("GH_USERNAME")
          password = System.getenv("GH_PASSWORD")
        }
      }
    }
    withType<MavenPublication> {
      artifact(tasks["javadocJar"])
      pom {
        name by project.name
        url by "https://github.com/$ghOwnerId/${rootProject.name}"
        description by provider { project.description }

        licenses {
          license {
            name by "The Apache License, Version 2.0"
            url by "https://www.apache.org/licenses/LICENSE-2.0.txt"
          }
        }

        developers {
          developer {
            id by ghOwnerId
            name by ghOwnerName
            organization by ghOwnerOrganization
            organizationUrl by ghOwnerOrganizationUrl
          }
        }

        scm {
          connection by "scm:git:git@github.com:$ghOwnerId/${rootProject.name}.git"
          url by "https://github.com/$ghOwnerId/${rootProject.name}"
          tag by Git.headCommitHash
        }
      }
    }
  }
}
