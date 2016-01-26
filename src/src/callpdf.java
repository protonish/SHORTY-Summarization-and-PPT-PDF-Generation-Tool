/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.util.PDFTextStripper;
/**
 *
 * @author kumaran
 */
public class callpdf {
    callpdf(File file) throws IOException{
     PDFTextParser pdfTextParserObj = new PDFTextParser();
                    String pdfToText = pdfTextParserObj.pdftoText(file.getAbsolutePath());
                    if (pdfToText == null) {
        	System.out.println("PDF to Text Conversion failed.");
        }
        else {
        	System.out.println("\nThe text parsed from the PDF Document....\n" + pdfToText);
        	pdfTextParserObj.writeTexttoFile(pdfToText, "Converted.txt");
                
              
        }
                    
}
}
