import java.io.*;
import java.util.*;

public class Main {
	
	static int N; // 수빈 위치
	static int K; // 동생 위치
	
	static boolean[] visited;
	
	static int dijkstra(int N) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(N, 0));
		
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			
			if (node.x == K) return node.cost;
			if (visited[node.x]) continue;
			visited[node.x] = true; 
			
			// 2배 위치
			for (int i = node.x * 2; i <= 100_000; i *= 2) {
				if (visited[i]) break;
				
				pq.add(new Node(i, node.cost));
			}
			
			// 1의 거리 이동
			if (node.x + 1 <= 100_000 && !visited[node.x + 1]) pq.add(new Node(node.x + 1, node.cost + 1));
			if (node.x - 1 >= 0 && !visited[node.x - 1]) pq.add(new Node(node.x - 1, node.cost + 1));
		}
		
		return -1;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		visited = new boolean[100_001];
		
		int res = dijkstra(N);
		
		System.out.println(res);
	}
}

class Node implements Comparable<Node> {
	int x;
	int cost;
	Node (int a, int b) {
		x = a;
		cost = b;
	}
	
	@Override
	public int compareTo(Node n) {
		return this.cost - n.cost;
	}
}
