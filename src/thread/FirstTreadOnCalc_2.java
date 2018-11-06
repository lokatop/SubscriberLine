package thread;


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


            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();

            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run.setFontFamily("Times New Roman");
            run.setFontSize(14);

            run.setText("Утверждаю   ");
            run.addBreak();
            run.setText("Звание__ Номер полка/роты и т.д.__ Полк/рота и т.д.   ");
            run.addBreak();
            run.setText("Должность__ И.Фамилия   ");
            run.addBreak();
            run.setText("Дата");
            run.addBreak();
            run.setText("");
            run.addBreak();
            run.setText("");
            run.addBreak();
            run.setText("");
            run.addBreak();

            XWPFParagraph paragraph2 = document.createParagraph();
            XWPFRun run2 = paragraph2.createRun();

            paragraph2.setAlignment(ParagraphAlignment.CENTER);
            run2.setFontFamily("Times New Roman");
            run2.setFontSize(14);

            run2.setText("РАСЧЕТ");
            run2.addBreak();
            run2.setText("развертывания абонентского оборудования на (где) (Номер полка/роты и т.д.__ Полк/рота и т.д.)");
            run2.addBreak();
            run2.setText("");
            run2.addBreak();

            //!!!Вот тут первая и основная таблица создается
            CreateTableMain(observableList,document);
            //-----------------------------------------
            CreateTableAbon(,document);
            //-----------------------------------------
            CreateTableCable(,document);
            //-----------------------------------------
            CreateTableTypeApp(,document);
            //-----------------------------------------

            document.write(out);
            out.close();
            System.out.println("create_table.docx written successully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void CreateTableMain(ObservableList<TheLastTable> observableList, XWPFDocument document){

        // Create a new table with 7 rows and 6 columns
        int nRows = observableList.size()+1;
        int nCols = 5;
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
/*
                    // create cell color element
                    CTShd ctshd = tcpr.addNewShd();
                    ctshd.setColor("auto");
                    ctshd.setVal(STShd.CLEAR);
                    if (rowCt == 0) {
                        // header row
                       // ctshd.setFill("A7BFDE");
                    } else if (rowCt % 2 == 0) {
                        // even row
                        //ctshd.setFill("D3DFEE");
                    } else {
                        // odd row
                        //ctshd.setFill("EDF2F8");
                    }
*/
                // get 1st paragraph in cell's paragraph list
                XWPFParagraph para = cell.getParagraphs().get(0);
                // create a run to contain the content
                XWPFRun rh = para.createRun();


                if(rowCt != 0) {
                    rh.setFontSize(11);
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
                            rh.setText(" " + String.valueOf(observableList.get(rowCt-1).getLengthCable()) + " ");
                            break;
                    }
                    //---------------------------The end of switch------------
                }else {

                    rh.setFontSize(11);
                    rh.setFontFamily("Times New Roman");
                    rh.setBold(false);
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
                            rh.setText("Длина кабеля");
                            break;
                    }}

                colCt++;
            } // for cell
            colCt = 0;
            rowCt++;
        }// for row

    }
    public void CreateTableAbon(, XWPFDocument document){

        // Create a new table with n+1 rows and 2 columns
        int nRows = observableList.size()+1;
        int nCols = 2;
        XWPFTable table2 = document.createTable(nRows, nCols);

        // Get a list of the rows in the table
        List<XWPFTableRow> rows = table2.getRows();
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

                // get 1st paragraph in cell's paragraph list
                XWPFParagraph para = cell.getParagraphs().get(0);
                // create a run to contain the content
                XWPFRun rh = para.createRun();


                if(rowCt != 0) {
                    rh.setFontSize(11);
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
                    }
                    //---------------------------The end of switch------------
                }else {

                    rh.setFontSize(11);
                    rh.setFontFamily("Times New Roman");
                    rh.setBold(true);
                    para.setAlignment(ParagraphAlignment.CENTER);

                    switch (colCt) {
                        case 0:
                            rh.setText("Тип абонентского оборудования");
                            break;
                        case 1:
                            rh.setText("Количество");
                            break;
                    }}

                colCt++;
            } // for cell
            colCt = 0;
            rowCt++;
        }// for row
    }
    public void CreateTableCable(, XWPFDocument document){

        // Create a new table with n+1 rows and 2 columns
        int nRows = observableList.size()+1;
        int nCols = 2;
        XWPFTable table2 = document.createTable(nRows, nCols);

        // Get a list of the rows in the table
        List<XWPFTableRow> rows = table2.getRows();
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

                // get 1st paragraph in cell's paragraph list
                XWPFParagraph para = cell.getParagraphs().get(0);
                // create a run to contain the content
                XWPFRun rh = para.createRun();


                if(rowCt != 0) {
                    rh.setFontSize(11);
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
                    }
                    //---------------------------The end of switch------------
                }else {

                    rh.setFontSize(11);
                    rh.setFontFamily("Times New Roman");
                    rh.setBold(true);
                    para.setAlignment(ParagraphAlignment.CENTER);

                    switch (colCt) {
                        case 0:
                            rh.setText("Тип кабеля");
                            break;
                        case 1:
                            rh.setText("Длина");
                            break;
                    }}

                colCt++;
            } // for cell
            colCt = 0;
            rowCt++;
        }// for row
    }
    public void CreateTableTypeApp(, XWPFDocument document){

        // Create a new table with n+1 rows and 2 columns
        int nRows = observableList.size()+1;
        int nCols = 2;
        XWPFTable table2 = document.createTable(nRows, nCols);

        // Get a list of the rows in the table
        List<XWPFTableRow> rows = table2.getRows();
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

                // get 1st paragraph in cell's paragraph list
                XWPFParagraph para = cell.getParagraphs().get(0);
                // create a run to contain the content
                XWPFRun rh = para.createRun();


                if(rowCt != 0) {
                    rh.setFontSize(11);
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
                    }
                    //---------------------------The end of switch------------
                }else {

                    rh.setFontSize(11);
                    rh.setFontFamily("Times New Roman");
                    rh.setBold(true);
                    para.setAlignment(ParagraphAlignment.CENTER);

                    switch (colCt) {
                        case 0:
                            rh.setText("Тип аппаратной");
                            break;
                        case 1:
                            rh.setText("Количество");
                            break;
                    }}

                colCt++;
            } // for cell
            colCt = 0;
            rowCt++;
        }// for row
    }

}