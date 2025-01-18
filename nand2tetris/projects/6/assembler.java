import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class assembler {
    private HashMap<String, Integer> symbolTable;
    private Map<String, String> compMap = new HashMap<>();
    private Map<String, String> jumpMap = new HashMap<>();
    private int symbolCounter = 0;
    private int lineCounter = 1;
    private ArrayList<String> bitInstruction;

    public assembler(String[] path) {
        symbolTable = preDefinedSymbols();
        computationInit();
        jumpInit();
        asmScanner(path[0]);
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

    public void computationInit() {
        compMap.put("0", "0101010");
        compMap.put("1", "0111111");
        compMap.put("-1", "0111010");
        compMap.put("D", "0001100");
        compMap.put("A", "0110000");
        compMap.put("!D", "0001101");
        compMap.put("!A", "0110001");
        compMap.put("-D", "0001111");
        compMap.put("-A", "0110011");
        compMap.put("D+1", "0011111");
        compMap.put("A+1", "0110111");
        compMap.put("D-1", "0001110");
        compMap.put("A-1", "0110010");
        compMap.put("D+A", "0000010");
        compMap.put("D-A", "0010011");
        compMap.put("A-D", "0000111");
        compMap.put("D&A", "0000000");
        compMap.put("D|A", "0010101");
        compMap.put("M", "1110000");
        compMap.put("!M", "1110001");
        compMap.put("-M", "1110011");
        compMap.put("M+1", "1110111");
        compMap.put("M-1", "1110010");
        compMap.put("D+M", "1000010");
        compMap.put("D-M", "1010011");
        compMap.put("M-D", "1000111");
        compMap.put("D&M", "1000000");
        compMap.put("D|M", "1010101");
    }

    public void jumpInit() {
        jumpMap.put(null, "000");
        jumpMap.put("JGT", "001");
        jumpMap.put("JEQ", "010");
        jumpMap.put("JGE", "011");
        jumpMap.put("JLT", "100");
        jumpMap.put("JNE", "101");
        jumpMap.put("JLE", "110");
        jumpMap.put("JMP", "111");
    }

    public void asmScanner(String pathString) {
        String asmInstruction = "";
        try {
            File file = new File(pathString); //TO-DO FIND RIGHT FILE
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                asmInstruction = scanner.next();
                if (asmInstruction.charAt(0) == '@' || asmInstruction.charAt(0) == '(' ) { //( Will cause problems labels must be set to the following line i think
                    aInstruction(asmInstruction);
                } else {
                    cInstruction(asmInstruction);
                }
                lineCounter++;
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
            symbolTable.put(symbol, lineCounter);
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
        String binary = "111";
        String dest = "";
        String comp = "";
        String jump = "";

        if(currentInstruction.indexOf('=') != -1) {
            String destination = currentInstruction.substring(0, currentInstruction.indexOf('='));
            dest = destConversion(destination);

            String computation = currentInstruction.substring(currentInstruction.indexOf('=') + 1); //if issues arrise look into debugging here
            comp = compConversion(computation);

            jump = jumpConversion(null);

        } else {
            dest = destConversion(null);
        }
        
        if(currentInstruction.indexOf(';') != -1) {
            String computation = currentInstruction.substring(0, currentInstruction.indexOf(';')); //if issues arrise look into debugging here
            comp = compConversion(computation);

            String jumpString = currentInstruction.substring(currentInstruction.indexOf(';') + 1);
            jump = jumpConversion(jumpString);
        }

        binary = comp + dest + jump;
        bitInstruction.add(binary);

    }

    public String compConversion (String computation) {
        return compMap.get(computation);
    }

    public String jumpConversion (String jump) {
        return jumpMap.get(jump);
    }

    public String destConversion(String destination) {
        String dest = "";

        switch (destination) {
            case "M":
                dest = "001";
                break;
            
            case "D":
                dest = "010";
                break;
            
            case "MD":
                dest = "011";
                break;
            
            case "A":
                dest = "100";
                break;
            
            case "AM":
                dest = "101";
                break;
            
            case "AD":
                dest = "110";
                break;

            case "AMD":
                dest = "111";
                break;
        
            case null:
                dest = "000";
                break;
            
            default:
            throw new IllegalArgumentException("Invalid destination: " + destination);
        }

        return dest;
    }

    public void symbolSorter(String symbol) {
        int startingRegister = 16 + symbolCounter;
        symbolTable.put(symbol, startingRegister);
        symbolCounter++;
    }

    public static void main (String[] args) {

    }
}