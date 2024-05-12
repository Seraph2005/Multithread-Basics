package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct = new ArrayList<>();
        List<List<Integer>> amat;
        List<List<Integer>> bmat;
        int aStart;
        int aEnd;
        int bStart;
        int bEnd;
        int q;

        public BlockMultiplier(List<List<Integer>> A, List<List<Integer>> B, int a, int b, int c, int d) {
            amat = A;
            bmat = B;
            aStart = a;
            aEnd = b;
            bStart = c;
            bEnd = d;
            q = B.size();
        }

        @Override
        public void run() {
            for(int i = 0; i < bmat.get(0).size(); i++)
            {
                List<Integer> temp = new ArrayList<>();
                for(int j = 0; j < amat.size(); j++)
                    temp.add(0);
                tempMatrixProduct.add(temp);
            }

            for(int i = aStart; i < aEnd; i++) {
                for(int j = bStart; j < bEnd; j++) {
                    int sum = 0;
                    for(int k = 0; k < q; k++) {
                        sum += amat.get(i).get(k)*bmat.get(k).get(j);
                    }
                    tempMatrixProduct.get(i).set(j, sum);
                }
            }
        }
    }

    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        int p = matrix_A.size();
        int r = matrix_B.get(0).size();
        BlockMultiplier b1 = new BlockMultiplier(
                matrix_A, matrix_B,
                0, p/2,
                0, r/2);
        BlockMultiplier b2 = new BlockMultiplier(
                matrix_A, matrix_B,
                0, p/2,
                r/2, r);
        BlockMultiplier b3 = new BlockMultiplier(
                matrix_A, matrix_B,
                p/2, p,
                0, r/2);
        BlockMultiplier b4 = new BlockMultiplier(
                matrix_A, matrix_B,
                p/2, p,
                r/2, r);

        Thread t1 = new Thread(b1);
        Thread t2 = new Thread(b2);
        Thread t3 = new Thread(b3);
        Thread t4 = new Thread(b4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        List<List<Integer>> finalmat = new ArrayList<>();
        for(int i = 0; i < r; i++)
        {
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < p; j++)
            {
                temp.add(b1.tempMatrixProduct.get(i).get(j) +
                        b2.tempMatrixProduct.get(i).get(j) +
                        b3.tempMatrixProduct.get(i).get(j) +
                        b4.tempMatrixProduct.get(i).get(j));
            }
            finalmat.add(temp);
        }
        return finalmat;
    }

    public static void main(String[] args) {

    }
}
