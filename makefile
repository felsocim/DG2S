JC = javac -d ./out -classpath ./src:./src/libs/cli.jar

all: producers consumers manager

producers:
	$(JC) ./src/Producers/*.java

consumers:
	$(JC) ./src/Consumers/*.java

manager:
	$(JC) ./src/Manager/*.java

clean:
	rm -f -r ./out/*
	rm -f -r *.drg

reinit:
	rm -f -r *.drg
