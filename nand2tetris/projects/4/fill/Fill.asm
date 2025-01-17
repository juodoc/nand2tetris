// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

    @SCREEN
    D=A
    @addr
    M=D

    @i
    M=0
    @j
    M=0 //Indices for columns and

    @24576
    D=A
    @key
    M=D

(INIT)
    @511
    M=A
    @255
    M=A

(COL)
    @j
    D=M
    @511
    D=M-D

    @LOOP
    D;JGT

    @j
    M=M+1
    @i
    M=0
(ROW)
    @i
    D=M
    @255
    D=D-M

    @COL
    D;JGT

    @addr
    A=M
    M=0

    @i
    M=M+1

    @32
    D=A

    @addr
    M=D+M

    @ROW
    0;JMP

(LOOP)
    //If keyboard == 0 loop until keypress
    @key
    A=M
    D=M

    @INIT
    D;JEQ

    @511
    M=A
    @255
    M=A

(COLUMNS)
    @j
    D=M
    @511
    D=M-D

    @LOOP
    D;JGT

    @j
    M=M+1
    @i
    M=0


(ROWLOOP)
    @i
    D=M
    @255
    D=D-M

    @COLUMNS
    D;JGT

    @addr
    A=M
    M=-1

    @i
    M=M+1

    @32
    D=A

    @addr
    M=D+M

    @ROWLOOP
    0;JMP

    

    


