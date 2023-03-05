package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GenerateAst {
//    PrintWriter printerWriter = new PrintWriter();
    public static void main(String args[]) throws IOException {
//        for(int i = 0;i< args.length;i++){
//            System.out.println("arg" + i + args[i]);
//        }
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir,"Expr", Arrays.asList(
                "Binary     :   Expr left,Token operator,Expr right",
                "Unary      :   Token operator,Expr expression",
                "Literal    :   Object value",
                "Grouping : Expr expression"
        ));
    }
//    base class is Expr a abstract class
//    metaprogramming
    public static void defineAst(String outputDir, String baseName, List<String> types) throws IOException{
        String path  = outputDir + "/" + baseName + ".java";
        //write the code into the file in an UTF-8 format
        PrintWriter printWriter = new PrintWriter(path,"UTF-8");

        printWriter.println("package lox;");
        printWriter.println();
        printWriter.println("import java.util.List;");
        printWriter.println();
        printWriter.println("abstract class " + baseName + "{");

        //define AST visit classes
        defineVisitor(printWriter,baseName,types);
        printWriter.println();

        // declare a abstract accept method
        printWriter.println("  abstract <R> R accept(Visitor<R> visitor);");

        //abstract accept method

        // the context of each type
        // the  AST class
        for(String type : types){
            String className = type.split(":")[0].trim();
            String args = type.split(":")[1].trim();
            defineType(printWriter,className ,baseName ,args);
        }
        printWriter.println("}");
        printWriter.close();
    }

//    define the vistor interface
    private static void defineVisitor(PrintWriter writer,String baseName,List<String>types){
        writer.println("    interface Visitor<R> {");
        for (String type : types){
            String typeName = type.split(":")[0].trim();
            writer.println("    R visit"+ typeName + baseName +"("+typeName + " " + baseName.toLowerCase()+");");
        }
        writer.println("    }");
    }


//    define a type in the [BaseName].java
    private static void defineType(PrintWriter writer,String className,String baseName,String field){
        writer.println("static class "+ className + " extends " + baseName + " {");      // static class Binary extends Expr {
        // constructor and initialize the variables
        writer.println("    "+className + "( " + field + ") {");                        //      Binary(Expr left,Token operator,Expr right) {
        String[] fields = field.split(",");
        for(String arg : fields){
            String name = arg.split(" ")[1];
            writer.println("        this." + name + " = "+ name + ";");                 //          this.left = left;
        }
        writer.println("    }");                                                        //      }
        // final Fields
        for(String finalField : fields){                                                //      final left;
            writer.println("    final " + finalField + ";");
        }

        // override the visit method
        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor) {");
        writer.println("      return visitor.visit" +
                className + baseName + "(this);");
        writer.println("    }");
        writer.println("}");
    }
}
