package extract.uk;

import java.io.*;
import java.util.regex.*;

import uday.kumar.DocxReader;

public class Extractar {
	static GraphicsPart object = GraphicsPart.getObj();

	public static void extract(File dir) {

		try {
			
			Pattern txt = Pattern.compile("[a-zA-Z0-9_$].*[.]txt");
			Pattern pdf = Pattern.compile("[a-zA-Z0-9_$].*[.]pdf");
			Pattern docx = Pattern.compile("[a-zA-Z0-9_$].*[.]docx");

			for (File file :dir.listFiles()) {

				Matcher txtMatcher = txt.matcher(file.getName());
				Matcher PdfMatcher = pdf.matcher(file.getName());
				Matcher docxMatcher = docx.matcher(file.getName());
				if (txtMatcher.find() && txtMatcher.group().equals(file.getName()))
					extractFromTxt(file);
				else if (PdfMatcher.find() && PdfMatcher.group().equals(file.getName()))
					extractFrmoPdf(file);
				else if (docxMatcher.find() && docxMatcher.group().equals(file.getName()))
					extractFromDocx(file);

			}

			object.log.append("Extraction done" + "\n" + "Numbers and Emails have been saved to Desktop" + "\n");
			
		} catch (Exception e) {
			object.log.append("error occurred while reading files " + "\n");
			
		}
	}
	
	public static void extractFromDocx(File file) {
		try {
			
			StringBuilder data=new StringBuilder(readDocxFile(file));
			extract(data,file);
		} catch (Exception e) {
			object.log.append("error ocurred during reading " + file.getName() +"\n");
		}
	}

	public static void extractFromTxt(File file) {

		try {
			StringBuilder data = readTextFile(file);
			extract(data, file);
		} catch (IOException e) {
			object.log.append("error ocurred during reading " + file.getName() +"\n");
			
		}
	}

	private static void extractFrmoPdf(File file) {

		try { 
			extract(readPdfFile(file), file);
		} catch (Exception e) {
			object.log.append("error ocurred during reading " + file.getName() +"\n");
			
		}
	}
	
	private static String readDocxFile(File file) throws Exception {
		return DocxReader.readDocx(file);
	}
	
	private static StringBuilder readTextFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder result = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			result.append(line);
			line = reader.readLine();
		}
		reader.close();
		return result;
	}

	private static StringBuilder readPdfFile(File file) throws Exception {

		PdfReader read = new PdfReader();
		return read.readPdf(file);
	}

	private static void extract(StringBuilder data, File file) throws IOException {

		Pattern p = Pattern.compile("((0|91)?[6-9][0-9]{9})|([a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z0-9]+([.][a-zA-Z]+))");

		File toWrite = new File("C:\\Users\\UDAY\\Desktop\\NumbersAndEmails.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(toWrite, true));
		Matcher m = p.matcher(data);
		
		while (m.find()) 
			writer.write(m.group() + "\n");
		
		writer.flush();
		writer.close();
		
		object.log.append("Extracting from " + file.getName() + "\n");
		

	}

	
	
}
