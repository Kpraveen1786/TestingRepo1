package com.forsys;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.TestNG;

public class TestRunner {
	public static String loggerfilepath = "";
	static {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
		String strDate = formatter.format(date);
		String file_name = "application" + strDate;
		file_name = file_name.replace(":", "-").replace(" ", "-");
		System.setProperty("application", file_name);
		try {
			String jarfilepath = new File(TestRunner.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getPath();

			jarfilepath = jarfilepath.substring(0, jarfilepath.lastIndexOf("\\"));
			loggerfilepath = jarfilepath + "\\" + file_name;
			System.out.println("jarfilepath = " + jarfilepath);
		} catch (Exception e) {

		}
	}

	public static TestNG testNg;
	public static String unprocessed_folder_path = "D:\\E2E Automation\\Harmonic\\";
	public static String processed_folder_path = "D:\\E2E Automation\\Harmonic\\";
	public static String success_folder_path = "D:\\E2E Automation\\Harmonic\\";
	public static String error_folder_path = "D:\\E2E Automation\\Harmonic\\";
	public static String filename = "";
	public static String folderpath = "";

	public static void main(String[] args) throws ClassNotFoundException, URISyntaxException {
		System.out.println("servicename : " + args[0]);
		System.out.println("filename : " + args[1]);
		System.out.println("loggerfilepath :  " + loggerfilepath);
		unprocessed_folder_path = unprocessed_folder_path + args[0] + "\\" + "Unprocess" + "\\" + args[1];
		processed_folder_path = processed_folder_path + args[0] + "\\" + "Process" + "\\";
		success_folder_path = success_folder_path + args[0] + "\\" + "Success" + "\\";
		error_folder_path = error_folder_path + args[0] + "\\" + "Error" + "\\";
		System.out.println("unprocessed_folder_path:  " + unprocessed_folder_path);
		System.out.println("processed_folder_path : " + processed_folder_path);
		System.out.println("success_folder_path : " + success_folder_path);
		System.out.println("error_folder_path : " + error_folder_path);
		try {
//			for (i = 0; i < 2; i++) {
//				try {
//					File f = new File(unprocessed_folder_path);
//					String filename = "";
//					for (File file : f.listFiles()) {
//						filename = file.getAbsolutePath();						
//						break;
//					}
//					if (!filename.isEmpty()) {						
//						TestRunner.testNg = new TestNG();
//						SuiteXmlParser parser = new SuiteXmlParser();
//						String file = "testng.xml";
//						XmlSuite xmlSuite = parser.parse(file, new FileInputStream(file), true);
//						TestRunner.testNg.setXmlSuites(Collections.singletonList(xmlSuite));

//						TestRunner.testNg.setTestClasses(new Class[] { Schedulingsalesorder.class});
//						ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
//						TestRunner.testNg.setXmlSuites(new Parser(is).parseToList());
//						TestRunner.testNg.run();
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				i=0;
//				Thread.sleep(60000);

			filename = args[1];
			TestRunner.testNg = new TestNG();
			TestRunner.testNg.setTestClasses(new Class[] { Schedulingsalesorder.class });
			TestRunner.testNg.run();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void checkFolderExistsOrNot(String servicename) {
		new File(unprocessed_folder_path + "\\" + servicename).mkdirs();
		new File(unprocessed_folder_path + "\\" + servicename + "\\" + "Unprocess").mkdirs();
		new File(unprocessed_folder_path + "\\" + servicename + "\\" + "Success").mkdirs();
		new File(unprocessed_folder_path + "\\" + servicename + "\\" + "Process").mkdirs();
		new File(unprocessed_folder_path + "\\" + servicename + "\\" + "Error").mkdirs();
	}

}
