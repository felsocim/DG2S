#!/bin/sh

USERINPUT=""
RED='\033[1;31m'
NC='\033[0m'

echo "This script will launch DRG demo with one producer of each type and three consumers with different options."
echo "$RED NOTE: The script is composed of three parts. First, it launches the producers. Once you've verified all producers have launched successfully,
 press ENTER to launch consumers. Only, once the consumers have launched successufully you can press ENTER to launch the game manager wich will
 allow you to start the game!$NC"

read -p "Press [ENTER] to start producers." USERINPUT 

xterm -e java -classpath ./src/libs/cli.jar:./src:./out Producers.Producer -i 1 -t wood -c 8 -max 5 -f 1000 -I &
xterm -e java -classpath ./src/libs/cli.jar:./src:./out Producers.Producer -i 2 -t marble -c 8 -max 5 -f 1000 -I &


read -p "Verify the producers are ready before launching Consumers! [ENTER]" USERINPUT

xterm -e java -classpath ./src/libs/cli.jar:./src:./out Consumers.Consumer -i Jean -tw 30 -tm 30 -c &
xterm -e java -classpath ./src/libs/cli.jar:./src:./out Consumers.Consumer -i Roger -tw 30 -tm 30 -I -o &
xterm -e java -classpath ./src/libs/cli.jar:./src:./out Consumers.Consumer -i Berger -tw 30 -tm 30 -c &

read -p "Verify the consumers are ready before launching manager! [ENTER]" USERINPUT 

xterm -e java -classpath ./src/libs/cli.jar:./src:./out Manager.Manager -a -s &
