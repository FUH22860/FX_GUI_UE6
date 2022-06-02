package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Textverbinder {

	private String text1, text2;

	public Textverbinder(String text1, String text2) throws TextException {
		setText1(text1);
		setText2(text2);
	}

	public void setText1(String text1) throws TextException {
		if (text1 != null)
			this.text1 = text1;
		else
			throw new TextException("Fehler bei setText1(): null-Referenz erhalten");
	}

	public void setText2(String text2) throws TextException {
		if (text2 != null)
			this.text2 = text2;
		else
			throw new TextException("Fehler bei setText2(): null-Referenz erhalten");
	}

	public String getText1() {
		return text1;
	}

	public String getText2() {
		return text2;
	}

	public String texteVerbindenDirekt() throws TextException {
		if (text1 != null && text2 != null)
			return texteVerbinden("");
		else
			throw new TextException(
					"Fehler: text1 oder text2 ungueltig (text1: \"" + text1 + "\", text2: \"" + text2 + "\")");
	}

	public String texteVerbindenLeerZeichen() throws TextException {
		if (text1 != null && text2 != null)
			return texteVerbinden(" ");
		else
			throw new TextException(
					"Fehler: text1 oder text2 ungueltig (text1: \"" + text1 + "\", text2: \"" + text2 + "\")");
	}

	public String texteVerbindenLeerZeile() throws TextException {
		if (text1 != null && text2 != null)
			return texteVerbinden("\n");
		else
			throw new TextException(
					"Fehler: text1 oder text2 ungueltig (text1: \"" + text1 + "\", text2: \"" + text2 + "\")");
	}

	private String texteVerbinden(String verbinder) throws TextException {
		if (text1 != null && text2 != null && verbinder != null)
			if (checkTextEmpty(text1) && checkTextEmpty(text2))
				if (checkVerbinder(verbinder)) {
					StringBuilder sb = new StringBuilder().append(text1).append(verbinder).append(text2);
					return sb.toString();
				} else
					throw new TextException("Fehler: verbinder ist ungueltig (verbinder: \"" + verbinder + "\")");
			else
				throw new TextException(
						"Fehler: text1 oder text2 ungueltig (text1: \"" + text1 + "\", text2: \"" + text2 + "\")");
		else
			throw new TextException("Fehler: null-Referenz fuer text1, text2 oder verbinder erhalten (text1: \"" + text1
					+ " \", text2: \"" + text2 + "\", " + ", verbinder: \"" + verbinder);
	}

	public void texteExport(String pfadDateiName) throws TextException {
		if (pfadDateiName != null && !pfadDateiName.isEmpty()) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(pfadDateiName))) {
				bw.write(toStringCsv());
			} catch (IOException e) {
				throw new TextException(
						"Ein-/Ausgabe-Fehler bei texteExport() mit Datei " + pfadDateiName + ": " + e.getMessage());
			}
		} else
			throw new TextException("Fehler bei texteExport(): pfadDateiName ist ungueltig (" + pfadDateiName + ")");
	}

	private static boolean checkTextEmpty(String text) {
		if (text != null && !text.isEmpty())
			return true;
		else
			return false;
	}

	private static boolean checkVerbinder(String text) {
		if (text != null && (text.equals("") || text.equals(" ") || text.equals("\n")))
			return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("text1=");
		builder.append(text1);
		builder.append(", text2=");
		builder.append(text2);
		return builder.toString();
	}

	public String toStringCsv() {
		StringBuilder builder = new StringBuilder();
		builder.append(text1);
		builder.append(";");
		builder.append(text2);
		return builder.toString();
	}

}
