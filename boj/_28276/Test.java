package _28276;

import _28276.answer.Answer;

import java.io.IOException;

public class Test {
    static char[][] map;

    public static void main(String[] args) throws Exception {

        for(int r=1; r<5; r++){
            for(int c=1; c<5; c++){
                for(int w=0; w<c; w++){
                    System.out.println(r+ " " + c + " " + w);
                    map = new char[r][c];
                    dfs(0,0,r,c,w);

                }
            }
        }
    }

    public static void dfs(int x, int y,int r, int c,int w) throws Exception {
        if(x==r) {
            int myAnswer = new MySolution(r,c,w,map).start();
            int realAnswer = new Answer(r,c,w,map).solution();
            if(myAnswer!=realAnswer) {
                for(int i=0; i<r; i++){
                    for(int j=0; j<c; j++){
                        System.out.print(map[i][j]+" ");
                    }
                    System.out.println();
                }

                System.out.println("wrong");
                System.out.println("myAnswer = " + myAnswer);
                System.out.println("realAnswer = " + realAnswer);
                System.out.println("r = " + r);
                System.out.println("c = " + c);
                System.out.println("w = " + w);
                System.out.println();
                System.out.println();
            }
            return;
        }

        int nx = x;
        int ny = y+1;
        if(ny==c){
            ny = 0;
            nx = x+1;
        }


        for(int i=0; i<2; i++){
            map[x][y] = (char)(i+ (int)'0');

            dfs(nx,ny,r,c,w);
        }
    }
}
