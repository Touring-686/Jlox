package craft;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public void testDigit(){

    }
    @Test
    public void testAlpha(){

    }

    @Test
    public void testOperators(){
        StringBuffer sb = InputFromFile("inputScanOper");
        String source = sb.toString();
        assert(source != null);
        System.out.println(sb);
    }

}
