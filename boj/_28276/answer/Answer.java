package _28276.answer;

import java.util.*;

public class Answer {
    int r, c, w;
    boolean[][] arr;
    int[] parents;
    List<UfOrder> orders = new ArrayList<>();

    public Answer(int r, int c, int w,char[][] map) {
        this.r = r;
        this.c = c;
        this.w = w;

        arr = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                arr[i][j] = map[i][j]== '1';
            }
        }
    }

    public int solution() throws Exception {
        parents = new int[r*c];

        int start = 0;
        int end = 1000000;
        while (start<=end) {
            int mid = (start+end)/2;

            Arrays.fill(parents, -1);
            orders = new ArrayList<>();

            if (!isPossible(mid))
                start = mid+1;
            else
                end = mid-1;
        }

        return start;
    }

    private int find(int a) {
        if (parents[a] < 0) return a;

        orders.add(new UfOrder(a, parents[a]));
        return parents[a] = find(parents[a]);
    }

    private void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return;

        int hi = parents[a]<=parents[b]?a:b;
        int lo = parents[a]<=parents[b]?b:a;
        orders.add(new UfOrder(hi, parents[hi]));
        parents[hi]+=parents[lo];
        orders.add(new UfOrder(lo, parents[lo]));
        parents[lo] = hi;
    }

    private boolean isPossible(final int limit) {
        int j = 0;
        int wIdx = 0;

        for (int wCnt = 0; wCnt <= w && j < c; wCnt++) {
            boolean chk = true;

            while (j<c) {
                for (int i = 0; i < r && j < c; i++) {
                    if (!arr[i][j]) continue;

                    int ufIdx = i*c+j;

                    if (i-1>=0 && arr[i-1][j]) {
                        int otherIdx = (i-1)*c+j;
                        union(ufIdx, otherIdx);
                    }

                    if (j-1>=0 && j-1>=wIdx && arr[i][j-1]) {
                        int otherIdx = i*c+(j-1);
                        union(ufIdx, otherIdx);
                    }

                    int cur = -parents[find(ufIdx)];
                    if (cur > limit) {
                        if (wIdx == j) return false;
                        chk = false;
                        wIdx = j;

                        for (int k = orders.size()-1; k >= 0; k--) {
                            UfOrder curOrder = orders.get(k);
                            parents[curOrder.idx] = curOrder.bf;
                        }
                        orders = new ArrayList<>();

                        break;
                    }
                }

                if (chk) {
                    j++;
//                    orders = new ArrayList<>();
                }
                else break;
            }
        }

        return j == c;
    }
}

class UfOrder {
    int idx;
    int bf;

    public UfOrder(final int idx, final int bf) {
        this.idx = idx;
        this.bf = bf;
    }
}