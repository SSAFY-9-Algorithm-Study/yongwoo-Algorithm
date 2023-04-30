import java.io.*;
import java.util.*;

public class Main {

	static int M; // 가로 크기
	static int N; // 세로 크기
	static int[][] arr;
	
	static int[][] cnt; // 부순 벽 수

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };
	
	static boolean isWall(int nx, int ny) {
		if (nx < 0 || nx >= N || ny < 0 || ny >= M) return true;
		return false;
	}
	
	static int dijkstra(int x, int y) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(x, y, 0));
		
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			
			if (node.x == N - 1 && node.y == M - 1) return node.cost;
			if (cnt[node.x][node.y] < node.cost) continue;
			
			for (int i = 0; i < 4; i++) {
				int nx = node.x + dx[i];
				int ny = node.y + dy[i];
				
				if (isWall(nx, ny)) continue;
				
				int ncost = node.cost + arr[nx][ny];
				if (cnt[nx][ny] <= ncost) continue;
				
				cnt[nx][ny] = ncost;
				pq.add(new Node(nx, ny, ncost));
			}
		}
		
		return -1;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			String inp = br.readLine();
			for (int j = 0; j < M; j++) {
				arr[i][j] = inp.charAt(j) - '0'; 
			}
		}
		
		cnt = new int[N][M];
		for (int i = 0; i < N; i++) {
			Arrays.fill(cnt[i], Integer.MAX_VALUE);
		}
		
		System.out.println(dijkstra(0, 0));
	}
}

class Node implements Comparable<Node> {
	int x;
	int y;
	int cost;
	Node (int a, int y, int b) {
		x = a;
		this.y = y;
		cost = b;
	}
	
	@Override
	public int compareTo(Node n) {
		return this.cost - n.cost;
	}
}
