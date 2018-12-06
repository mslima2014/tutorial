# tutorial
Spring Boot and Angular using Maven to build

Combining Spring Boot and Angular using Maven can be a challenging task. In this post, we will create a simple web app using Spring Boot and Angular 6 and package them together in a war file.
Creating a Maven Project

First, create a Maven project containing two modules: one for backend and another for front-end.

In my example, there are two Maven modules — tutorial-server (backend) and tutorial-web (frontend) — which are inheriting from the parent module, tutorial-parent.

The project structure is as follows:

The structure of parent POM is as follows:

<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.swathisprasad.tutorial</groupId>

    <artifactId>tutorial-parent</artifactId>

    <version>0.0.1-SNAPSHOT</version>

    <packaging>pom</packaging>

    <parent>

        <groupId>org.springframework.boot</groupId>

        <artifactId>spring-boot-starter-parent</artifactId>

        <version>2.0.4.RELEASE</version>

        <relativePath />

    </parent>

    <modules>

        <module>tutorial-server</module>

        <module>tutorial-web</module>

    </modules>

</project>

Now, configure the POM for tutorial-server as follows:

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>

        <groupId>com.swathisprasad.tutorial</groupId>

        <artifactId>tutorial-parent</artifactId>

        <version>0.0.1-SNAPSHOT</version>

    </parent>

    <artifactId>tutorial-server</artifactId>

    <packaging>war</packaging>

    <dependencies>

        <dependency>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-starter-web</artifactId>

        </dependency>

        <dependency>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-starter-actuator</artifactId>

        </dependency>

        <dependency>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-starter-security</artifactId>

        </dependency>

        <dependency>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-starter-tomcat</artifactId>

            <scope>provided</scope>

        </dependency>

        <dependency>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-starter-test</artifactId>

            <scope>test</scope>

        </dependency>

        <dependency>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-starter-aop</artifactId>

        </dependency>

        <dependency>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-starter-integration</artifactId>

        </dependency>

        <dependency>

            <groupId>com.swathisprasad.tutorial</groupId>

            <artifactId>tutorial-web</artifactId>

            <version>${project.version}</version>

            <scope>runtime</scope>

        </dependency>

    </dependencies>

    <build>

        <plugins>

            <plugin>

                <groupId>org.apache.maven.plugins</groupId>

                <artifactId>maven-failsafe-plugin</artifactId>

            </plugin>

            <plugin>

                <groupId>org.apache.maven.plugins</groupId>

                <artifactId>maven-surefire-plugin</artifactId>

            </plugin>

            <plugin>

                <groupId>org.springframework.boot</groupId>

                <artifactId>spring-boot-maven-plugin</artifactId>

            </plugin>

            <plugin>

                <groupId>org.apache.maven.plugins</groupId>

                <artifactId>maven-war-plugin</artifactId>

                <configuration>

                    <packagingExcludes>WEB-INF/lib/tomcat-*.jar</packagingExcludes>

                    <warName>tutorial-app</warName>

                </configuration>

            </plugin>

        </plugins>

    </build>

</project>

Notice that we have added the dependency for web module tutorial-web and Sprint Boot dependencies. We will come back to the backend POM configuration a bit later.

Let us look at the configuration for tutorial-web module POM.

<?xml version="1.0"?>

<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

    <modelVersion>4.0.0</modelVersion>

    <artifactId>tutorial-web</artifactId>

    <name>tutorial-web</name>

    <parent>

        <groupId>com.swathisprasad.tutorial</groupId>

        <artifactId>tutorial-parent</artifactId>

        <version>0.0.1-SNAPSHOT</version>

    </parent>

    <build>

        <plugins>

            <plugin></plugin>

        </plugins>

    </build>

</project>

We will come back to this configuration a bit later as well.
Creating a Spring Boot Application

In my sample project, I have created Spring Boot application manually. You could create using Spring Spring Initializer as well.

The package structure for Spring Boot application is as follows:

Let's look at the HomeController class as this is going to return a view.

@Controller

@RequestMapping("/home")

public class HomeController {

       @GetMapping

       public String home(Model model) {

              return "forward:/index.html";

       }

}

Notice that the method home accepts a model and returns "index.html", which is the view.
Creating an Angular6 Project Using Angular CLI

If the angular-cli is not already installed, install it using the following command from the terminal.

npm install -g @angular/cli 

Generate an Angular 6 project using the angular-cli from the tutorial-web/src/main/web directory. Navigate to the directory tutorial-web/src/main in the terminal and run the following command:

ng new -skip-git -directory web np-app 

In the above command, we are skipping the git repository as we are not running the command from the root directory. We have specified that the output directory as web and given the application the name np-app.

Add the following configuration to tutorial-web/pom.xml to build an Angular 6 project through Maven.

<plugin>

    <groupId>com.github.eirslett</groupId>

    <artifactId>frontend-maven-plugin</artifactId>

    <version>1.3</version>

    <configuration>

        <nodeVersion>v8.11.3</nodeVersion>

        <npmVersion>6.3.0</npmVersion>

        <workingDirectory>src/main/web/</workingDirectory>

    </configuration>

    <executions>

        <execution>

            <id>install node and npm</id>

            <goals>

                <goal>install-node-and-npm</goal>

            </goals>

        </execution>

        <execution>

            <id>npm install</id>

            <goals>

                <goal>npm</goal>

            </goals>

        </execution>

        <execution>

            <id>npm run build</id>

            <goals>

                <goal>npm</goal>

            </goals>

            <configuration>

                <arguments>run build</arguments>

            </configuration>

        </execution>

        <execution>

            <id>prod</id>

            <goals>

                <goal>npm</goal>

            </goals>

            <configuration>

                <arguments>run-script build</arguments>

            </configuration>

            <phase>generate-resources</phase>

        </execution>

    </executions>

</plugin>

Notice that we are using front-maven-plugin to build the Angular 6 project.

We have specified the node and npm versions and the working directory in the configuration section. In the first execution section, we are telling Maven to install npm and node. This will install within the project which gives us many advantages.

In the next execution sections, we tell Maven to run the 'npm install' and 'npm run build' commands. By default, angular-cli will write the files in the src\main\web\dist directory.
Configuring Backend Maven POM to Include Angular 6 Resources

Edit tutorial-server/pom.xml and add the following plugin section:

<plugin>

    <artifactId>maven-resources-plugin</artifactId>

    <executions>

        <execution>

            <id>copy-resources</id>

            <phase>validate</phase>

            <goals>

                <goal>copy-resources</goal>

            </goals>

            <configuration>

                <outputDirectory>${project.build.directory}/classes/resources/</outputDirectory>

                <resources>

                    <resource>

                        <directory>${project.parent.basedir}/tutorial-web/src/main/web/dist/np-app/</directory>

                    </resource>

                </resources>

            </configuration>

        </execution>

    </executions>

</plugin>

Here, we are copying the resources from tutorial-web/ src/main/web/dist/np-app to the tutorial-server/target/classes/resources directory. This will make sure to include t utorial-app/WEB-INF/classes/resources when the war file is build.
Run the Application

Run  mvn clean install from the project root directory. This will generate a war file in the tutorial-server/target directory. It can be deployed to the Tomcat server and the application can be viewed.

To run the Spring Boot application using Maven, run the following command from the tutorial-server directory.

mvn spring-boot:run 

Once the application has started, we should be able to see welcome page using http://localhost:8090/.
