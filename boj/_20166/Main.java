package _20166;

import java.io.*;
import java.util.*;

/**
 * @link <a href="https://www.acmicpc.net/problem/20166"/>
 */
class Solution {

  int N, M, K;
  char[][] board;
  String[] favoriteTexts;

  int[] dx = {1, 0, -1, 0, 1, 1, -1, -1};
  int[] dy = {0, 1, 0, -1, 1, -1, 1, -1};

  static class Node {

    int x, y;
    int delta;

    public Node(int x, int y, int delta) {
      this.x = x;
      this.y = y;
      this.delta = delta;
    }
  }

  void readInput() throws IOException {
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    board = new char[N][M];
    for (int i = 0; i < N; i++) {
      board[i] = br.readLine().toCharArray();
    }

    favoriteTexts = new String[K];
    for (int i = 0; i < K; i++) {
      favoriteTexts[i] = br.readLine();
    }
  }

  String answer;

  void solve() {
    StringBuilder sb = new StringBuilder();

    Map<String, Integer> cache = new HashMap<>();

    for (String text : favoriteTexts) {
      sb.append(cache.computeIfAbsent(text, key -> countBy(text))).append("\n");
    }

    answer = sb.toString();
  }

  int countBy(String text) {
    int count = 0;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        if (text.charAt(0) != board[i][j]) {
          continue;
        }

        count += countFrom(text, new Node(i, j, 0));
      }
    }

    return count;
  }

  int countFrom(String text, Node start) {
    int count = 0;
    Queue<Node> q = new ArrayDeque<>();

    q.offer(start);

    while (!q.isEmpty()) {
      Node current = q.poll();

      int nDelta = current.delta + 1;
      if (nDelta == text.length()) {
        count++;
        continue;
      }

      for (int i = 0; i < 8; i++) {
        int nx = (N + current.x + dx[i]) % N;
        int ny = (M + current.y + dy[i]) % M;

        if (text.charAt(nDelta) != board[nx][ny]) {
          continue;
        }

        q.offer(new Node(nx, ny, nDelta));
      }
    }

    return count;
  }

  void writeOutput() throws IOException {
    bw.write(answer);
    bw.flush();
  }

  public static void run() throws IOException {
    Solution solution = new Solution();
    solution.readInput();
    solution.solve();
    solution.writeOutput();
  }

  private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
  private StringTokenizer st;
}

public class Main {

  public static void main(String[] args) throws IOException {
    Solution.run();
  }
}