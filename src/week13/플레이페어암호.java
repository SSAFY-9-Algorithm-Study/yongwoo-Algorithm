import java.io.*;
import java.util.*;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder res = new StringBuilder();

        StringBuilder input = new StringBuilder(br.readLine());
        String key = br.readLine() + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // 특정 글자에 따른 위치 저장
        Map<Character, Pair> wordPosMap = new HashMap<>();

        // 데이터가 저장될 표
        char[][] arr = new char[5][5];

        // 키 값을 통해 표 만들기
        int keyIdx = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // 등록되지 않은 키 값 찾기
                char k = key.charAt(keyIdx);
                while (wordPosMap.containsKey(k)) k = key.charAt(++keyIdx);

                wordPosMap.put(k, new Pair(i, j));
                if (k == 'I') wordPosMap.put('J', new Pair(i, j));
                else if (k == 'J') wordPosMap.put('I', new Pair(i, j));

                arr[i][j] = k;

                keyIdx++;
            }
        }

        // 암호화하려는 메시지 두 글자씩 나누기
        for (int i = 0; i < input.length() - 1; i += 2) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i + 1);

            // 짝이 아니면 continue
            if (c1 != c2) continue;

            // XX짝이면 사이에 Q넣기, 아니면 X넣기
            if (c1 == 'X' && c2 == 'X') input.insert(i + 1, 'Q');
            else input.insert(i + 1, 'X');
        }
        if (input.length() % 2 == 1) input.append('X'); // 홀수개면 맨 뒤에 X 붙여서 짝 맞추기

        // 두 글자씩 표에서 확인
        for (int i = 0; i < input.length(); i += 2) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i + 1);

            Pair pos1 = wordPosMap.get(c1);
            Pair pos2 = wordPosMap.get(c2);

            if (pos1.x == pos2.x) {
                // 행이 같으면 열 한칸씩 이동
                res.append(arr[pos1.x][(pos1.y + 1) % 5]);
                res.append(arr[pos2.x][(pos2.y + 1) % 5]);
            }
            else if (pos1.y == pos2.y) {
                // 열이 같으면 행 한칸씩 이동
                res.append(arr[(pos1.x + 1) % 5][pos1.y]);
                res.append(arr[(pos2.x + 1) % 5][pos2.y]);
            }
            else {
                // 행, 열 다르면 열 위치 교환
                res.append(arr[pos1.x][pos2.y]);
                res.append(arr[pos2.x][pos1.y]);
            }
        }

        System.out.println(res);
    }
}

class Pair {
    int x;
    int y;
    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
