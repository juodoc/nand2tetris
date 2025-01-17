// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

// Each loop calls the inner loop 256 times when multiplied covers the 8,191 bits needs. The last bit is not changed

    @24576
    D=A
    @key
    M=D

(INIT) //initializes the screen variables
    @SCREEN
    D=A
    @addr
    M=D
    @inner
    M=D
    
    @15
    M=A
    @31
    M=A

    @i //Indecis for cols rows and inner addresses per row
    M=0
    @j
    M=0
    @b
    M=0

    @LOOP
    0;JMP

(COL) //Creates a blank screen
    @j
    D=M
    @15
    D=M-D

    @INIT //returns to key listener once done
    D;JLT

    @j
    M=M+1
    @i
    M=0
(ROW) //nested loop
    @i
    D=M
    @15
    D=M-D

    @COL
    D;JLT

    @addr
    D=M
    @inner
    M=D

    @b
    M=0
(INLOOP)
    @b
    D=M
    @31
    D=M-D

    @ROWCONT
    D;JLT

    @inner
    A=M
    M=0

    @inner
    M=M+1

    @b
    M=M+1

    @INLOOP
    0;JMP

(ROWCONT)

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

    @COL //if no key is pressed continues to have the screen blank
    D;JEQ

(COLUMNS) //blacks out the screen if a key is pressed
    @j //col index
    D=M
    @15
    D=M-D

    @INIT //returns to key loop once complete
    D;JLT

    @j
    M=M+1
    @i
    M=0

(ROWLOOP) //nested loop
    @i
    D=M
    @15
    D=M-D

    @COLUMNS
    D;JLT

    @addr
    D=M

    @inner
    M=D

    @b
    M=0
(INNERLOOP)
    @b
    D=M
    @31
    D=M-D

    @ROWCONTINUED
    D;JLT

    @inner
    A=M
    M=-1

    @inner
    M=M+1

    @b
    M=M+1

    @INNERLOOP
    0;JMP

(ROWCONTINUED)
    @i
    M=M+1

    @32
    D=A

    @addr
    M=D+M

    @ROWLOOP
    0;JMP

    

    


