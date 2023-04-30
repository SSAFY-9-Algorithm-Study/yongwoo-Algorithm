import java.io.*;
import java.util.*;

class Main {

    static int R; // 행
    static int C; // 열
    static int[][] arr; // 1: 종수, 2: 미친 아두이노
    static String move; // 종수의 이동 방향

    static int jx; // 종수의 위치
    static int jy;
    static int moveCnt; // 총 이동횟수

    static int[] dx = { 0, 1, 1, 1, 0, 0, 0, -1, -1, -1 };
    static int[] dy = { 0, -1, 0, 1, -1, 0, 1, -1, 0, 1 };


    static void start() {
        for (moveCnt = 0; moveCnt < move.length(); moveCnt++) {
            // 다음 위치 이동 배열
            int[][] moveArr = new int[R][C];

            // 종수 이동
            int dir = move.charAt(moveCnt) - '0';
            jx += dx[dir];
            jy += dy[dir];
            if (arr[jx][jy] == 2) return; // 미친 아두이노 만나면 종료
            moveArr[jx][jy] = 1;
            
            // 이동 전 배열에서 미친 아두이노 찾아서 이동 후 배열에 이동
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (arr[i][j] != 2) continue;

                    // 미친 아두이노 위치
                    int mx = i;
                    int my = j;

                    // 거리가 가까우려면 (미.아 위치 - 종수 위치)가 +면 --, -면 ++
                    if (mx > jx) mx--;
                    else if (mx < jx) mx++;
                    if (my > jy) my--;
                    else if (my < jy) my++;
                    
                    if (moveArr[mx][my] == 1) return; // 종수 만나면 종료
                    else if (moveArr[mx][my] >= 2) moveArr[mx][my] = 3; // 다른 미친 아두이노 만나면 터짐, 3으로 처리해 뒀다가 같은 단계에서 뒤에 또 만나면 계속 터짐처리
                    else moveArr[mx][my] = 2;
                }
            }

            arr = moveArr;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        
        arr = new int[R][C];
        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                char input = line.charAt(j);
                if (input == '.') continue;
                else if (input == 'I') {
                    arr[i][j] = 1;
                    jx = i; jy = j;
                }
                else if (input == 'R') arr[i][j] = 2;
            }       
        }

        move = br.readLine();
        // 입력 끝

        start();

        if (moveCnt < move.length()) {
            sb.append("kraj ").append(moveCnt + 1);
        }
        else {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (arr[i][j] == 1) sb.append("I");
                    else if (arr[i][j] == 2) sb.append("R");
                    else sb.append("."); // 터진 경우에 입력값이 3이므로 ==0으로 처리하면 오류
                }
                sb.append("\n");
            }
        }

        System.out.println(sb);
    }
}
