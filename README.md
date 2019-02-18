# DG2S: Distributed Game Strategy Simulator

Adjustable distributed game strategy modeling platform

## About

As a part of our Distributed Systems classes, we had to develop a distributed platform for modeling game strategies by choosing number, type and personalities of gamers and by configuring a certain amount and types of resources to consume by the formers.

The complete description of the subject is available in the [Assignment](Assignment.pdf) file (in French only).

## Implementation

This simulator was coded in Java and uses CORBA for distributed execution.

It allows to configure *n* producers of resources such as marble, wood, etc. These resources can be unlimited (periodically reproduced) or exhaustibles. On the other hand, one can define an arbitrary amount of gamers (consumers) with different kinds of personalities each such as cooperative, individualist, capable of stealing resources from other gamers, etc. The whole set is managed by one main process coordinating actions of every agent.

## Usage

Provided *Makefile* allows one to compile the sources on a Linux machine. To run it on Windows, one will have to write down a custom building script (inspiration can be taken from the Linux version).

To see configuration possibilities of each type of agent, run the corresponding executable with option **-h**.

## Demo

The two Bash scripts present in the root folder can be used to run a demo execution of the platform on a Linux machine. The description of what each of the scripts does is included directly on the beginning of each script.

## Visualization of results

Our platform has no graphical user interface. Besides observing the behavior of each agent on the standard output, the platform is capable of keeping track of all the activities stored to a specific type of JavaScript file in the *analysis* folder. Each JS script generated is provided with a HTML page to visualize the activity log in a graphical manner. The result files are named using the identities given to agents on launch.

## Author

[Marek Felsoci](mailto:marek.felsoci@etu.unistra.fr), student at the [University of Strasbourg](http://www.unistra.fr).

## License

This project is licensed under the terms of the GNU General Public License, version 2. See the [LICENSE](LICENSE) file for full license text.
