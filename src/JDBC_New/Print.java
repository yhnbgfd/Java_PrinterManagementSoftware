/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JDBC_New;

import java.io.FileInputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Lugia
 */
public class Print {

/* 打印指定的文件 */
public void printFileAction(String file) {
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintService printService[] =
			PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = ServiceUI.printDialog(null, 200, 200, printService
            , defaultService, flavor, pras);
        if (service != null)
        {
             try
            {
                DocPrintJob job = service.createPrintJob();
                FileInputStream fis = new FileInputStream(file);
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das);
                job.print(doc, pras);
            }
            catch(Exception e)
            {
                 e.printStackTrace();
            }
        }
    }
}
