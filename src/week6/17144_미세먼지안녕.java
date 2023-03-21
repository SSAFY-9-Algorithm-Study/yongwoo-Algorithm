import java.io.*;
import java.util.*;

public class 17144_미세먼지안녕.java {
	
	static int R; // 행
	static int C; // 열
	static int T; // 시간
	
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	
	static boolean isWall(int x, int y) {
		if (x < 0 || x >= R || y < 0 || y >= C) return true;
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		int[][] arr = new int[R][C];
		int ax = 0; // 공기청정기 위치;
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
			if (arr[i][0] == -1) ax = i;
		}
		
		for (int t = 0; t < T; t++) {
			// 배열 복사해서 확장하고 재적용
			int[][] tmp = new int[R][C];
			for (int i = 0; i < R; i++) {
				tmp[i] = arr[i].clone();
			}
			
			// 미세먼지 확산
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++) {
					if (arr[i][j] == 0 || arr[i][j] == -1) continue; // 미세먼지가 없거나 공기청정기
					
					int div = arr[i][j] / 5; // 확산값
					
					for (int k = 0; k < 4; k++) {
						int nx = i + dx[k];
						int ny = j + dy[k];
						
						if (isWall(nx, ny) || arr[nx][ny] == -1) continue;
						
						tmp[nx][ny] += div;
						tmp[i][j] -= div;
					}
				}
			}
			arr = tmp;
			
			// 공기청정기 작동
			ax--; // 위쪽부터 탐색
			arr[ax-1][0] = 0; // 공기청정기 위쪽은 정화
			for (int i = ax-1; i > 0; i--) {
				// 맨 왼쪽에서 위쪽으로
				arr[i][0] = arr[i-1][0];
			}
			for (int i = 0; i < C-1; i++) {
				// 맨위에서 오른쪽으로
				arr[0][i] = arr[0][i+1];
			}
			for (int i = 0; i < ax; i++) {
				// 맨오른쪽에서 아래로
				arr[i][C-1] = arr[i+1][C-1];
			}
			for (int i = C-1; i > 1; i--) {
				// 공기청정기쪽으로
				arr[ax][i] = arr[ax][i-1];
			}
			arr[ax][1] = 0;
			
			ax++; // 아래쪽 탐색
			arr[ax+1][0] = 0;
			for (int i = ax+1; i < R-1; i++) {
				//  맨 왼쪽에서 아래로
				arr[i][0] = arr[i+1][0];
			}
			for (int i = 0; i < C-1; i++) {
				// 만 아래에서 오른쪽으로
				arr[R-1][i] = arr[R-1][i+1];
			}
			for (int i = R-1; i > ax; i--) {
				// 맨 오른쪽에서 위로
				arr[i][C-1] = arr[i-1][C-1];
			}
			for (int i = C-1; i > 1; i--) {
				// 공기청정기쪽으로
				arr[ax][i] = arr[ax][i-1];
			}
			arr[ax][1] = 0;
		}
		
		// 남아있는 미세먼지 양
		int sum = 2; // 공기청정기 -1이 두개 있으므로 2부터 시작
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				sum += arr[i][j];
			}
		}
		
		System.out.println(sum);
	}
}
