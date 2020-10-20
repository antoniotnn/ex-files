package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();

		System.out.print("Enter source file path: ");
		String sourceFilePath = sc.nextLine();
		
		File sourceFile = new File(sourceFilePath);
		String sourceFolder = sourceFile.getParent();
		
		boolean success = new File(sourceFolder +"\\out").mkdir();
		
		System.out.println("Folder Created successly: "+success);
		
		String targetFile = sourceFolder + "\\out\\summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader (sourceFile))) {
			String itemCsv = br.readLine();
			while (itemCsv != null) {
				String[]fields=itemCsv.split(",");
				String name=fields[0];
				Double price=Double.parseDouble(fields[1]);	
				int quantity=Integer.parseInt(fields[2]);
				
				list.add(new Product(name,price,quantity));
				
				itemCsv=br.readLine();
				
			}	
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))) {
				for (Product item : list) {
					bw.write(item.getName()+","+String.format("%.2f", item.total()));
					bw.newLine();
				}
				
				System.out.println(targetFile+" CREATED!");
			}
			catch (IOException e) {
				System.out.println("Error Writing File: "+ e.getMessage());
			}
			
			
		} catch (IOException e) {
			System.out.println("Error reading file: "+ e.getMessage());
		} 
		

		sc.close();
	}

}
