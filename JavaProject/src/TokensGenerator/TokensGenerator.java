import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class TokensGenerator {
    public static void main(String[] args) {
        String code = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\HP\\OneDrive\\Desktop\\java\\JavaProject\\src\\TokensGenerator\\MyCode.txt"));
              
              if (reader != null) {
              int counter;
              boolean isSpace = false; 

              while ((counter = reader.read()) != -1) {
                char c = (char) counter;
                if (Character.isWhitespace(counter)) {
                  if (!isSpace) {
                    code += " "; 
                    isSpace = true;
                   }
                } else if (c == ';') {
                code += c + "\n"; 
                isSpace = false;
                } else {
                code += c;
                isSpace = false;
            }
        }
        

        reader.close();
    }
        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
        
        List<Token> tokens = TokenService.GetTokens(code);
        TokenService.WriteToOutputFile(tokens,code);
        

        LocalDateTime currentDateTime = LocalDateTime.now();

        System.out.println("Name:-Taha Ahmed Radwan, Stu_ID:- 2201805468");
        System.out.println("Created at:"+currentDateTime);
    }
}


