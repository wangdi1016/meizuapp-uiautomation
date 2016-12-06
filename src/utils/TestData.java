package utils;

public class TestData {

	private String caseName;
	private String query;
	private String expectedFirstCard;
	
	public TestData() {
	}
	
	public String getCaseName() {
		return this.caseName;
	}
	
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	public String getQuery() {
		return this.query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getExpectedFirstCard() {
		return this.expectedFirstCard;
	}
	
	public void setExpectedFirstCard(String expectedFirstCard) {
		this.expectedFirstCard = expectedFirstCard;
	}
	
	public String toString() {
		return this.caseName + "-" +this.query;
	}
	
}
