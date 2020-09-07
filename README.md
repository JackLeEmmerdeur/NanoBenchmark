# NanoBenchmark

A simple benchmarking library for Java.

git clone https://github.com/JackLeEmmerdeur/NanoBenchmark.git

Open the folder with IntelliJ

Open the Maven-tool-window and click install

Create a new Maven-project

Create a the classpath de.myorg (replace myorg with your organization) in the src/main/java folder

Add App.java to that classpath

Use compile in Maven-tool-window

Enjoy your fat-jar in the target-folder

## App.java

```java
package de.myorg.test;

import de.jackleemmerdeur.NanoBenchmark;

public class App {
    public static void main(String args[]) {
        NanoBenchmark n = new NanoBenchmark();
        n.start();
        try {
            Thread.sleep(4000);
        } catch(Exception e) {

        }
        n.stop();
        System.out.println(n.getElapsedMS());
    }
}
```

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>de.myorg</groupId>
	<artifactId>Test</artifactId>
	<version>1.0-SNAPSHOT</version>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
	</properties>
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
				<version>3.2.4</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
							<!--<minimizeJar>true</minimizeJar>-->
							<transformers>
								<transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
									<mainClass>de.myorg.test.App</mainClass>
								</transformer>
							</transformers>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
	<dependencies>
		<dependency>
			<groupId>de.jackleemmerdeur</groupId>
			<artifactId>NanoBenchmark</artifactId>
			<version>0.1</version>
		</dependency>
	</dependencies>
</project>
```