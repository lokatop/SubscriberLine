package treads;

import javafx.collections.ObservableList;
import model.TheLastTable;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;

public class FirstTreadOnCalc extends Thread {
    public void run(ObservableList<TheLastTable> observableList) {
        //Blank Document
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("create_table.docx"));


        //create table
        XWPFTable table = document.createTable();

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
            //create second row
        XWPFTableRow tableRowTwo = table.createRow();
        //create third row
        XWPFTableRow tableRowThree = table.createRow();
            //create four row
        XWPFTableRow tableRowFour = table.createRow();
        //create five row
        XWPFTableRow tableRowFive = table.createRow();
        //create six row
        XWPFTableRow tableRowSix = table.createRow();
        tableRowOne.getCell(0).setText("Должностные лица");
        tableRowOne.addNewTableCell().setText("Тип абонентского устройства");
        tableRowOne.addNewTableCell().setText("Аппаратная, из состава которой абонентское устройство");
        tableRowOne.addNewTableCell().setText("Тип кабеля");
        tableRowOne.addNewTableCell().setText("Аппаратная, из состава которой кабель");
        tableRowOne.addNewTableCell().setText("Длина кабеля");
        String a = observableList.size()+"";

        for (int i = 1; i < observableList.size(); i++) {
            XWPFTableRow tableR = table.createRow();
            tableR.getCell(i-1).setText(observableList.get(i-1).getOfficialPerson());
            tableR.addNewTableCell().setText(observableList.get(i-1).getTypeAbon());
            tableR.addNewTableCell().setText(observableList.get(i-1).getAppFrom1());
            tableR.addNewTableCell().setText(observableList.get(i-1).getTypeCable());
            tableR.addNewTableCell().setText(observableList.get(i-1).getAppFrom2());
            tableR.addNewTableCell().setText(observableList.get(i-1).getLengthCable()+"");

        }


        //tableRowTwo.getCell(0).setText("col one, row two");
        //tableRowTwo.getCell(1).setText("col two, row two");
        //tableRowTwo.getCell(2).setText("col three, row two");
        //tableRowThree.getCell(0).setText("col one, row three");
        //tableRowThree.getCell(1).setText("col two, row three");
        //tableRowThree.getCell(2).setText("col three, row three");

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
