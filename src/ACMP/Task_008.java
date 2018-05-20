package ACMP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Task_008 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();

        String[] s = str.split(" ");
        if (Integer.parseInt(s[0])*Integer.parseInt(s[1])==Integer.parseInt(s[2])){
            System.out.println("YES");
        }
        else System.out.println("NO");
    }
}
