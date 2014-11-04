# Java Interpreter
This project is an interpreter for a minimal form of [Eiffel](http://en.wikipedia.org/wiki/Eiffel_(programming_language)). This minimal form of Eiffel has only 1 data type, integer, 
and the only identifiers are single letters (i.e. 26 possible identifiers since Eiffel is not case sensitive).

The interpreter parses the Eiffel program and builds intermediate data structures. These data structures will are then 
interpreted and execute the program. The tokens in this language are separated by white space. The parsing algorithm 
detects syntactical and semantic errors. The first such error discovered causes an appropriate error message and prints
the error while terminating the program. Run-time errors are also detected and the appropriate error message is printed 
and terminates the program as well.

## How-to-use
To execute the parsing application, use the run-rast shell script accordingly:
sh run-fast <file_name>