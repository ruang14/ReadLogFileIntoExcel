/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readlogfileintoexcel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author ruang
 */
public class ReadLogFileIntoExcel {

    public static void main(String[] args) {
        try {
            readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readFile() {
        BufferedReader br = null;
        StringBuilder s = null;
        List<EntitiesAndErrors> list = new ArrayList();
        String coverNumber = "";
        String email = "";
        String error = "";
        String line = "";
        String conLine = "";

        try {
            br = new BufferedReader(new FileReader("C:\\Users\\RuanG\\Documents\\NetBeansProjects\\ReadLogFileIntoExcel\\reasons.txt"));
            while ((s = new StringBuilder(br.readLine())) != null) {
                try {
                    line = s.toString();
                    System.out.println("line: " + line);
                    EntitiesAndErrors er = new EntitiesAndErrors();

                    if (line.contains(" | ")) {
                        try {
                            line.substring(0, line.indexOf(" | ")).trim();
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        
                        coverNumber = line.substring(0, line.indexOf(" | ")).trim();
                        conLine = line.substring(line.indexOf(" | ") + 2).trim();
                        if (conLine.contains(" - ")) {
                            email = conLine.substring(0, conLine.indexOf(" - ")).trim();
                            error = conLine.substring(conLine.indexOf(" - ") + 2).trim();
                            System.out.println(coverNumber + " | " + email + " | " + error);

                            er.setEntityCoverNumber(coverNumber);
                            er.setEntityEmail(email);
                            er.setEntityError(error);
                        } else {
                            er.setEntityCoverNumber(coverNumber);
                            er.setEntityEmail("");
                            er.setEntityError("Invalid Email Address");
                        }

                        list.add(er);
                    } else {
                        continue;
                    }
                } catch (Exception e) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("erList: " + list.size());
        writeToExcel(list);
    }

    private static void writeToExcel(List<EntitiesAndErrors> list) {
        String[] headers = {"CoverNumber", "Email", "Error"};

        try {
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("Sheet 1");
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            setBoldHeaders(wb, headerRow);

            Row row;
            int rowNumber = 1;
            for (int i = 0; i < list.size(); i++) {
                int cellNumber = 0;
                row = sheet.createRow(rowNumber++);
                row.createCell(cellNumber++).setCellValue(list.get(i).getEntityCoverNumber());
                row.createCell(cellNumber++).setCellValue(list.get(i).getEntityEmail());
                row.createCell(cellNumber++).setCellValue(list.get(i).getEntityError());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream fsOut = new FileOutputStream("C:\\Users\\RuanG\\Documents\\NetBeansProjects\\ReadLogFileIntoExcel\\" + "46817.xls");
            wb.write(fsOut);
            fsOut.close();

            File file = new File("C:\\Users\\RuanG\\Documents\\NetBeansProjects\\ReadLogFileIntoExcel\\" + "46817.xls");
            System.out.println("File = " + file);
            InputStream streamIn = new FileInputStream(file);
            int contentLength = (int) file.length();
            ByteArrayOutputStream tempOutput;

            if (contentLength != -1) {
                tempOutput = new ByteArrayOutputStream(contentLength);
            } else {
                tempOutput = new ByteArrayOutputStream(20480);
            }

            byte[] buffer = new byte[512];
            System.out.println("File downloading");

            while (true) {
                int readFile = streamIn.read(buffer);
                if (readFile == -1) {
                    break;
                }

                tempOutput.write(buffer, 0, readFile);
            }
            streamIn.close();

            System.out.println("File Exported");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setBoldHeaders(Workbook wb, Row headerRow) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerRow.getCell(i).setCellStyle(style);
        }
    }

}
