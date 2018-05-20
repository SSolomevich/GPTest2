package ACMP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Task_010 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();

        String[] s = str.split(" ");

        long a = Long.parseLong(s[0]);
        long b = Long.parseLong(s[1]);
        long c = Long.parseLong(s[2]);
        long d = Long.parseLong(s[3]);

        ArrayList<Integer> listAnswer = new ArrayList<>();

        for (int i=-100; i<101;i++){
            if (a*i*i*i+ b*i*i+c*i+d==0){
                listAnswer.add(i);
            }
        }
        for (int i=0;i<listAnswer.size();i++){
            System.out.print(listAnswer.get(i)+" ");
        }
    }
}
