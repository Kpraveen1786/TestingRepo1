package com.forsys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.schedule.util.TakeScreenShotOfBrowser;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Schedulingsalesorder {

	public WebDriver browser;
	public String Order_Number;
	public String Fulfillment_Number;
	public String Item_Number;
	public String SSD_Update;
	boolean loginsuccess = false;

	Logger logger = Logger.getLogger(Schedulingsalesorder.class);

	@BeforeTest()
	public void Login_Page() throws Exception {
		try {
			logger.info("Initial loginsuccess : " + loginsuccess);
			WebDriverManager.chromedriver().version("92").setup();
			ChromeOptions options = new ChromeOptions();
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			browser = new ChromeDriver(options);
			browser.manage().window().maximize();
			logger.info("Chrome driver started");
			browser.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			browser.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			browser.get("https://elme-dev1.fa.us8.oraclecloud.com");
			browser.findElement(By.id("userid")).click();
			browser.findElement(By.id("userid")).sendKeys("forsys.user");
			browser.findElement(By.id("password")).click();
			browser.findElement(By.id("password")).sendKeys("Boyd2021!");
			browser.findElement(By.id("btnActive")).click();
			JavascriptExecutor js = (JavascriptExecutor) browser;
			for (int i = 0; i < 25; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.info("Page is not loaded");
				}
				// To check page ready state.
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					break;
				}
			}
//			Thread.sleep(12000);
			browser.findElement(By.xpath("//a[text()='You have a new home page!']")).click();
			Thread.sleep(10000);
			browser.findElement(By.linkText("Order Management")).click();
			WebElement order1 = browser.findElement(By.id("itemNode_order_management_order_management_1"));
			WebDriverwaitelement(order1);
			order1.click();
			loginsuccess = true;
			logger.info("loginsuccess in Login_Page(): " + loginsuccess);
		} catch (Exception e) {
			logger.info("Exception occured in Login_Page()");
			e.printStackTrace();
			convertPrintStrackToString(e);
		}

	}

	@Test()
	public void Home_Page() throws Exception {
		boolean flag = false;
		File f = null;
		FileInputStream fis = null;
		Workbook wb = null;
		logger.info("loginsuccess :  " + loginsuccess);
		if (loginsuccess) {
			String filename = getFileName();
			try {
				if (!filename.isEmpty()) {
					logger.info("Script is started");
					logger.info("File path is :" + filename);

//		File f = new File(System.getProperty("user.dir")+"\\Excel\\Scheduling_Salesorder.xlsx");
					f = new File(filename);
					fis = new FileInputStream(f);
					wb = new HSSFWorkbook(fis);
					Sheet sheet = wb.getSheet("Schedulingsalesorder");
					sheet.getRow(0).createCell(4).setCellValue("Result");
					sheet.getRow(0).createCell(5).setCellValue("Comments");
					int totalrows = sheet.getPhysicalNumberOfRows();
					System.out.println("Total number of Excel rows are :" + totalrows);

					if (sheet.getRow(1).getCell(4) == null) {

						for (int i = 1; i <= totalrows; i++) {

							if (sheet.getRow(i) == null) {
								break;
							}

							Order_Number = sheet.getRow(i).getCell(0).getStringCellValue();
							Fulfillment_Number = sheet.getRow(i).getCell(1).getStringCellValue();
							Item_Number = sheet.getRow(i).getCell(2).getStringCellValue();
							SSD_Update = sheet.getRow(i).getCell(3).getStringCellValue();

							Thread.sleep(4000);
							WebElement task = browser.findElement(By.linkText("Tasks"));							
							WebDriverwaitelement(task);
							task.click();
							WebElement fulfillment = browser
									.findElement(By.xpath("//td[text()='Manage Fulfillment Lines']"));
							WebDriverwaitelement(fulfillment);
							fulfillment.click();
							WebElement el = browser.findElement(By.xpath("//*[contains(@id,'value20::content')]"));
							WebDriverwaitelement(el);
							el.click();
							Select sc = new Select(
									browser.findElement(By.xpath("//*[contains(@id,'operator2::content')]")));
							sc.selectByVisibleText("Equals");
							Thread.sleep(3000);
							browser.findElement(By.xpath("//*[contains(@id,'value20::content')]")).click();
							browser.findElement(By.xpath("//*[contains(@id,'value20::content')]"))
									.sendKeys(Order_Number);
							Thread.sleep(2000);
							browser.findElement(By.xpath("//*[contains(@id,'value30::content')]")).click();
							browser.findElement(By.xpath("//*[contains(@id,'value30::content')]"))
									.sendKeys(Fulfillment_Number);
							Thread.sleep(3000);
							browser.findElement(By.xpath("//*[contains(@id,'value50::content')]")).click();
							browser.findElement(By.xpath("//*[contains(@id,'value50::content')]"))
									.sendKeys(Item_Number);
							Thread.sleep(3000);
							browser.findElement(By.xpath("//*[contains(@id,'q1::search')]")).click();
							try {
								WebElement table = browser
										.findElement(By.xpath("//*[contains(@id,'ATt1::db')]/table/tbody/tr/td[1]"));
								WebDriverwaitelement(table);
								table.click();
								Thread.sleep(3000);
								WebElement edit = browser.findElement(By.xpath("//*[contains(@id,'edit::icon')]"));
								JavascriptExecutor js = (JavascriptExecutor) browser;
								js.executeScript("arguments[0].click();", edit);
								Thread.sleep(8000);
								Select overide = new Select(browser
										.findElement(By.xpath("//*[contains(@id,'overrideScheduleDate::content')]")));
								overide.selectByVisibleText("Yes");
								Thread.sleep(6000);
								browser.findElement(By.xpath("//*[contains(@id,'id1::content')]")).click();
								browser.findElement(By.xpath("//*[contains(@id,'id1::content')]")).sendKeys(SSD_Update);
								Thread.sleep(3000);
								browser.findElement(By.xpath("//*[contains(@id,'FulSAP:AT1:cb4')]")).click();
								try {
									WebElement okbutton = browser
											.findElement(By.xpath("//*[contains(@id,'FulSAP:AT1:d9::ok')]"));
									WebDriverwaitelement(okbutton);
									okbutton.click();
									Thread.sleep(3000);
									WebElement refresh = browser.findElement(By.xpath("//button[text()='Refresh']"));
									WebDriverwaitelement(refresh);
									refresh.click();
									Thread.sleep(4000);
									browser.findElement(By.xpath("//button[text()='Refresh']")).click();
									Thread.sleep(3000);
									browser.findElement(By.xpath("//button[text()='Refresh']")).click();
									Thread.sleep(6000);
									browser.findElement(By.xpath("//*[contains(@id,'FulSAP:cb1')]")).click();
									sheet.getRow(i).createCell(4).setCellValue("Pass");
									Updatefile(f, wb);

								} catch (Exception e) {
									browser.findElement(By.id("d1::msgDlg::cancel")).click();
									WebElement cancel = browser
											.findElement(By.xpath("//*[contains(@id,'d3::cancel')]"));
									WebDriverwaitelement(cancel);
									cancel.click();
									WebElement done = browser.findElement(By.xpath("//*[contains(@id,'FulSAP:cb1')]"));
									WebDriverwaitelement(done);
									done.click();
									sheet.getRow(i).createCell(4).setCellValue("Fail");
									sheet.getRow(i).createCell(5).setCellValue(
											"You cannot set the scheduled ship date to a date prior to today.");
									Updatefile(f, wb);
								}
							} catch (Exception e) {
								browser.findElement(By.xpath("//*[contains(@id,'FulSAP:cb1')]")).click();
								sheet.getRow(i).createCell(4).setCellValue("Fail");
								sheet.getRow(i).createCell(5)
										.setCellValue("Order has no data or edit button is in disablemode");
								Updatefile(f, wb);

							}
						}
					} else {
						System.out.println("File is already processed");
						logger.info("File is processed");
					}
				}
			} catch (Exception e) {
				flag = true;
				e.printStackTrace();
				convertPrintStrackToString(e);
				logger.info("Script is failed");
			}
			if (wb != null) {
				wb.close();
			}
			if (fis != null) {
				fis.close();
			}

			logger.info("flag : " + flag);
			if (flag) {
				logger.info("Error folder");
				logger.info(TestRunner.processed_folder_path + f.getName());
				try {
					Files.move(Paths.get(TestRunner.processed_folder_path + f.getName()),
							Paths.get(TestRunner.error_folder_path + f.getName()), StandardCopyOption.REPLACE_EXISTING);
					Email email = new Email("File upload status",
							"The provided file was errored out. Please find the attachment for more details",
							TestRunner.error_folder_path + f.getName());
					email.sendEmail();
				} catch (Exception e) {
					e.printStackTrace();
					convertPrintStrackToString(e);
				}
				File f1 = new File(TestRunner.loggerfilepath);
				String logmessage = readFromLast(f1, 1000);
//				String screenshot = TakeScreenShotOfBrowser.takeScreenShot(browser);
//				Email email = new Email();
//				email.sendErrorEmail(logmessage, screenshot);
//				Files.deleteIfExists(Paths.get(screenshot));
			} else {
				logger.info("Success folder");
				logger.info(TestRunner.processed_folder_path + f.getName());
				try {
					Files.move(Paths.get(TestRunner.processed_folder_path + f.getName()),
							Paths.get(TestRunner.success_folder_path + f.getName()),
							StandardCopyOption.REPLACE_EXISTING);
					Email email = new Email("File upload status",
							"The provided file was processed successfully. Please find the attachment",
							TestRunner.success_folder_path + f.getName());
					email.sendEmail();

				} catch (Exception e) {
					convertPrintStrackToString(e);
				}
			}
		}

	}

	public void WebDriverwaitelement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(browser, 350);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void Updatefile(File f, Workbook wb) {
		try {
			FileOutputStream fos = new FileOutputStream(f);
			wb.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			convertPrintStrackToString(e);
		}
	}

	@AfterTest()
	public void Close_browser() {
		if (browser != null) {
			browser.quit();
			logger.info("browser is closed");
		}
	}

	public String getFileName() {
		try {
			File f = new File(TestRunner.unprocessed_folder_path);
			String filename = f.getAbsolutePath();
			String name = TestRunner.filename;
			Files.move(Paths.get(filename), Paths.get(TestRunner.processed_folder_path + name),
					StandardCopyOption.REPLACE_EXISTING);
			return TestRunner.processed_folder_path + name;
		} catch (Exception e) {
			e.printStackTrace();
			convertPrintStrackToString(e);
		}
		return "";
	}

	public void convertPrintStrackToString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string
		logger.info(sStackTrace);
	}

	// Read n lines from the end of the file
	public String readFromLast(File file, int lines) {
		int readLines = 0;
		StringBuilder builder = new StringBuilder();
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
			long fileLength = file.length() - 1;
			// Set the pointer at the last of the file
			randomAccessFile.seek(fileLength);
			for (long pointer = fileLength; pointer >= 0; pointer--) {
				randomAccessFile.seek(pointer);
				char c;
				// read from the last one char at the time
				c = (char) randomAccessFile.read();
				// break when end of the line
				if (c == '\n') {
					readLines++;
					if (readLines == lines)
						break;
				}
				builder.append(c);
			}
			// Since line is read from the last so it
			// is in reverse so use reverse method to make it right
			builder.reverse();
//	    System.out.println("Line - " + builder.toString());
//	    System.out.println("----------------------------------");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

}
