package com.chenzehe.wordsorter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;

/**
 * 
 * @description 读取一个文件中的字符(使用BufferedReader按行读取)，排序(使用fork-join框架快速排序)，写到另一个文件中(
 *              使用BufferedWriter進行寫入)
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2013年12月3日 下午2:56:49
 */
public class SortMain {
	private static SortMain instance = null;
	private String[] words;
	private final int THREADS = 12;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date start = new Date();
		SortMain sortMain = SortMain.getInstance();
		if (args == null || args.length < 1) {
			args = new String[2];
			args[0] = sortMain.getClass().getClassLoader().getResource("")
					.getPath()
					+ "com/chenzehe/wordsorter/sowpods.txt";
			args[1] = sortMain.getClass().getClassLoader().getResource("")
					.getPath()
					+ "com/chenzehe/wordsorter/out.txt";
		}
		sortMain.getWordsFromInput(args[0]);
		sortMain.sort();
		sortMain.writeWordsToOutput(args[1]);
		Date end = new Date();
		long timeDiff = end.getTime() - start.getTime();
		System.out.println("total:" + timeDiff + "ms");
	}

	/**
	 * 调用fork-join框架快速排序
	 */
	public void sort() {
		Date start = new Date();
		ForkJoinSortTask wordsSortTask = new ForkJoinSortTask(words);
		ForkJoinPool forkJoinPool = new ForkJoinPool(THREADS);
		forkJoinPool.invoke(wordsSortTask);
		Date end = new Date();
		long timeDiff = end.getTime() - start.getTime();
		System.out.println("sort:" + timeDiff + "ms");
	}

	/**
	 * 按行读取文件保存到集合中 使用BufferedReader按行读取
	 * 
	 * @param inputFile
	 * @return
	 */
	public void getWordsFromInput(String inputFile) {
		Date start = new Date();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(inputFile);
			bufferedReader = new BufferedReader(fileReader);
			String linevalue;
			int i = 0;
			while ((linevalue = bufferedReader.readLine()) != null) {
				// 文件第一行保存行数
				if (words == null) {
					words = new String[Integer.parseInt(linevalue) + 1];
				}
				words[i] = linevalue;
				i += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		Date end = new Date();
		long timeDiff = end.getTime() - start.getTime();
		System.out.println("read file:" + timeDiff + "ms");
	}

	/**
	 * 把经过排序的集合中的字符写到文件中 使用BufferedWriter進行寫入
	 * 
	 * @param outputFile
	 */
	public void writeWordsToOutput(String outputFile) {
		Date start = new Date();
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(outputFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < words.length; i++) {
				String outputWord = (i == words.length - 1) ? words[i]
						: words[i] + "\n";
				bufferedWriter.write(outputWord);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Date end = new Date();
		long timeDiff = end.getTime() - start.getTime();
		System.out.println("write file:" + timeDiff + "ms");
	}

	private static SortMain getInstance() {
		if (instance == null) {
			instance = new SortMain();
		}
		return instance;
	}
}
