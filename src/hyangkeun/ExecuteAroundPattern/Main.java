package src.hyangkeun.ExecuteAroundPattern;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        // 한줄 밖에 못 읽는다.
        ExecuteAroundPattern executeAroundPattern = new ExecuteAroundPattern();
        System.out.println(executeAroundPattern.processFile());

        // 개선
        String oneLine = main.processFile((BufferedReader br) -> br.readLine());
        System.out.println(oneLine);

        String twoLine = main.processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(twoLine);

        String threeLine = main.processFile((BufferedReader br) -> br.readLine() + br.readLine() + br.readLine());
        System.out.println(threeLine);
    }

    public String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

}
