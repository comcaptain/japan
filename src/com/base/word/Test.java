package com.base.word;


public class Test {
	public static void main(String[] args) {
		System.out.println("1".startsWith(null));
		
	}
	public static boolean switchints(int[] a, int[] b) {
		int diff = getDiff(a, b);
		int originDiff = diff;
		int i = 0, j = 0;
		//N^2 time
		for (int anum: a) {
			j = 0;
			for (int bnum: b) {
				if (Math.abs(diff - 2 * anum + 2 * bnum) < Math.abs(diff)) {
					a[i] = bnum;
					b[j] = anum;
					//N time 
					diff = diff - 2 * anum + 2 * bnum;
					//print(a, b);
					System.out.println("difference:" + diff);
					anum = bnum;
				}
				j++;
			}
			i++;
		}
		return Math.abs(diff) < Math.abs(originDiff);
	}
	private static void print(int[] a, int[] b) {
		for (int anum: a) {
			System.out.print(anum + "\t");
		}
		System.out.println();
		for (int bnum: b) {
			System.out.print(bnum + "\t");
		}
		System.out.println();
	}
	public static int getDiff(int[] a, int[] b) {
		return sum(a) - sum(b);
	}
	private static int sum(int[] ints) {
		int result = 0;
		for (int num: ints) result += num;
		return result;
	}
	public static int[] getRandomIntegers(int n, int limit) {
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = (int)(Math.random() * limit);
		}
		return result;
	}
}
