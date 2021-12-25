package extract.uk;

import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader{
	
	public StringBuilder readPdf(File file) throws Exception{
		
		FileInputStream fileStream=new FileInputStream(file);
		PDDocument pdfDoc= PDDocument.load(fileStream);
		PDFTextStripper pdfContent=new PDFTextStripper();
		
		StringBuilder data=new StringBuilder();
		data.append( pdfContent.getText(pdfDoc));
		pdfDoc.close();
		fileStream.close();
		return data;
		
	}
	
	
}