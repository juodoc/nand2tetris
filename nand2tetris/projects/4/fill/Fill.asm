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

    @c
    M=511

(COL)

(ROW)

(LOOP)
    //If keyboard == 0 loop until keypress
    @key
    A=M
    D=M

    @LOOP
    D;JEQ

    @r
    M=255

(COLUMNS)
    @j
    D=M
    @c
    D=M-D

    @

    @j
    M=M+1
    @i
    M=0


(ROWLOOP)
    @i
    D=M
    @r
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

    

    


