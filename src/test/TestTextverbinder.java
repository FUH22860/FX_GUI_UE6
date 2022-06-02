package test;

import model.TextException;
import model.Textverbinder;

public class TestTextverbinder {

	public static void main(String[] args) 
	{
		try
		{
//			Textverbinder tv01 = new Textverbinder(null, null); // Fehler null
//			Textverbinder tv02 = new Textverbinder("", null); // Fehler null
//			Textverbinder tv03 = new Textverbinder("", ""); // Fehler ungueltig

			Textverbinder tv0 = new Textverbinder(" ", " "); // ok
			System.out.println(tv0);
			System.out.println();
			
			Textverbinder tv1 = new Textverbinder("Java", "FX"); // ok
			System.out.println(tv1);
			System.out.println();
			
			Textverbinder tv2 = new Textverbinder("kein", "Problem"); // ok	
			System.out.println(tv2);
			System.out.println();
			
			Textverbinder tv3 = new Textverbinder("heute", "morgen"); // ok
			System.out.println(tv3);
			System.out.println();

			System.out.println("\"" + tv0.texteVerbindenDirekt() + "\""); 			// "  " (Anm.: 2x Leerzeichen)	
			System.out.println("\"" + tv0.texteVerbindenLeerZeichen() + "\""); 		// "   " (Anm.: 3x Leerzeichen)
			System.out.println("\"" + tv0.texteVerbindenLeerZeile() + "\""); 		// "   " (Anm.: 1x Leerzeichen, 1x Leerzeile, 1x Leerzeichen)							
			
			System.out.println("\"" + tv1.texteVerbindenDirekt() + "\""); 			// "JavaFX"
			System.out.println("\"" + tv2.texteVerbindenLeerZeichen() + "\""); 		// "kein Problem"	
			System.out.println("\"" + tv3.texteVerbindenLeerZeile() + "\""); 		// "heute", dann Leerzeile, dann "morgen")
	
			System.out.println(tv1.toStringCsv()); // "Java;FX"
			System.out.println();
			
			System.out.println(tv2.toStringCsv()); // "kein;Problem"
			System.out.println();
			
			System.out.println(tv3.toStringCsv()); // "heute;morgen"
			System.out.println();
			
//			tv1.texteExport(null); // Fehler pfadDateiName ist ungueltig (null)
//			tv1.texteExport("x:\\scratch\\texteExport.csv"); // Fehler Das System kann den angegebenen Pfad nicht finden
			
			String pfadDateiName = "/home/andre/Documents/EclipseExports/texteExport.csv";
			tv1.texteExport(pfadDateiName); // ok
			System.out.println("Texte wurden in Datei " + pfadDateiName + " geschrieben");			
			
		}
		catch (TextException e)
		{
			System.out.println(e.getMessage());
		}
	}

}
