// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.

    @R2 //Ensures that R2 is initialized to zero
    M=0

    @R1
    D=M

    @ZERO //Checks if input values 1 and 2 are equal to zero, if so output is automatically 0
    D;JEQ

    @R0
    D=M

    @ZERO
    D;JEQ //Checks if input values 1 and 2 are equal to zero, if so output is automatically 0

(LOOP)
    @R0 
    D=M
    @R2
    M=D+M //For each loop the D register adds R0's value to R2

    @R1
    MD=M-1 //For each loop R1 is incremented by one

    @LOOP //Until the value of R1 is less than zero the script will continue to add
    D;JGT

(END)
    @END
    0;JMP

(ZERO)
    @R2 //Makes out put zero
    M=0

    @END //Ends program
    0;JMP


    


    
