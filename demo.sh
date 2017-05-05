#!/bin/sh

USERINPUT=""

xterm -e java -classpath ./src/libs/cli.jar:./src:./out Producers.Producer -i 1 -t wood -c 8 -max 5 -f 1000 -I &
xterm -e java -classpath ./src/libs/cli.jar:./src:./out Producers.Producer -i 2 -t marble -c 8 -max 5 -f 1000 -I &


read -p "Verify the producers are ready before launching Consumers! [ENTER]" USERINPUT

xterm -e java -classpath ./src/libs/cli.jar:./src:./out Consumers.Consumer -i Jean -tw 30 -tm 30 -c &
xterm -e java -classpath ./src/libs/cli.jar:./src:./out Consumers.Consumer -i Roger -tw 30 -tm 30 -I -o &
xterm -e java -classpath ./src/libs/cli.jar:./src:./out Consumers.Consumer -i Berger -tw 10 -tm 15 -c &

read -p "Verify the consumers are ready before launching manager! [ENTER]" USERINPUT 

xterm -e java -classpath ./src/libs/cli.jar:./src:./out Manager.Manager -a -s &
