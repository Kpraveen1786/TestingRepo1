package softwareTestingMaterial;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNGAnnotationTypesTest {
	
	 
	@BeforeTest
	public void beforeTest() {
		System.out.println("beforeTest");
		try {
			int i = 1/0;
		}catch (Exception e) {
			System.out.println("Catch block");
		}finally {
			System.out.println("finally block");
		}
		String str = "Hello";
		boolean bool = str.contentEquals("Hello");
		System.out.println(bool);
		
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println("After Test");
	}
	
	@Test
	public void test() {
		System.out.println("Test");
	}
	
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Before Method");
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("After Method");
	}
	
	@BeforeClass
	public void beforeClass() {
		System.out.println("Before class");
	}
	
	@AfterClass
	public void afterClass() {
		System.out.println("After Class");
	}
	
	@Test
	public void test1() {
		System.out.println("test1");
	}
}
