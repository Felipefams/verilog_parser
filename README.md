# verilog_parser
### Parse your logical expressions to executable verilog code! 

The input must be given in proper verilog format:
- & (AND)
- | (OR)
- ~ (NOT)

so, the following input
>a & b

should produce the following output:
>
    a    b =    wire_a 
    x    x =    x
    0    0 =    0
    0    1 =    0
    1    1 =    1
    1    0 =    0
>
---
## Compiling and building
#### to compile the java file
beware that the [package entities](https://github.com/Felipefams/verilog_parser/tree/main/main) and the `logicalExpressionParser.java` **must** be in the same folder <br>
that being said, the main file is compiled as any normal java program:
> javac logicalExpressionParser.java

and executed using:
>java logicalExpressionParser
#### to compile the verilog file

this project was tested using [Icarus Verilog](http://iverilog.icarus.com) compiler. and, in case you are also using icarus, it should be compiled as following:

> iverilog file.v -o file.vvp

and executed as:
> vvp file.vpp

or
>./file.vvp
