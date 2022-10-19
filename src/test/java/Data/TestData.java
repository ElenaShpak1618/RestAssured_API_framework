package Data;

import org.testng.annotations.DataProvider;

public class TestData {
	
	@DataProvider(name="iso")
	
	public Object[][] getCountysVariant() {
		
		return new Object[][] {
	
	
	{"AND","ANDORRA"},
	{"AFG","AFGHANISTAN"},
	{"AIA","ANGUILLA"},
	{"ALB","ALBANIA"},
	{"ARM","ARMENIA"}
		};
	
	

		}
	}		
