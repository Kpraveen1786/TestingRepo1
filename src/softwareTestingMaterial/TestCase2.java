package softwareTestingMaterial;
import org.testng.annotations.Test;
public class TestCase2 {
	@Test (groups = { "functionalTest" })
	public void composeMail(){
		System.out.println("Mail Sent");
	}
}