package _16985;
import java.io.*;
import java.util.*;

/**
 * @link <a href="https://www.acmicpc.net/problem/16985"/>
 */
class Solution {

  static class Point{
    int x,y,z;

    public Point(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    @Override
    public String toString() {
      return "Point{" +
          "x=" + x +
          ", y=" + y +
          ", z=" + z +
          '}';
    }
  }

  int[][][] board = new int[5][5][5];

  void readInput() throws IOException {
    for(int z=0; z<5; z++){
      for(int x=0; x<5; x++){
        st = new StringTokenizer(br.readLine());
        for(int y=0; y<5; y++){
          board[x][y][z] = Integer.parseInt(st.nextToken());
        }
      }
    }
  }

  int[][] edge = {{0,0},{0,4},{4,0},{4,4}};
  int[] dx = {1,0,-1,0};
  int[] dy = {0,1,0,-1};

  void solve() {
    for(int i=0; i<edge.length; i++){
      if(board[edge[i][0]][edge[i][1]][0]==1) continue;

      int distance = getDistanceFrom(new Point(edge[i][0],edge[i][1],0));
    }

    int answer = Integer.MAX_VALUE;
    for(int i=0; i<edge.length; i++){
      if(board[edge[i][0]][edge[i][1]][4]==1) continue;

      answer = Math.min(answer, distance[edge[i][0]][edge[i][1]][4]);
    }

    System.out.println("answer = " + answer);
  }

  int getDistanceFrom(Point start){
    int[][][] distance = new int[5][5][5];
    Queue<Point> q = new ArrayDeque<>();

    while(!q.isEmpty()){
      Point current = q.poll();

      for(int i=0; i<4; i++){
        int nx = current.x + dx[i];
        int ny = current.y + dy[i];

        if(isOut(nx,ny)) continue;
        if(board[nx][ny][current.z]==1) continue;
        if(distance[nx][ny][current.z]!=0) continue;

        q.offer(new Point(nx,ny, current.z));
        distance[nx][ny][current.z] = distance[current.x][current.y][current.z]+1;
      }

      if(current.z>=4) continue;

      int nx = current.x;
      int ny = current.y;
      for(int i=0; i<4; i++){
        int tmp = nx;
        nx = ny;
        ny = 4- tmp;

        if(board[nx][ny][current.z+1]==1) continue;
        if(distance[nx][ny][current.z+1]!=0) continue;

        q.offer(new Point(nx,ny, current.z+1));
        distance[nx][ny][current.z+1] = distance[current.x][current.y][current.z]+1;
      }
    }

    return;
  }

  boolean isOut(int x, int y){
    return 0>x || x>4 || y<0 || y>4;
  }

  void writeOutput() throws IOException {
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