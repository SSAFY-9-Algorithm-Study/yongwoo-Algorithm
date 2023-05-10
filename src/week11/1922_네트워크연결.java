import java.io.*;
import java.util.*;

// 노드 최대 1_000개, 간선 최대 100_000개
class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static int N; // 컴퓨터 수
	static int M; // 연결 선 수
	
	static List<Node>[] list; // 연결 리스트, prim
	static boolean[] visited;
	
	static PriorityQueue<Node2> queue; // 우선순위큐, kruskal 
	static int[] parent; // 부모 노드 저장 배열
	
	static int res;
	
	static void prim(int x) throws IOException {
		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			list[a].add(new Node(b, c));
			list[b].add(new Node(a, c));
		}
		
		visited = new boolean[N + 1];
		
		Queue<Node> queue = new PriorityQueue<>();
		queue.add(new Node(x, 0));
		
		int visitedCnt = 0; // 방문한 노드 수
		
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			
			// 방문한 노드 continue, 방문하지 않은 노드 방문 처리
			if (visited[node.n]) continue;
			visited[node.n] = true;

			res += node.cost;
			
			// 모든 노드를 방문하였으면 종료
			visitedCnt++;
			if (visitedCnt == N) return;
			
			for (Node nNode : list[node.n]) {
				if (visited[nNode.n]) continue;
				
				queue.add(nNode);
			}
		}
	}
	
	static void kruskal() throws IOException {
		queue = new PriorityQueue<>();
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			queue.add(new Node2(a, b, c));
		}

		parent = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			parent[i] = i;
		}

		int visitedCnt = 0; // 방문한 노드 수
		
		while(!queue.isEmpty()) {
			Node2 node = queue.poll();
			
			if (findSet(node.s) == findSet(node.e)) continue;
			
			res += node.cost;

			// 모든 노드를 방문하였으면 종료
			visitedCnt++;
			if (visitedCnt == N) return;
			
			union(node.s, node.e);
		}
		
	}
	
	static int findSet(int x) {
		if (parent[x] != x) parent[x] = findSet(parent[x]);
		return parent[x];
	}
	
	static void union(int x, int y) {
		int p1 = findSet(x);
		int p2 = findSet(y);
		
		parent[p1] = p2;
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
//		prim(1);
		
		kruskal();
		
		System.out.println(res);
	}
}

// prim
class Node implements Comparable<Node> {
	int n;
	int cost;
	Node (int n, int cost) {
		this.n = n;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(Node o) {
		return this.cost - o.cost;
	}
}

// kruskal
class Node2 implements Comparable<Node2> {
	int s;
	int e;
	int cost;
	Node2 (int s, int e, int cost) {
		this.s = s;
		this.e = e;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(Node2 o) {
		return this.cost - o.cost;
	}
}
