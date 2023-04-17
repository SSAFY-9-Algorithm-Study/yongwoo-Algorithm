import java.io.*;
import java.util.*;

class Main {

	static int N; // 정점 개수
	static int M; // 간선 개수
	static int X; // 도착 정점
	
	// X에서 특정 위치까지 가는 경우의 수를 위한 정방향 리스트와 X위치까지 오는 경우의 수를 위한 역방향 리스트
	static List<Node>[] fromXList;
	static List<Node>[] toXList;

	static int[] fromXDist;
	static int[] toXDist;

	static void dijkstra(List<Node>[] list, int[] dist, int s) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[s] = 0;
		pq.add(new Node(s, 0));

		while(!pq.isEmpty()) {
			Node node = pq.poll();
			
			if (node.cost > dist[node.num]) continue;

			for (Node n : list[node.num]) {
				if (dist[n.num] <= dist[node.num] + n.cost) continue;

				dist[n.num] = dist[node.num] + n.cost;
				pq.add(new Node(n.num, dist[n.num]));
			}
		}
	}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		fromXList = new ArrayList[N + 1];
		toXList = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			fromXList[i] = new ArrayList<Node>();
			toXList[i] = new ArrayList<Node>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			fromXList[u].add(new Node(v, w));
			toXList[v].add(new Node(u, w));
		}

		fromXDist = new int[N + 1];
		toXDist = new int[N + 1];

		Arrays.fill(fromXDist, Integer.MAX_VALUE);
		Arrays.fill(toXDist, Integer.MAX_VALUE);

		dijkstra(fromXList, fromXDist, X);
		dijkstra(toXList, toXDist, X);

		int max = 0;
		for (int i = 1; i <= N; i++) {
			max = Math.max(max, fromXDist[i] + toXDist[i]);
		}

		System.out.println(max);
    }
}

class Node implements Comparable<Node> {
	int num;
	int cost;

	Node (int num, int cost) {
		this.num = num;
		this.cost = cost;
	}

	@Override
	public int compareTo(Node n) {
		return this.cost - n.cost;
	}
}
