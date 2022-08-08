#!/usr/bin/env bash

# Test Colours
red=`tput setaf 1`
green=`tput setaf 2`
reset=`tput sgr0`

# Ensure that file is being sourced, as ./ won't set the Env Variables outside this script's context.
if [ "$0" = "./setEnvironmentalVariables.sh" ]
then
  echo " ${red}[error]  Usage of this file should be 'source setEnvironmentalVariables.sh'${reset}"
  exit 1
fi

# Set ROADCC_USERNAME for local testing
echo " [info] Setting ROADCC_USERNAME"
export ROADCC_USERNAME='yourusername'
echo " ${green}[info] Set ROADCC_USERNAME to $ROADCC_USERNAME ${reset}"

# Set ROADCC_PASSWORD for local testing
echo " [info] Setting ROADCC_PASSWORD"
export ROADCC_PASSWORD='yourpassword'
echo " ${green}[info] Set ROADCC_PASSWORD to $ROADCC_PASSWORD ${reset}"

# Set ROADCC_LEAGUE_NAME for local testing
echo " [info] Setting ROADCC_LEAGUE_NAME"
export ROADCC_LEAGUE_NAME='yourleaguename'
echo " ${green}[info] Set ROADCC_LEAGUE_NAME to ROADCC_LEAGUE_NAME ${reset}"