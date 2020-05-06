package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/** This class is used to generate a 32char for a Session key
 using Fisher–Yates shuffle for more unique permutation*/
public class KeyGenerator {
	private static final int keyLen = 32;
	private static final String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"; 
	private static final Random gen = new Random();
	
	private static Integer[] getIndices(){
		List<Integer> indices = new ArrayList<Integer>();
		
		for(int i = 0;i < keyLen; i++) {
			int index = gen.nextInt(alphaNum.length());
			indices.add(index);
		}
		Integer [] arr = new Integer[indices.size()];
		return indices.toArray(arr);
		
	}
	
	private static Integer []  swap(Integer[] arr, int i, int j) {
		int temp  = arr[i];
		arr[i] = arr[j];
		arr[j]  = temp;
		return arr;
	}
	
	private static int  generateIndex(int min, int max) {
		return gen.ints(min, max).findFirst().getAsInt();
	}
	

	private static Integer[] shuffle(Integer [] indices) {
		for(int i = 0; i < indices.length - 2; i++) {
			int j = generateIndex(i, indices.length);
			indices = swap(indices, i, j);
		}
		
		return indices;
	}
	
	public static String generateKey() {
		Integer [] indices = getIndices();
		indices = shuffle(indices);
		StringBuilder key = new StringBuilder();
		
		for(int i = 0; i < indices.length; i++) {
			key.append(alphaNum.charAt(indices[i]));
		}
		return key.toString();
		
		
	}
	public static void main(String[] args) {
		System.out.println(KeyGenerator.generateKey());
	}
	

	
}
