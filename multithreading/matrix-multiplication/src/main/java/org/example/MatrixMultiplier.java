package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MatrixMultiplier {
	public static long[][] multiply(int[][] m1, int[][] m2) {
		int m1TotCols = m1[0].length;
		int m1TotRows = m1.length;
		int m2TotCols = m2[0].length;
		int m2TotRows = m2.length;
		assert m1TotCols == m2TotRows;
		assert m1TotRows == m2TotCols;

		long[][] result = new long[m1TotRows][m2TotCols];
		for (int i = 0; i < m1TotRows; i++) {
			for (int j = 0; j < m2TotCols; j++) {
//				System.out.println("Multiplying row: " + i + " column: " + j);
				for (int k = 0; k < m1TotCols; k++) {
					result[i][j] += (long) m1[i][k] * m2[k][j];
				}
			}
		}
		return result;
	}

	public static long[][] multiplyMultiThreaded(int[][] m1, int[][] m2, ExecutorService executorService) {
		int m1TotCols = m1[0].length;
		int m1TotRows = m1.length;
		int m2TotCols = m2[0].length;
		int m2TotRows = m2.length;
		assert m1TotCols == m2TotRows;
		assert m1TotRows == m2TotCols;
		List<Future<?>> futures = new ArrayList<>();
		long[][] result = new long[m1TotRows][m2TotCols];
		for (int i = 0; i < m1TotRows; i++) {
			for (int j = 0; j < m2TotCols; j++) {
				Runnable runnable = new RowColumnMultiplierRunnable(m1, m2, i, j, result);
				Future<?> submit = executorService.submit(runnable);
				futures.add(submit);
			}
		}
		futures.forEach(future -> {
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

//	public static long[][] multiplyMultiThreadedVirtual(int[][] m1, int[][] m2){
//		int m1TotCols = m1[0].length;
//		int m1TotRows = m1.length;
//		int m2TotCols = m2[0].length;
//		int m2TotRows = m2.length;
//		assert m1TotCols == m2TotRows;
//		assert m1TotRows == m2TotCols;
//
//		long[][] result = new long[m1TotRows][m2TotCols];
//		for (int i = 0; i < m1TotRows; i++) {
//			for (int j = 0; j < m2TotCols; j++) {
//				Runnable runnable = new RowColumnMultiplierRunnable(m1, m2, i, j, result);
//				Thread.ofVirtual().start(runnable);
//			}
//		}
//		return result;
//	}

	private record RowColumnMultiplierRunnable(int[][] m1, int[][] m2, int i, int j,
											   long[][] result) implements Runnable {

		@Override
		public void run() {
			try {
				int m1TotCols = m1[0].length;
//				System.out.println("Multiplying row: " + i + " column: " + j);
				for (int k = 0; k < m1TotCols; k++) {
					result[i][j] += (long) m1[i][k] * m2[k][j];
				}
				Thread.sleep(20);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
