package treads;

import javafx.collections.ObservableList;
import model.TheLastTable;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class FirstTreadOnCalc_2 extends Thread {
    public void run(ObservableList<TheLastTable> observableList) {
        //Blank Document
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("create_table.docx"));


        // Create a new table with 7 rows and 6 columns
        int nRows = observableList.size()+1;
        int nCols = 6;
        XWPFTable table = document.createTable(nRows, nCols);

            // Get a list of the rows in the table
            List<XWPFTableRow> rows = table.getRows();
            int rowCt = 0;
            int colCt = 0;
            for (XWPFTableRow row : rows) {
                // get table row properties (trPr)
                CTTrPr trPr = row.getCtRow().addNewTrPr();
                // set row height; units = twentieth of a point, 360 = 0.25"
                CTHeight ht = trPr.addNewTrHeight();
                ht.setVal(BigInteger.valueOf(360));

                // get the cells in this row
                List<XWPFTableCell> cells = row.getTableCells();
                // add content to each cell
                for (XWPFTableCell cell : cells) {



                    // get a table cell properties element (tcPr)
                    CTTcPr tcpr = cell.getCTTc().addNewTcPr();
                    // set vertical alignment to "center"
                    CTVerticalJc va = tcpr.addNewVAlign();
                    va.setVal(STVerticalJc.CENTER);

                    // create cell color element
                    CTShd ctshd = tcpr.addNewShd();
                    ctshd.setColor("auto");
                    ctshd.setVal(STShd.CLEAR);
                    if (rowCt == 0) {
                        // header row
                        ctshd.setFill("A7BFDE");
                    } else if (rowCt % 2 == 0) {
                        // even row
                        ctshd.setFill("D3DFEE");
                    } else {
                        // odd row
                        ctshd.setFill("EDF2F8");
                    }

                    // get 1st paragraph in cell's paragraph list
                    XWPFParagraph para = cell.getParagraphs().get(0);
                    // create a run to contain the content
                    XWPFRun rh = para.createRun();


                    if(rowCt != 0) {
                        rh.setFontSize(10);
                        rh.setFontFamily("Times New Roman");
                        rh.setBold(false);
                        para.setAlignment(ParagraphAlignment.CENTER);
                        //----------------------------switch / case-----------
                        switch (colCt) {
                            case 0:
                                rh.setText(observableList.get(rowCt-1).getOfficialPerson());
                                break;
                            case 1:
                                rh.setText(observableList.get(rowCt-1).getTypeAbon());
                                break;
                            case 2:
                                rh.setText(observableList.get(rowCt-1).getAppFrom1() + "       ");
                                break;
                            case 3:
                                rh.setText(observableList.get(rowCt-1).getTypeCable() + "       ");
                                break;
                            case 4:
                                rh.setText(observableList.get(rowCt-1).getAppFrom2() + "       ");
                                break;
                            case 5:
                                rh.setText(" " + String.valueOf(observableList.get(rowCt-1).getLengthCable()) + " ");
                                break;
                        }
                        //---------------------------The end of switch------------
                    }else {

                        rh.setFontSize(12);
                        rh.setFontFamily("Times New Roman");
                        rh.setBold(true);
                        para.setAlignment(ParagraphAlignment.CENTER);

                        switch (colCt) {
                        case 0:
                            rh.setText("Должностные лица");
                            break;
                        case 1:
                            rh.setText("Тип абонентского устройства");
                            break;
                        case 2:
                            rh.setText("Аппаратная");
                            break;
                        case 3:
                            rh.setText("Тип кабеля");
                            break;
                        case 4:
                            rh.setText("Аппаратная_2");
                            break;
                        case 5:
                            rh.setText("Длина кабеля");
                            break;
                    }}

                    colCt++;
                } // for cell
                colCt = 0;
                rowCt++;
            }// for row

        document.write(out);
        out.close();
        System.out.println("create_table.docx written successully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
