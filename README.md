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

Heroku and Render are supported. The Environment Variables are supplied in the relevant Config Files.

### How To Test

PostMan Collection: [JSON Format](https://www.getpostman.com/collections/6f8d705afefeb67c6aa8).

Swagger UI is Enabled: [Localhost](http://localhost:8080/swagger-ui/index.html#/).
