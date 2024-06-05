
// Test stukjes code hier
public class Kladblok {
	
	public void blad() {
		
		String postcodeRegex = "\\b\\d{4}[A-Za-z]{2}\\b";
		boolean b = postcodeRegex.matches("2929KD");
		System.out.println(b);
	}
	
	public static void main(String[] args) {
		new Kladblok().blad();
	  }
}
