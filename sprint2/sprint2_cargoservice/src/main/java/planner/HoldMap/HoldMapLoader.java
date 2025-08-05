package main.java.planner.HoldMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class HoldMapLoader {
	
	public HoldMapLoader() {
		super();
	}
	
	public static HoldMap loadHoldMap(String fileName) throws IOException, ClassNotFoundException {		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
		
		HoldMap holdMap = (HoldMap) ois.readObject();
		
		ois.close();
		
		return holdMap;
	}
	
	public static void saveHoldMap(HoldMap holdMap, String fileName) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName.replace(".txt", "") + ".bin"));
		PrintWriter pw = new PrintWriter(new FileWriter(fileName.replace(".bin", "") + ".txt"));
		
		oos.writeObject(holdMap);
		pw.print(holdMap.toString());
		
		oos.close();
		pw.close();
	}
	
	public static void fromTextToBinFile(String fileName) throws IOException {
		HoldMap holdMap = HoldMap.getHoldMap();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		int rowIndex = 0;
		String line = br.readLine();
		while(line != null) {
			line = line.replace("|", "");
			String[] cells = line.split(",");
			
			int colIndex = 0;
			for(String c : cells) {
				Cell cell = new Cell(CellType.fromCode(c.trim()));
				holdMap.putCell(rowIndex, colIndex, cell);
				
				colIndex += 1;
			}
			
			line = br.readLine();
			rowIndex += 1;
		}
		
		br.close();
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName.replace(".txt", "") + ".bin"));
		oos.writeObject(holdMap);
		oos.close();
	}
}
