package org.example;

public class MatrixMultiplier {
	public static long[][] multiply(int[][] m1, int[][] m2){
		int m1TotCols = m1[0].length;
		int m1TotRows = m1.length;
		int m2TotCols = m2[0].length;
		int m2TotRows = m2.length;
		assert m1TotCols == m2TotRows;
		assert m1TotRows == m2TotCols;

		long[][] result = new long[m1TotRows][m2TotCols];
		for (int i = 0; i < m1TotRows; i++) {
			for (int j = 0; j < m2TotCols; j++) {
				for (int k = 0; k < m1TotCols; k++) {
					result[i][j] += (long) m1[i][k] * m2[k][j];
				}
			}
		}
		return result;
	}

	public static long[][] multiplyMultiThreaded(int[][] m1, int[][] m2){
		int m1TotCols = m1[0].length;
		int m1TotRows = m1.length;
		int m2TotCols = m2[0].length;
		int m2TotRows = m2.length;
		assert m1TotCols == m2TotRows;
		assert m1TotRows == m2TotCols;

		long[][] result = new long[m1TotRows][m2TotCols];
		for (int i = 0; i < m1TotRows; i++) {
			for (int j = 0; j < m2TotCols; j++) {
				Runnable runnable = new RowColumnMultiplierRunnable(m1, m2, i, j, result);
				new Thread(runnable).start();
			}
		}
		return result;
	}

	public static long[][] multiplyMultiThreadedVirtual(int[][] m1, int[][] m2){
		int m1TotCols = m1[0].length;
		int m1TotRows = m1.length;
		int m2TotCols = m2[0].length;
		int m2TotRows = m2.length;
		assert m1TotCols == m2TotRows;
		assert m1TotRows == m2TotCols;

		long[][] result = new long[m1TotRows][m2TotCols];
		for (int i = 0; i < m1TotRows; i++) {
			for (int j = 0; j < m2TotCols; j++) {
				Runnable runnable = new RowColumnMultiplierRunnable(m1, m2, i, j, result);
				Thread.ofVirtual().start(runnable);
			}
		}
		return result;
	}

	private static class RowColumnMultiplierRunnable implements Runnable {

		private final int[][] m1;
		private final int[][] m2;
		private final long[][] result;
		private final int i;
		private final int j;

		public RowColumnMultiplierRunnable(int[][] m1, int[][] m2, int i, int j, long[][] result) {
			this.m1 = m1;
			this.m2 = m2;
			this.result = result;
			this.i = i;
			this.j = j;
		}

		@Override
		public void run() {
			int m1TotCols = m1[0].length;
			System.out.println("Multiplying row: " + i + " column: " + j);
			for (int k = 0; k < m1TotCols; k++) {
				result[i][j] += (long) m1[i][k] * m2[k][j];
			}
		}
	}
}
