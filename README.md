# Fantasy Cycling League Tools

A suite of Spring Boot REST APIs for a Fantasy Cycling League.

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://dashboard.heroku.com/new-app?template=https://github.com/lukegjpotter/FantasyCyclingLeagueTools)

[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://render.com/deploy?repo=https://github.com/lukegjpotter/FantasyCyclingLeagueTools)

## The Goal

Currently, our Fantasy League is a process of manually checking web pages to see who made transfers, who scored what
points, and what people's current teams are. These are then shared into a WhatsApp group in the form of screenshots.
This floods our camera roll.

The goal of this suite of tools is to create a few APIs that can send formatted text, or screenshots, into a WhatsApp
Group at the call of a REST Endpoint.

## Active APIs

* Get Transfers
* Get League Standings Including Today's scores.
* Get Current Teams

## Pending Tasks

There are a horrid amount of Strings inside the Application. These need to be changed to POJOs, or possibly Java 17
Records. This is a pending action.

## Build, Run and Test

### Download "Chrome For Testing" Browser

Since Chrome version 115 (late-July 2023), there is a new Browser, "Chrome for Testing", that the July 2023 releases of
Selenium and WebDriverManager look for.

This can be downloaded via Puppet, or from the Website
[Chrome for Testing Releases](https://googlechromelabs.github.io/chrome-for-testing/).

On MacOS, you should try to open the newly downloaded Chrome for Testing, to ensure that the security will allow it to
be opened. If you are presented with a Security Message, not allowing it to be run, open System Settings -> Privacy and
Security -> Open Anyway. You will also be presented with a message asking you to Sign In to Chromium, which you can
dismiss.

You can set the version of Chrome for Testing that you have downloaded, in the
`src/main/resources/application.properties` file. The `version.chromefortesting` property takes a string.

### Terminal or CLI

You can run this in your Terminal by editing the `setEnvironmentalVariables.sh` or `setEnvironmentalVariables.bat`
files to set the three Environmental Variables to your values.  
Be wary not to commit the updated file to a public Git Repo. Consider adding it to the `.gitignore` file.

As this project uses Spring Boot 3, you need Java 17 to run it.  
This project uses port 8080 by default, so ensure that it's free when you're trying to run it.

Then executing

    source setEnvironmentalVariables.sh
    ./gradlew build bootRun

or with Docker:

    docker build -t fantasy-cycling-tools:latest .
    
    docker run --name fantasy_cycling \
      -p 8080:8080 \
      --env=ROADCC_USERNAME='yourUsername' \
      --env=ROADCC_PASSWORD='yourPassword' \
      --env=ROADCC_LEAGUE_NAME='Your League Name' \
      -d --rm fantasy-cycling-tools:latest

### IDE

You can run this in your IDE by adding the Environment Variables to your Run Configurations.

    IS_FANTASY_CYCLING_TOOLS_ON_DOCKER = false
    ROADCC_USERNAME = yourUsername
    ROADCC_PASSWORD = yourPassword
    ROADCC_LEAGUE_NAME = Your League Name

### Cloud Hosting Service

[comment]: <> (FixMe: Render support is not working.)
Heroku is supported. Render is a Work in Progress. The Environment Variables are supplied in the relevant Config Files.

### How To Test

[comment]: <> (FixMe: JSON format is no longer supported in PostMan, use a standard collection instead.)
PostMan Collection: [JSON Format](https://www.getpostman.com/collections/6f8d705afefeb67c6aa8).

Swagger UI is Enabled: [Localhost](http://localhost:8080/swagger-ui/index.html).
