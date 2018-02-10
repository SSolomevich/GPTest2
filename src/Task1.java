import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Sergey Solomevich on 10.02.2018.
 */
public class Task1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String firstStr = reader.readLine(); // считывание первой строки "Параметры программы: ..."
        reader.readLine();                   // считывание второй строки "Строки:"
//        Первая строка начинается словом "Параметр" или "Параметры",
//        проверяем, если "Параметр" - создаем подстроку, начиная с 20 символа
//        если "Параметры" - c 21 символа
        if (firstStr.substring(8,9).equals("ы")){
            firstStr=firstStr.substring(21);
        }
        else {
            firstStr = firstStr.substring(20);
        }
        String[] words = firstStr.split(" "); //      создаем массив параметров
        ArrayList<String> list = new ArrayList<>(); // лист для хранения строк, которые являются решением
        String str="";

//если один параметр
        if (words.length == 1) {
//     проверка  является ли строка регулярным выражением
//     НО единственными случаями, когда  будет возвращено false,
//      являются строки, которые не являются допустимыми регулярными выражениями
            boolean isRegex;
            try {
                Pattern.compile(firstStr);
                isRegex = true;
            } catch (PatternSyntaxException e) {
                isRegex = false;
            }
//     если недопутимое выражение, то обрабатываем, как обычную строку
            if (!isRegex) {
                while (true) {
                    str = reader.readLine();  //считываем строки
                    if (str.isEmpty()) break; // если пустая строка - стоп
 //     все строки заканчиваются кавычками, которые необходимо убрать,
//      поэтому удаляю последний символ
                    str=str.substring(0,str.length()-1);
                    String[] strArray = str.split(" "); // массив слов из строки
                    for (int i = 0; i < strArray.length; i++) {
//        если одно из слов соответствует параметру программы,
//        добавляем его в лист и переходим к следующей строке
                        if (firstStr.equals(strArray[i])) {
                            list.add(str);
                            break;
                        }
                    }

                }
            }
 //если регулярное выражение
            else {
                while (true) {
                    str = reader.readLine();
                    if (str.isEmpty()) break;
                    str= str.substring(0,str.length()-1);
                    String[] strArray = str.split(" ");
                    for (int i = 0; i < strArray.length; i++) {
//              вызываем метод для проверки совпадения слов с регулярным выражением,
//              если совпадает, добавляем строку в лист, переходим к следующей
                        if (test(strArray[i],firstStr)) {
                            list.add(str);
                            break;
                        }
                    }
                }
            }
        }
// если параметров несколько, делаем всё тоже самое, но каждый параметр сравниваем отдельно с каждым словом из каждой строки
        else {
            while (true) {
                str = reader.readLine();
                if (str.isEmpty()) break;
                str=str.substring(0,str.length()-1);
                String[] strArray = str.split(" ");
                for (int j = 0; j < strArray.length; j++) {
                    for (int i = 0; i < words.length; i++) {
                        if (words[i].equals(strArray[j])) {
                            list.add(str);
                            j++;
                            break;
                        }
                    }
                }
            }
        }
// выводим ответ - лист со строками
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        reader.close();
    }
// метод для проверки совпадения слова и регулярного выражения
    public static boolean test(String testString, String firstStr){
        Pattern p = Pattern.compile(firstStr);
        Matcher m = p.matcher(testString);
        return m.matches();
    }
}