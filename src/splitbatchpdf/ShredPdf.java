/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splitbatchpdf;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.pdfbox.Loader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author KAZUNGU
 */
public class ShredPdf {

    public String getDocumentNo(String pdfFilePaths) {

        String pdfContents = "";
          DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssss");
            String dateName = df.format(new Date());
        String documentNo = "___"+dateName;
        try {
            PDDocument document = Loader.loadPDF(new File(pdfFilePaths));
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                pdfContents = stripper.getText(document).replace("\n", "").replace("\r", "").replaceAll(" ", "").toLowerCase();
                 System.out.println(">>>doc contains>>>" + pdfContents);
                if (pdfContents.contains("docno") && pdfContents.contains("docdate")) {
                    int indexStart = pdfContents.indexOf("docno");
                    int indexEnd = pdfContents.toLowerCase().indexOf("docdate");
                    documentNo = pdfContents.substring(indexStart, indexEnd).replace("docno:", "");

                    System.out.println(">>>doc no>>>" + documentNo);
                }

            } else {
            }
            document.close();
        } catch (IOException ex) {
            //  Logger.getLogger(DocPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documentNo;

    }
}
