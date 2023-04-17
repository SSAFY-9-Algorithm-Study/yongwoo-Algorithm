import java.io.*;
import java.util.*;

public class Main {
	
	static int N;
	static String[][] arr;
	
	static void make(int n, int x, int y) {
		if (n == 3) {
			arr[x][y] = arr[x][y + 1] = arr[x][y + 2] = "*";
			arr[x + 1][y] = "*"; arr[x + 1][y + 1] = " "; arr[x + 1][y + 2] = "*";
			arr[x + 2][y] = arr[x + 2][y + 1] = arr[x + 2][y + 2] = "*";
			return;
		}

		make(n / 3, x, y); make(n / 3, x, y + n / 3); make(n / 3, x, y + n * 2 / 3);
		make(n / 3, x + n / 3, y); make(n / 3, x + n / 3, y + n * 2 / 3);
		make(n / 3, x + n * 2 / 3, y); make(n / 3, x + n * 2 / 3, y + n / 3); make(n / 3, x + n * 2 / 3, y + n * 2 / 3);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		// 입력
		N = Integer.parseInt(br.readLine());
		arr = new String[N][N];
		
		make(N, 0, 0);
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr[i][j] == null) sb.append(" ");
				else sb.append(arr[i][j]);
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
}
