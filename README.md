# verilog_parser
### Parse your logical expressions to executable verilog code! 

The input must be given in proper verilog format:
- & (AND)
- | (OR)
- ~ (NOT)

so, the following input
>a & b

should produce the following output:
>    a &emsp; b = &nbsp; wire_a 
>    x &emsp; x = &nbsp;  x
>    0 &emsp; 0 = &nbsp;  0
>    0 &emsp; 1 = &nbsp;  0
>    1 &emsp; 1 = &nbsp;  1
>    1 &emsp; 0 = &nbsp;  0
---
## Compiling and building
#### to compile the java file
beware that the [package entities](https://github.com/Felipefams/verilog_parser/tree/main/main) and `logicalExpressionParser.java` **must** be in the same folder <br>
that being said, the main file is compiled as any normal java program:
> javac logicalExpressionParser.java

and executed using:
>java logicalExpressionParser
#### to compile the verilog file

this project was tested using [Icarus Verilog](http://iverilog.icarus.com) compiler. and we compile as following:

> iverilog file.v -o file.vvp

and executed as:
> vvp file.vpp

or
>./file.vvp
---
