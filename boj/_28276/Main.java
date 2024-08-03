package _28276;

import java.io.*;
import java.util.*;

/**
 * @link https://www.acmicpc.net/problem/28276
 * @title Yawned-Zoned
 * @algorithm
 */
public class Main {

    public static void main(String[] args) throws IOException {
        MySolution.createSolution()
                .start();
    }
}

class MySolution {

    int R,C,W;
    boolean[][] isEmpty;

    int[] roots;
    int[] studentCounts;

    int min = 1_000_001;

    final int IS_EMPTY_TARGET = 0;
    final int IS_OVER_STUDENT_COUNT_LIMIT = -1;
    final int UNION_SUCCESS = 1;
    private void readInput() throws IOException{
        st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        isEmpty = new boolean[R][C];
        for(int x=0; x<R; x++) {
            int y = 0;
            for(char c : br.readLine().toCharArray()){
                isEmpty[x][y] = c=='0';
                y++;
            }
        }
    }

    public MySolution(int R, int C, int W, char[][] map) {
        this.R = R;
        this.C = C;
        this.W = W;

        isEmpty = new boolean[R][C];
        for(int x=0; x<R; x++) {
            int y = 0;
            for(char c : map[x]){
                isEmpty[x][y] = c=='0';
                y++;
            }
        }
    }

    private void solve(){
        int l = 0;
        int r = R * C;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (isPossibleIn(mid)) {
                min = Math.min(min, mid);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
//        System.out.println("isPossibleIn(3) = " + isPossibleIn(3));
    }

    private int[] dx = {-1,0};
    private int[] dy = {0,-1};
    private boolean isNotIn(int x,int y){
        return x<0 || x>=R || y<0 || y>=C;
    }

    private boolean isPossibleIn(int studentCountLimit){
        int partitionCount = 0;

        int total = R*C;
        roots = new int[total];
        studentCounts = new int[total];

        int y=0;
        while(y<C && partitionCount<=W){
            boolean isNotRequiredPartition = true;

            List<int[]> undoList = new ArrayList<>();
            int x=0;
            for(; x<R; x++){
                if(isEmpty[x][y]) continue;

                int unionStatus = unionTopAndGetStatus(x,y,studentCountLimit);
                if(unionStatus==IS_OVER_STUDENT_COUNT_LIMIT){
                    /* undo */
                    for(int[] undoDetail : undoList){
                        studentCounts[find(undoDetail[0]*C+y)] -= undoDetail[1];
                    }

                    isNotRequiredPartition = false;
                    break;
                }

                unionStatus = unionLeftAndGetStatus(x,y,studentCountLimit);
                if(unionStatus==IS_OVER_STUDENT_COUNT_LIMIT){
                    /* undo */
                    for(int[] undoDetail : undoList){
                        studentCounts[find(undoDetail[0]*C+y)] -= undoDetail[1];
                    }

                    x++;
                    isNotRequiredPartition = false;
                    break;
                }else if(unionStatus>0){
                    undoList.add(new int[]{x, unionStatus});
                }
            }

            if(isNotRequiredPartition){
                y++;
                continue;
            }

            if(partitionCount>=W) {
                return false;
            }

            for(; x<R; x++){
                if(isEmpty[x][y]) continue;

                int unionStatus = unionTopAndGetStatus(x,y,studentCountLimit);

                if(unionStatus==IS_OVER_STUDENT_COUNT_LIMIT) return false;
            }

            partitionCount++;
            y++;
        }

        return true;
    }

    private int unionTopAndGetStatus(int currentX, int currentY, int studentCountLimit){
        /* create */
        int source = currentX*C+currentY;
        if(studentCounts[source]==0){
            roots[source] = source;
            studentCounts[source] = 1;
        }

        if(studentCountLimit==0) return IS_OVER_STUDENT_COUNT_LIMIT;

        /* validate Next */
        int nx = currentX+dx[0];
        int ny = currentY+dy[0];

        if(isNotIn(nx,ny)) return IS_EMPTY_TARGET;
        if(isEmpty[nx][ny]) return IS_EMPTY_TARGET;

        /* getUnionStatus */
        int target = nx*C+ny;
        int targetRoot = find(target);

        if(studentCounts[targetRoot]==studentCountLimit) return IS_OVER_STUDENT_COUNT_LIMIT;

        roots[targetRoot] = source;
        studentCounts[source] += studentCounts[targetRoot];

        return 1;
    }

    private int unionLeftAndGetStatus(int currentX, int currentY, int studentCountLimit){
        int nx = currentX+dx[1];
        int ny = currentY+dy[1];

        if(isNotIn(nx,ny)) return IS_EMPTY_TARGET;
        if(isEmpty[nx][ny]) return IS_EMPTY_TARGET;

        int current = currentX*C+currentY;
        int left = nx*C+ny;

        int currentRoot = find(current);
        int leftRoot = find(left);

        if(currentRoot==leftRoot) return IS_EMPTY_TARGET;
        if(studentCounts[currentRoot]+studentCounts[leftRoot]>studentCountLimit) return IS_OVER_STUDENT_COUNT_LIMIT;

        roots[leftRoot] = currentRoot;
        studentCounts[currentRoot] += studentCounts[leftRoot];

        return studentCounts[leftRoot];
    }

    private int find(int index){
        if(index == roots[index]) return index;

        return roots[index] = find(roots[index]);
    }

    private void writeOutput() throws IOException{
        bw.write(Integer.toString(min));
        bw.flush();
    }

    public int start() throws IOException{
        solve();
        return min;
    }

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private StringTokenizer st;

    private MySolution(){}
    public static MySolution createSolution(){
        return new MySolution();
    }
}

