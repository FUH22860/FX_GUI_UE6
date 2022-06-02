package application;

import java.io.File;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.TextException;
import model.Textverbinder;

public class RootBorderPane extends BorderPane {
	
	private MenuBar menuBar;
	private Menu mDatei, mBearbeiten, mHilfe;
	private MenuItem miBeenden, miZuruecksetzen, miExport;
	private GridPane gpZentrum;
	private RadioButton rbDirekt, rbLeerzeichen, rbLeerzeile;
	private TextField tfText1, tfText2;
	private TextArea taErgebnis;
	private Button btVerbinden;
	private ToggleGroup tgRadios;
	private Label lbErgebnis;
	private Textverbinder textverbinder;
	
	public RootBorderPane() throws TextException {
		initComponents();
		addComponents();
		addHandler();
	}

	private void initComponents() throws TextException {
		
		String promptText = "Bitte einen Text eingeben...";
		
		menuBar = new MenuBar();
		mDatei = new Menu("Datei");
		mBearbeiten = new Menu("Bearbeiten");
		mHilfe = new Menu("Hilfe");
		miExport = new MenuItem("Texte in Datei exportieren...");
			miExport.setDisable(true);
		miBeenden = new MenuItem("Beenden");
		miZuruecksetzen = new MenuItem("Zuruecksetzen");
		
		gpZentrum = new GridPane();
			gpZentrum.setAlignment(Pos.CENTER);
			gpZentrum.setHgap(10);
			gpZentrum.setVgap(10);
			gpZentrum.setPadding(new Insets(10));
			
		tfText1 = new TextField();
			//tfText1.setPromptText("Bitte einen Text eingeben...");
			tfText1.setPromptText(promptText);			
		tfText2 = new TextField();
			tfText2.setPromptText(promptText);
		taErgebnis = new TextArea();
			taErgebnis.setDisable(true);
			taErgebnis.setEditable(false);
					
		rbDirekt = new RadioButton("Direkt:");
		rbLeerzeichen = new RadioButton("Leerzeichen:");
		rbLeerzeile = new RadioButton("Leerzeile:");
		
		btVerbinden = new Button("Verbinden");
			
		tgRadios = new ToggleGroup();
		
		lbErgebnis = new Label("Ergebnis:");
		
		textverbinder = new Textverbinder("Bitte geben Sie einen Text ein...", "Bitte geben Sie einen Text ein...");
		
	}

	private void addComponents() {
		mDatei.getItems().addAll(miExport, new SeparatorMenuItem(), miBeenden);
		mBearbeiten.getItems().addAll(miZuruecksetzen);
		menuBar.getMenus().addAll(mDatei, mBearbeiten, mHilfe);
		
		gpZentrum.add(new Label("Verbinden:"), 	0, 0);
		gpZentrum.add(new Label("Text 1:"), 	0, 3);
		gpZentrum.add(new Label("Text 2:"), 	0, 4);
		
		//gpZentrum.add(new Label("Ergebnis:"), 	0, 5);
		gpZentrum.add(lbErgebnis, 	0, 5);
			GridPane.setValignment(lbErgebnis, VPos.TOP);
		
		gpZentrum.add(rbDirekt, 1, 0);
		gpZentrum.add(rbLeerzeichen, 1, 1);
		gpZentrum.add(rbLeerzeile, 1, 2);
		
		gpZentrum.add(tfText1, 1, 3);
		gpZentrum.add(tfText2, 1, 4);		
		gpZentrum.add(taErgebnis, 1, 5);
		
		gpZentrum.add(btVerbinden, 1, 6);
			GridPane.setHalignment(btVerbinden, HPos.RIGHT);
		
		tgRadios.getToggles().addAll(rbDirekt, rbLeerzeichen, rbLeerzeile);
			tgRadios.selectToggle(rbDirekt);
		
		setTop(menuBar);
		setCenter(gpZentrum);
		
	}

	private void addHandler() {
		miBeenden.setOnAction(event -> beenden());
		btVerbinden.setOnAction(event -> verbinden());
		miZuruecksetzen.setOnAction(event -> zuruecksetzen());
		miExport.setOnAction(event -> export());
	}

	// ---------------------- handlers -----------------------

	private void export()
	{ 
		FileChooser fc = new FileChooser();
		File selected = fc.showSaveDialog(null);
		if (selected != null)
		{
			String pfadDateiName = selected.getAbsolutePath();
			try {
				textverbinder.texteExport(pfadDateiName);
				Main.showAlert(AlertType.CONFIRMATION, "Die Texte wurden in die Datei " + pfadDateiName + " exportiert");
				
			} catch (TextException e) {
				Main.showAlert(AlertType.CONFIRMATION, e.getMessage());
			}
		}
		else
			Main.showAlert(AlertType.CONFIRMATION, "Es wurde keine Datei fuer den Export der Texte ausgewaehlt");
	}
	
	private void beenden() {
		Platform.exit();
	}
		
	private void verbinden() {
		
		//String text1 = tfText1.getText(); // z.B. "Java"
		//String text2 = tfText2.getText(); // z.B. "FX"
		
		//String verbinder = ""; // bei Standard-Auswahl der Radios "Direkt"
		String ergebnis = "";
		
		try {
			textverbinder.setText1(tfText1.getText());
			textverbinder.setText2(tfText2.getText());
			
			if (rbDirekt.isSelected())
				ergebnis = textverbinder.texteVerbindenDirekt();
			
			if (rbLeerzeichen.isSelected())
			{
				ergebnis = textverbinder.texteVerbindenLeerZeichen();
			}
		
			if (rbLeerzeile.isSelected())
			{
				ergebnis = textverbinder.texteVerbindenLeerZeile();
			}
		
			taErgebnis.setText(ergebnis);
			taErgebnis.setDisable(false);
			miExport.setDisable(false);
		}
		catch (TextException e)
		{
			Main.showAlert(AlertType.CONFIRMATION, e.getMessage());			
		}
	}
	
	private void zuruecksetzen()
	{
		//rbDirekt.setSelected(true);
		tgRadios.selectToggle(rbDirekt);
		tfText1.clear();
		tfText2.clear();
		taErgebnis.clear();
		taErgebnis.setDisable(true);
		miExport.setDisable(true);
	}
	
}
