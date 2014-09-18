package csc.kth.adk14;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import csc.kth.adk14.Concordance.PositionRange;


public class Mountains {
	private File kFile, k2File, eFile;

	/**
	 * The K file should already exists. K2 and Everest files will be created/generated by this class.
	 * @param kPath input
	 * @param k2Path output
	 * @param everestPath output
	 */
	public Mountains(String kPath, String k2Path, String everestPath) {
		kFile = new File(kPath);
		k2File = new File(k2Path);
		eFile = new File(everestPath);
	}

	/**
	 * Creates a K2 and Everest file from the K file.
	 * K2 contains a list of distinct words and their offset in Everest,
	 * and Everest contains a list of the offsets in S sorted by each word.
	 * 
	 * @throws Exception sometiemess. dont ask
	 */
	public void generateFromFile() throws IOException {
		BufferedReader kReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(kFile), "ISO-8859-1"));
		BufferedWriter k2Writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(k2File), "ISO-8859-1"));
		DataOutputStream eWriter = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(eFile)));
		  
		String lastSaved = "";
		String line;
		long offsetInE = 0;
		int positionSize = Long.SIZE/8;

		while ((line = kReader.readLine()) != null) {
			String[] data = line.split(" "); 
			String currWord = data[0];
			long posInS = Long.valueOf(data[1]);
			
			if (!currWord.equals(lastSaved)) {
				// Save word in K2 together with corresponding byte offset in E.
				k2Writer.write(currWord+" "+offsetInE+"\n");
				// Save the word's position in S in E.
				eWriter.writeLong(posInS);
				lastSaved = currWord;
			} else {
				// The word has previously been added to K2 so only add it to E.
				// Save position in S.
				eWriter.writeLong(posInS);
			}
			offsetInE += positionSize;
		}
		eWriter.close();
		// Make sure this is written after the writer to 
		k2Writer.write("EOF "+eFile.length()+"\n");
		kReader.close();
		k2Writer.close();
	}
	
	public File getK2() {
		return k2File;
	}

	public File getEverest() {
		return eFile;
	}
}
