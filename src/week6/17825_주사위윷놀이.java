import java.io.*;
import java.util.*;

public class Main {
	
	static int[] dices; // 주사위 입력값
	
	static int max = 0; // 점수 최대값
	
	// 말 위치
	// [i][0]/[i][1] : i번째 말의 board상 위치
	static int[][] pos = new int[4][2];
	
	// 게임판
	static int[][] board = 
		{
				{/* 0 ~ 40, -1까지 짝수만 들어감*/}, 
				{10, 13, 16, 19, 25, 30, 35, 40, -1, -1, -1, -1, -1}, 
				{20, 22, 24, 25, 30, 35, 40, -1, -1, -1, -1, -1},
				{30, 28, 27, 26, 25, 30, 35, 40, -1, -1, -1, -1, -1},
				{40, -1, -1, -1, -1, -1}
		};
	
	// 이동한 말 되돌리기
	static void moveBack(int i, int moveCnt) {
		if (pos[i][1] == 0) {
			pos[i][1] = pos[i][0] * 5;
			pos[i][0] = 0;
		}
		pos[i][1] -= dices[moveCnt];
	}
	
	static boolean isSamePosition(int i, int j) {
		if (pos[j][0] == pos[i][0] && pos[j][1] == pos[i][1]) return true;

		if (pos[j][1] == 0 && pos[i][0] == 0 && pos[i][1] % 5 == 0 && pos[i][1] / 5 == pos[j][0]) return true;
		if (pos[i][1] == 0 && pos[j][0] == 0 && pos[j][1] % 5 == 0 && pos[j][1] / 5 == pos[i][0]) return true;

		if ((pos[i][0] == 1 && pos[j][0] == 3) || (pos[i][0] == 3 && pos[j][0] == 1)) 
			if (pos[i][1] >= 4 && pos[i][1] <= 7 && pos[i][1] == pos[j][1]) return true;

		if (pos[i][0] == 2 && (pos[j][0] == 1 || pos[j][0] == 3)) 
			if (pos[j][1] >= 4 && pos[j][1] <= 7 && pos[i][1]+1 == pos[j][1]) return true;
		if (pos[j][0] == 2 && (pos[i][0] == 1 || pos[i][0] == 3)) 
			if (pos[i][1] >= 4 && pos[i][1] <= 7 && pos[j][1]+1 == pos[i][1]) return true;
		
		if (board[pos[i][0]][pos[i][1]] == 40 && board[pos[j][0]][pos[j][1]] == 40) return true;
		
		return false;
	}
	
	// onBoardCnt: 게임판 상의 말 개수, moveCnt: 게임 턴, point: 점수 합계
	static void move(int onBoardCnt, int moveCnt, int point) {
		if (moveCnt == 10) {
			// 이동 완료
			max = Math.max(point, max);
			return;
		}
		
		// System.out.println("onBoardCnt: " + onBoardCnt + ", movecnt: " + moveCnt);
		// 4개 말 이동
		for (int i = 0; i < 4; i++) {
			// 이미 도착한 말이면 continue
			if (board[pos[i][0]][pos[i][1]] == -1) continue;

			// 파란색 칸이면 이동 방향 변경(0번 인덱스값 변경)
			if (pos[i][0] == 0 && board[pos[i][0]][pos[i][1]] % 10 == 0) {
				pos[i][0] = board[pos[i][0]][pos[i][1]] / 10;
				pos[i][1] = 0;
			}
			
			pos[i][1] += dices[moveCnt];
			
			// for (int j = 0; j < 4; j++) {
			// 	System.out.println("j: " + j + " / " + pos[j][0] + " " + pos[j][1]);
			// }
			
			// 게임판에 올라가지 않은 말이 있으면 올리고 진행, 중복되므로 재귀하지 않음
			if (i == onBoardCnt) {
				// 이동하려는 위치에 다른 말이 있으면 종료
				for (int j = 0; j < onBoardCnt; j++) {
					if (isSamePosition(i, j)) {
						moveBack(i, moveCnt);
						return;
					}
				}
				
				move(onBoardCnt+1, moveCnt+1, point+board[pos[i][0]][pos[i][1]]);
				moveBack(i, moveCnt);
				return;
			}
			
			// 배열 범위 밖으로 벗어나면 도착(-1로 이동)
			if (board[pos[i][0]][pos[i][1]] == -1) {
				move(onBoardCnt, moveCnt+1, point);
				moveBack(i, moveCnt);
				continue;
			}
			
			// 다른 말이 해당 위치에 있으면 이동 불가
			boolean cantGo = false;
			for (int j = 0; j < onBoardCnt; j++) {
				if (j == i) continue;
				
				if (isSamePosition(i, j)) {
					cantGo = true;
					break;
				}
			}
			if (cantGo) {
				moveBack(i, moveCnt);
				continue;
			}
			
			move(onBoardCnt, moveCnt+1, point+board[pos[i][0]][pos[i][1]]);
			moveBack(i, moveCnt);
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		dices = new int[10];
		for (int i = 0; i < 10; i++) {
			dices[i] = Integer.parseInt(st.nextToken());
		}
		
		// 0번째 값 초기화
		board[0] = new int[27];
		for (int i = 0; i <= 20; i++) {
			board[0][i] = i*2;
		}
		board[0][21] = -1; board[0][22] = -1; board[0][23] = -1;
		board[0][24] = -1; board[0][25] = -1; board[0][26] = -1; 
		
		move(0, 0, 0);
		
		System.out.println(max);
	}
}
