package com.forsys.conga.comparedatatypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//=IF(AND(B2<>"",C2<>"",B2=C2),"True"," False")

public class Opportunity2 {
	
	public void setCellValueColor(Font font, CellStyle cellStyle, Sheet sheet, Row row, String trueOrFalse, String color) {
		cellStyle.setFont(font);
		if(color.equalsIgnoreCase("red")) {
			font.setColor(IndexedColors.RED.getIndex());
		}else {
			font.setColor(IndexedColors.GREEN.getIndex());
		}
		row.createCell(3).setCellStyle(cellStyle);
		Cell cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(trueOrFalse);
		sheet.autoSizeColumn(3);
	}

	public void updateEmptyValuesWithDataTypes(String filePath, String fileName, String sheetName, int rownum,
			String datatype, int cellnumber) {
		try {
			File file = new File(filePath + "\\" + fileName);
			FileInputStream inputStream = new FileInputStream(file);

			Workbook workbook = null;
			String fileExtensionName = fileName.substring(fileName.indexOf("."));
			if (fileExtensionName.equals(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (fileExtensionName.equals(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			}
			Sheet sheet = workbook.getSheet(sheetName);
			sheet.getRow(rownum).createCell(cellnumber).setCellValue(datatype);
			Font font = workbook.createFont();
			CellStyle cellStyle = workbook.createCellStyle();

			if (!sheet.getRow(rownum).getCell(cellnumber).getStringCellValue().equals("") && sheet.getRow(rownum).getCell(1).getStringCellValue().equals("")) {
				setCellValueColor(font, cellStyle, sheet, sheet.getRow(rownum), "False", "red");
			}
			sheet.autoSizeColumn(cellnumber);
			try {
				FileOutputStream out = new FileOutputStream(file);
				workbook.write(out);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out
						.println("Error occured while setting default values in makeEmptyValuesOfMappingFieldsSheet()");
			}
			workbook.close();
			inputStream.close();
		} catch (Exception e) {

		}
	}

	public void updateDataTypeValuesOfMappingFieldsSheet(String filePath, String fileName, String sheetName,
			List<Model2> listOfMappingfileds, List<Model2> listOfC1Objects, List<Model2> listOfA1Objects)
			throws IOException {
		Set<String> notFoundFields = new HashSet<>();
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = workbook.getSheet(sheetName);
		for (Model2 mappingfield : listOfMappingfileds) {
			boolean c1exists = false;
			for (Model2 a1Model : listOfA1Objects) {
				if (mappingfield.getField().equals(a1Model.getField())) {
					for (Model2 c1Model : listOfC1Objects) {
						if (mappingfield.getField().equals(c1Model.getField())) {
							c1exists = true;
							Row row = sheet.getRow(mappingfield.getRownum());
							if (c1Model.getDatatype() == null || c1Model.getDatatype().equals("")) {
								row.createCell(1).setBlank();
							} else {
								row.createCell(1).setCellValue(c1Model.getDatatype());
							}
							if (a1Model.getDatatype() == null || a1Model.getDatatype().equals("")) {
								row.createCell(2).setBlank();
							} else {
								row.createCell(2).setCellValue(a1Model.getDatatype());
							}

							Font font = workbook.createFont();
							CellStyle cellStyle = workbook.createCellStyle();

							if (c1Model.getDatatype().equals(a1Model.getDatatype())) {
								setCellValueColor(font, cellStyle, sheet, row, "True", "green");
							} else {
								setCellValueColor(font, cellStyle, sheet, row, "False", "red");
							}
							cellStyle.setFont(font);
							sheet.autoSizeColumn(1);
							sheet.autoSizeColumn(2);
							break;
						}
					}
					if (c1exists) {
						break;
					} else {
						notFoundFields.add(mappingfield.getField());
					}
				}
			}

		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occured while setting default values in makeEmptyValuesOfMappingFieldsSheet()");
		}
		workbook.close();
		inputStream.close();
//		System.out.println(notFoundFields.size());
	}

	public void makeEmptyValuesOfMappingFieldsSheet(String filePath, String fileName, String sheetName)
			throws IOException {
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		for (int i = 1; i < rowCount + 1; i++) {

			Row row = sheet.getRow(i);
			row.createCell(1).setBlank();
			row.createCell(2).setBlank();

		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occured while setting default values in makeEmptyValuesOfMappingFieldsSheet()");
		}
		workbook.close();
		inputStream.close();
	}

	public List<Model2> readMappingFields(String filePath, String fileName, String sheetName) throws IOException {
		List<Model2> listOfMappingFields = new ArrayList<>();
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		for (int i = 1; i < rowCount + 1; i++) {
			Model2 model = new Model2();
			Row row = sheet.getRow(i);
			model.setField(row.getCell(0).getStringCellValue());
			if (row.getCell(1) == null || row.getCell(1).getStringCellValue() == null
					|| row.getCell(1).getStringCellValue().equals("")) {
				model.setC1DataType("");
			} else {
				model.setC1DataType(row.getCell(1).getStringCellValue());
			}
			if (row.getCell(2) == null || row.getCell(2).getStringCellValue() == null
					|| row.getCell(2).getStringCellValue().equals("")) {
				model.setA1DataType("");
			} else {
				model.setA1DataType(row.getCell(2).getStringCellValue());
			}

			model.setRownum(i);
			listOfMappingFields.add(model);

		}

		workbook.close();
		inputStream.close();
		return listOfMappingFields;
	}

	public List<Model2> readInstanceDetails(String filePath, String fileName, String sheetName) throws IOException {
		List<Model2> listOfObjects = new ArrayList<>();
		File file = new File(filePath + "\\" + fileName);

		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);

		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		for (int i = 0; i < rowCount + 1; i++) {
			Model2 model = new Model2();

			Row row = sheet.getRow(i);

			String field = row.getCell(1).getStringCellValue();
			String datatype = row.getCell(2).getStringCellValue();
			model.setField(field);
			model.setDatatype(datatype);
			listOfObjects.add(model);

		}

		workbook.close();
		inputStream.close();
		return listOfObjects;
	}

	public static void main(String[] args) throws IOException {

		Opportunity2 obj = new Opportunity2();
		String filePath = "D:\\CONGA_OBJECTS";
		String filename = "Opportunity.xlsx";

		// Call read file method of the class to read data
		String[] sheetnames = { "C1_UAT", "A1_UAT", "Mapping_fields" };
		List<Model2> listOfMappingfileds = null;
		List<Model2> listOfC1Objects = null;
		List<Model2> listOfA1Objects = null;
		for (String sheetname : sheetnames) {
			if (sheetname.equals("Mapping_fields")) {
				obj.makeEmptyValuesOfMappingFieldsSheet(filePath, filename, sheetname);
				listOfMappingfileds = obj.readMappingFields(filePath, filename, sheetname);
				obj.updateDataTypeValuesOfMappingFieldsSheet(filePath, filename, sheetname, listOfMappingfileds,
						listOfC1Objects, listOfA1Objects);
				listOfMappingfileds = obj.readMappingFields(filePath, filename, sheetname);
				int count = 0;
				for (Model2 m : listOfMappingfileds) {
					if (m.getC1DataType().equals("") && m.getA1DataType().equals("")) {
						for (Model2 a1model : listOfA1Objects) {
							if (m.getField().equals(a1model.getField())) {
								obj.updateEmptyValuesWithDataTypes(filePath, filename, sheetname, m.getRownum(),
										a1model.getDatatype(), 2);
								break;
							}
						}
					}

					count = count + 1;
				}
				System.out.println("count = " + count + "    " + listOfMappingfileds.size());
			} else if (sheetname.equals("C1_UAT")) {
				listOfC1Objects = obj.readInstanceDetails(filePath, filename, sheetname);
			} else if (sheetname.equals("A1_UAT")) {
				listOfA1Objects = obj.readInstanceDetails(filePath, filename, sheetname);
			}
		}


	}

}
