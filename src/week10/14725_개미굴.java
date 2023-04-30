import java.io.*;
import java.util.*;

class Main {

    static int N; // 정보 개수
    static List<Node> tree;

    static StringBuilder sb;

    static void print(List<Node> list, int depth) {
        Collections.sort(list);

        for (Node node : list) {
            for (int i = 0; i < depth; i++) {
                sb.append("-");
            }
            sb.append(node.str).append("\n");

            if (node.list.size() > 0) print(node.list, depth + 2);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        // 입력
        N = Integer.parseInt(br.readLine());
        tree = new ArrayList<Node>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            List<Node> list = tree;

            for (int j = 0; j < K; j++) {
                String input = st.nextToken();

                int k = 0;
                int cnt = list.size();
                for (; k < cnt; k++) {
                    Node node = list.get(k);

                    if (node.str.equals(input)) {
                        list = node.list;
                        break;
                    }
                }
                if (k == cnt) {
                    Node node = new Node(input);
                    list.add(node);
                    list = node.list;
                }
            }
        }

        print(tree, 0);

        System.out.println(sb);
    }
}

class Node implements Comparable<Node> {
    String str;
    ArrayList<Node> list;
    
    Node (String str) {
        this.str = str;
        list = new ArrayList<>();
    }

    @Override
    public int compareTo(Node n) {
        return str.compareTo(n.str);
    }
}
