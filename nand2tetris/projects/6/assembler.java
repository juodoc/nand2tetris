import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class assembler {
    private HashMap<String, Integer> symbolTable;
    private int symbolCounter = 0;
    private ArrayList<String> bitInstruction;

    public assembler() {
        symbolTable = preDefinedSymbols();
    }

    public HashMap<String, Integer> preDefinedSymbols() {
        HashMap<String, Integer> preDefinedTable = new HashMap<String, Integer>();

        for(int i = 0; i < 16; i ++){
            preDefinedTable.put("R" + i , i); // provides R0... R(i)
        }

        preDefinedTable.put("SCREEN" , 16384);
        preDefinedTable.put("KBD" , 24576);
        preDefinedTable.put("SP" , 0);
        preDefinedTable.put("LCL" , 1);
        preDefinedTable.put("ARG" , 2);
        preDefinedTable.put("THIS" , 3);
        preDefinedTable.put("THAT" , 4);
        
        return preDefinedTable;
    }

    public void asmScanner() {
        String asmInstruction = "";
        try {
            File file = new File("xx/xx/placeholder.asm");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                asmInstruction = scanner.nextLine();
                if (asmInstruction.charAt(0) == '@' || asmInstruction.charAt(0) == '(' ) { //( Will cause problems labels must be set to the following line i think
                    aInstruction(asmInstruction);
                } else {
                    cInstruction(asmInstruction);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }


    }

    public void aInstruction(String currentInstruction) {
        String symbol = "";
        if(currentInstruction.charAt(0) == '('){
            symbol = currentInstruction.substring(1, currentInstruction.length() - 2);
        } else {
            symbol = currentInstruction.substring(1);
        }
        
        if(isNumeric(symbol)){
            bitInstruction.add(decimalToBinary(symbol));
        } else if(!symbolTable.containsKey(symbol)) {
            symbolSorter(symbol);
            bitInstruction.add(decimalToBinary(symbolTable.get(symbol) + ""));
        } else {
            bitInstruction.add(decimalToBinary(symbolTable.get(symbol) + ""));
        }
    }

    public String decimalToBinary(String decimal) {
        int dec = Integer.parseInt(decimal);
        String binary = "0";

        while (dec != 0) {
            int remainder = dec % 2;

            binary = binary + remainder;

            dec = dec/2;
        }

        return binary;
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public void cInstruction(String currentInstruction) {

    }

    public void symbolSorter(String symbol) {
        int startingRegister = 16 + symbolCounter;
        symbolTable.put(symbol, startingRegister);
        symbolCounter++;
    }

    public static void main (String[] args) {

    }
}