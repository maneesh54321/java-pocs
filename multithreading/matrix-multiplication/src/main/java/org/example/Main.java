package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int a = 2000;
		int b = 2000;
		int c = 2000;
		Random random = new Random();
		int[][] m1 = new int[a][b];
		int[][] m2 = new int[b][c];
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				m1[i][j] = random.nextInt(50);
			}
		}
		for (int i = 0; i < b; i++) {
			for (int j = 0; j < c; j++) {
				m2[i][j] = random.nextInt(50);
			}
		}
		System.out.println("Starting matrix multiplication...");
		Instant start = Instant.now();
		long[][] result = MatrixMultiplier.multiplyMultiThreadedVirtual(m1, m2);
		Instant end = Instant.now();
		System.out.println("Finished matrix multiplication in " + Duration.between(start, end).getSeconds());
//		System.out.println("Result is: ");
//		for (int i = 0; i < result.length; i++) {
//			System.out.println(Arrays.toString(result[i]));
//		}
	}
}