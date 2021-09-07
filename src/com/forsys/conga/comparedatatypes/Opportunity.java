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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Opportunity {

	public List<Model> readInstanceDetails(String filePath, String fileName, String sheetName) throws IOException {

		List<Model> listOfObjects = new ArrayList<>();
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
			Model model = new Model();
			model.setField(sheet.getRow(i).getCell(0).getStringCellValue());
			String datatype = sheet.getRow(i).getCell(1).getStringCellValue();
			if (datatype == null) {
				datatype = "";
			}
			model.setDatatype(datatype);
			Cell cell = sheet.getRow(i).getCell(2);
			String picklist = "";
			if (cell != null) {
				picklist = cell.getStringCellValue();
			}
			List<String> picklistvalues = new ArrayList<>();
			if (datatype.equals("Picklist") || datatype.equals("Picklist (Multi-Select)")) {
				if (picklist.contains("<->")) {
//					System.out.println("contains");
					for (String value : picklist.split("<->")) {
						if (!value.isEmpty()) {
							picklistvalues.add(value);
						}
					}

				} else if (picklist.contains(",")) {
					for (String value : picklist.split(",")) {
						if (!value.isEmpty()) {
							picklistvalues.add(value);
						}
					}
				}

			}
			model.setPicklist(picklistvalues);

			listOfObjects.add(model);
		}

		workbook.close();
		inputStream.close();
//		for (Model m : listOfObjects) {
//			System.out.println(m.getField() + " ---  " + m.getDatatype() + "  -----  " + m.getPicklist()+"  size = "+m.getPicklist().size());
//			if(m.getDatatype().equals("Picklist (Multi-Select)")) {
//				for(String pick : m.getPicklist()) {
//					System.out.println(pick);
//				}
//			}
//			System.out.println("----------------------------");
//		}
		return listOfObjects;
	}
	
	public void updateSheet(String filePath, String fileName, String sheetName, Model prodModel, Model uatModel, int rownum, String result)throws IOException {
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
		Row row = sheet.getRow(rownum);
		row.createCell(0).setCellValue(prodModel.getField());
		row.createCell(1).setCellValue(prodModel.getDatatype());
		row.createCell(2).setCellValue(result);
		row.createCell(3).setCellValue(uatModel.getField());
		row.createCell(4).setCellValue(uatModel.getDatatype());
		row.createCell(5).setCellValue(result);
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

	public static void main(String[] args) throws IOException {
		Opportunity obj = new Opportunity();
		String filePath = "D:\\CONGA_OBJECTS";
		String filename = "Opportunity_c1uat_c2prod.xlsx";

		// Call read file method of the class to read data
		String[] sheetnames = { "C1_UAT", "C2_PROD", "DATATYPES_COMPARE" };

		List<Model> listOfC1UatObjects = null;
		List<Model> listOfC2ProdObjects = null;

		for (String sheetname : sheetnames) {
			if (sheetname.equals("C1_UAT")) {
				listOfC1UatObjects = obj.readInstanceDetails(filePath, filename, sheetname);
			} else if (sheetname.equals("C2_PROD")) {
				listOfC2ProdObjects = obj.readInstanceDetails(filePath, filename, sheetname);
			}
		}

//		List<Model> commonElements = new ArrayList<>(listOfC2ProdObjects);
//		commonElements.retainAll(listOfC1UatObjects);
//		System.out.println(commonElements.size());

//		commonElements.clear();
//		List<Model> commonElements1 = new ArrayList<>(listOfC1UatObjects);
//		commonElements1.retainAll(listOfC2ProdObjects);
//		System.out.println(commonElements1.size());
		int count = 0;
		int exceptPickList = 0;
		for (Model uat : listOfC1UatObjects) {
			for (Model prod : listOfC2ProdObjects) {
				boolean fieldfound = false;
				boolean datatypefound = false;
				if (uat.getField().equals(prod.getField())) {
					fieldfound = true;
//					System.out.println(uat.getField());
//					count = count +1;
				}
				if (fieldfound && uat.getDatatype().equals(prod.getDatatype())) {
//					count = count + 1;
					datatypefound = true;
				}
				if (datatypefound) {
					int prodsize = prod.getPicklist().size();
					int uatsize = uat.getPicklist().size();
//					System.out.println("size = "+prodsize+" -------------  "+  uatsize);
					if ((prod.getDatatype().equals("Picklist") || prod.getDatatype().equals("Picklist (Multi-Select)"))
							&& prod.getPicklist().size() == uat.getPicklist().size()) {
						
//						System.out.println("count = "+count+"     "+"size = " + prodsize + " -------------  " + uatsize + "  -----"
//								+ uat.getPicklist().containsAll(prod.getPicklist()) + " prod = " + prod.getPicklist()
//								+ "  uat = " + uat.getPicklist());

						List<String> prodlist = prod.getPicklist();
						Set<String> s1 = new HashSet<>();
						for (String s3 : prodlist) {
							s1.add(s3);
						}

//						s1.addAll(prodlist);
						List<String> uatlist = uat.getPicklist();
						Set<String> s2 = new HashSet<>();
						for (String s4 : uatlist) {
							s2.add(s4);
						}

//						s2.addAll(uatlist);

						System.out.println(s1.containsAll(s2));
						count = count + 1;
						obj.updateSheet(filePath, filename,  sheetnames[2], prod, uat, count, String.valueOf(s1.containsAll(s2)).toUpperCase());
					} else if ((prod.getDatatype().equals("Picklist") || prod.getDatatype().equals("Picklist (Multi-Select)"))
							){
//						System.out.println("count = "+count+"     "+"not equal to picklist size"+"size = " + prodsize + " -------------  " + uatsize + "  -----"
//								+ uat.getPicklist().containsAll(prod.getPicklist()) + " prod = " + prod.getPicklist()
//								+ "  uat = " + uat.getPicklist());
						count = count+1;
						obj.updateSheet(filePath, filename,  sheetnames[2], prod, uat, count, "FALSE");
					}else {
						
						count = count + 1;
						exceptPickList = exceptPickList+1;
//						System.out.println("count = "+count+"     "+prod.getField() +" --->  "+prod.getDatatype()+ "======" +uat.getField() +" --->  "+uat.getDatatype());
//						System.out.println(prod.getField()+" "+prod.getDatatype()+" TRUE "+uat.getField()+"  "+uat.getDatatype()+" TRUE");
						obj.updateSheet(filePath, filename,  sheetnames[2], prod, uat, count,"TRUE");
					}
//					System.out.println(prod.getPicklist().retainAll(uat.getPicklist())+"   prodsize   "+prodsize+"  uatsize   "+uatsize);
				}
			}
		}
		
		

		System.out.println("count = " + count);
		System.out.println("exceptPickList = "+exceptPickList);
	}
}
