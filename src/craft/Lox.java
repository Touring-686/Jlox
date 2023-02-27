package craft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
//import java.util.src.craft.com.Scanner;

public class Lox {
    static boolean hadError = false;
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes,Charset.defaultCharset()));
        if(hadError) System.exit(65);
    }
    private static void runPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in); //字符流读入
        BufferedReader reader = new BufferedReader(input);
        for(;;){
            System.out.print("> ");
            String line = null;
            line = reader.readLine();
            //deal with the end of file ---- Ctrl-D
            if(line == null) break;
            run(line);
            //reset the flag
            hadError = false;
        }
    }
    private static void run(String source){
        Scan scanner = new Scan (source);
        List<Token> tokens = scanner.scanTokens();
        for(Token t:tokens){
            System.out.println(t.lexeme+ " "+t.literal);
        }

    }



    /** error handling code
     * This error() function and its report() helper tells the user
     * some syntax error occurred on a given line.That is really the
     * bare minimum to be able to claim you even have error reporting. */

    static void error(int line, String message){
        report(line,message);
    }
    private static void report(int line, String message){

    }


    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        }
        // support 2 ways of running code
        else if (args.length == 1) {
            runFile(args[0]);
        } else {
            //run interactively
            runPrompt();
        }
    }

//    private static class scanTokens implements List<src.craft.com.Token> {
//    }
}
