package lox;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class ScanningTest {
    private static final String CHARSET_NAME = "UTF-8";

    private static final String FILEDIR = "E:\\cs\\cs143\\jlox\\src\\input\\";
//    private static Scanner scanner;

    public StringBuffer InputFromFile(String name){
        try {
            String url = FILEDIR + name + ".txt";
            File file = new File(url);
            if(file.exists()){
                FileReader fr =new FileReader(file);   //reads the file
                BufferedReader br= new BufferedReader(fr);  //creates a buffering character input stream
                StringBuffer sb= new StringBuffer();
            //readline
                String line = "";
                while((line = br.readLine())!=null){
                    sb.append(line);
                    sb.append("\n");
                }
                fr.close();
                return sb;
            }
            return null;
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open"+name,ioe);
        }

    }
    @Test
    public void testAlpha(){
        StringBuffer sb = InputFromFile("inputStringTest");
        String source = sb.toString();
        assert(source != null);
        Scan scan = new Scan(source);
        scan.scanTokens();
        List<Token> DigitTokens = scan.tokenArray();
        Assert.assertEquals("STRING",scan.tokenType(DigitTokens.get(0)));
        Assert.assertEquals("STRING",scan.tokenType(DigitTokens.get(1)));
        Assert.assertEquals("AND",scan.tokenType(DigitTokens.get(2)));
        Assert.assertEquals("STRING",scan.tokenType(DigitTokens.get(3)));
        Assert.assertEquals("CLASS",scan.tokenType(DigitTokens.get(4)));
        Assert.assertEquals("FOR",scan.tokenType(DigitTokens.get(5)));
        Assert.assertEquals("FUN",scan.tokenType(DigitTokens.get(6)));
        Assert.assertEquals("IF",scan.tokenType(DigitTokens.get(7)));
        Assert.assertEquals("STRING",scan.tokenType(DigitTokens.get(8)));
        Assert.assertEquals("PRINT",scan.tokenType(DigitTokens.get(9)));
        Assert.assertEquals("THIS",scan.tokenType(DigitTokens.get(10)));
        Assert.assertEquals("TRUE",scan.tokenType(DigitTokens.get(11)));
        Assert.assertEquals("STRING",scan.tokenType(DigitTokens.get(12)));
        Assert.assertEquals("VAR",scan.tokenType(DigitTokens.get(13)));
        Assert.assertEquals("WHILE",scan.tokenType(DigitTokens.get(14)));
        Assert.assertEquals("ELSE",scan.tokenType(DigitTokens.get(15)));
        Assert.assertEquals("NIL",scan.tokenType(DigitTokens.get(16)));
        Assert.assertEquals("OR",scan.tokenType(DigitTokens.get(17)));
        Assert.assertEquals("RETURN",scan.tokenType(DigitTokens.get(18)));
        Assert.assertEquals("IDENTIFIER",scan.tokenType(DigitTokens.get(19)));
        Assert.assertEquals("IDENTIFIER",scan.tokenType(DigitTokens.get(20)));
        Assert.assertEquals("IDENTIFIER",scan.tokenType(DigitTokens.get(21)));
        Assert.assertEquals("SUPER",scan.tokenType(DigitTokens.get(22)));
        Assert.assertEquals("EOF",scan.tokenType(DigitTokens.get(23)));
    }
    @Test
    public void testDigit(){
        StringBuffer sb = InputFromFile("inputDigitTest");
        String source = sb.toString();
        assert(source != null);
        Scan scan = new Scan(source);
        scan.scanTokens();
        List<Token> DigitTokens = scan.tokenArray();
        Assert.assertEquals("NUMBER",scan.tokenType(DigitTokens.get(0)));
        Assert.assertEquals("NUMBER",scan.tokenType(DigitTokens.get(1)));
        Assert.assertEquals("DOT",scan.tokenType(DigitTokens.get(2)));
        Assert.assertEquals("NUMBER",scan.tokenType(DigitTokens.get(3)));
        Assert.assertEquals("NUMBER",scan.tokenType(DigitTokens.get(4)));
        Assert.assertEquals("GREATER",scan.tokenType(DigitTokens.get(5)));
        Assert.assertEquals("NUMBER",scan.tokenType(DigitTokens.get(6)));
        Assert.assertEquals("IDENTIFIER",scan.tokenType(DigitTokens.get(7)));
        Assert.assertEquals("NUMBER",scan.tokenType(DigitTokens.get(8)));
        Assert.assertEquals("EOF",scan.tokenType(DigitTokens.get(9)));

        scan.printToken();
    }

    @Test
    public void testOperators(){
        StringBuffer sb = InputFromFile("inputScanOper");
        String source = sb.toString();
        assert(source != null);
        Scan scan = new Scan(source);
        scan.scanTokens();
        List<Token> tokens = scan.tokenArray();
        Assert.assertEquals("PLUS",scan.tokenType(tokens.get(0)));
        scan.printToken();
    }

}
