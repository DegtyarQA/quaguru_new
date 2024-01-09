package com.degtyar;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.degtyar.model.MenuProgram;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class FileTests {

    ClassLoader cl = FileTests.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Проверка PDF")
    void checkZipPdfTest() throws Exception {
        try (
                InputStream stream = cl.getResourceAsStream("ReferenceCard.zip");
                ZipInputStream file = new ZipInputStream(stream)
        ) {
            ZipEntry entry;
            while ((entry = file.getNextEntry()) != null) {
                PDF pdf = new PDF(file);
                assertThat(pdf.text).contains("Recent files popup");
            }
        }
    }

    @Test
    void checkXLSZipText() throws Exception {
        try (
                InputStream st = cl.getResourceAsStream("reut.zip");
                ZipInputStream unzipread = new ZipInputStream(st)
        ) {
            ZipEntry entryXlsx;
            while ((entryXlsx = unzipread.getNextEntry()) != null) {
                XLS xlsx_file = new XLS(unzipread);
                assertThat(xlsx_file.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).contains("degtyar");
                assertThat(xlsx_file.excel.getActiveSheetIndex()).isEqualTo(0);
                assertThat(xlsx_file.excel.getSheetName(0)).contains("Лист1");
            }
        }
    }

    @Test
    void checkCsvZip() throws Exception {
        try (
                InputStream st2 = cl.getResourceAsStream("testData.zip");
                ZipInputStream csv = new ZipInputStream(st2);
        ) {
            ZipEntry entryCsv;
            while ((entryCsv = csv.getNextEntry()) != null) {
                CSVReader csvReader = new CSVReader(new InputStreamReader(csv));
                List <String[]> str = csvReader.readAll();
                assertThat(str.get(1)[0]).contains("kurak");
                assertThat(str.get(0)[0]).isEqualTo("werd");
            }
        }
    }

    @Test
    void parseJson() throws IOException {
        File fileJson = new File("src/test/resources/MenuProgram.json");
        MenuProgram menuProgram = objectMapper.readValue(fileJson, MenuProgram.class);
        assertThat(menuProgram.menu.id).isEqualTo("file");
        assertThat(menuProgram.menu.popup.menuitem[0].onclick).isEqualTo("CreateNewDoc()");
    }

    @Test
    void downLoadPdf() throws Exception{
        open("https://solva.kz/docs/");
        File downloadPDF = $(".docs__list li a").download();
        PDF pdf = new PDF(downloadPDF);
        assertThat(pdf.numberOfPages).isEqualTo(35);
        assertThat(pdf.creator).isEqualTo("HP Scan");

    }
}