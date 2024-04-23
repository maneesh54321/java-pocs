package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
			System.out.println("Starting matrix multiplication...");
			Instant start = Instant.now();
			long[][] result = MatrixMultiplier.multiplyMultiThreaded(m1, m2, executorService);
			Instant end = Instant.now();
			System.out.printf("Finished matrix multiplication in %s seconds\n", Duration.between(start, end).getSeconds());
//			print(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static void print(long[][] result) {
		System.out.println("Result is: ");
		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
		}
	}
}