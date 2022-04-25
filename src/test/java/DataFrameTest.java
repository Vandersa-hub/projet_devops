import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataFrameTest {
    private DataFrame dataFrame;

    @Before
    public  void initialize() {
        dataFrame = new DataFrame("src/main/resources/csv/csvTest.csv");
    }

    @Test
    public void shouldTestDefaultDisplay() {
        String targetDisplay = "Ligne matiere nbeleves moyenne\n" +
                "0 anglais 41 10.2\n" +
                "1 francais 23 14.2\n" +
                "2 mathematiques 32 7.2\n" +
                "3 histoire 12 11.4";

        assertEquals(targetDisplay, dataFrame.defaultDisplay());
    }

    @Test
    public void shouldDisplayNFirstLines() {
        String targetDisplay = "Ligne matiere nbeleves moyenne\n";
        assertEquals(targetDisplay, dataFrame.displayFirstLines(0));

        targetDisplay += "0 anglais 41 10.2";
        assertEquals(targetDisplay, dataFrame.displayFirstLines(1));

        targetDisplay += "\n1 francais 23 14.2";
        assertEquals(targetDisplay, dataFrame.displayFirstLines(2));

        targetDisplay += "\n2 mathematiques 32 7.2";
        assertEquals(targetDisplay, dataFrame.displayFirstLines(3));

        targetDisplay += "\n3 histoire 12 11.4";
        assertEquals(targetDisplay, dataFrame.displayFirstLines(4));

        assertEquals(null, dataFrame.displayFirstLines(5));
    }

    @Test
    public void shouldDisplayNEndLines() {
        String targetDisplay = "Ligne matiere nbeleves moyenne\n";
        assertEquals(targetDisplay, dataFrame.displayEndLines(0));

        targetDisplay += "3 histoire 12 11.4";
        assertEquals(targetDisplay, dataFrame.displayEndLines(1));

        targetDisplay = "Ligne matiere nbeleves moyenne\n" +
                "2 mathematiques 32 7.2\n" +
                "3 histoire 12 11.4";
        assertEquals(targetDisplay, dataFrame.displayEndLines(2));

        targetDisplay = "Ligne matiere nbeleves moyenne\n" +
                "1 francais 23 14.2\n" +
                "2 mathematiques 32 7.2\n" +
                "3 histoire 12 11.4";
        assertEquals(targetDisplay, dataFrame.displayEndLines(3));

        targetDisplay = "Ligne matiere nbeleves moyenne\n" +
                "0 anglais 41 10.2\n" +
                "1 francais 23 14.2\n" +
                "2 mathematiques 32 7.2\n" +
                "3 histoire 12 11.4";
        assertEquals(targetDisplay, dataFrame.displayEndLines(4));

        assertEquals(null, dataFrame.displayEndLines(5));

    }

    /**
     * Cette méthode de choix d'affichage est inspiré de ce qu'il se fait en python.
     */
    @Test
    public void shouldChooseTheRightDisplayFormat() {
        //Aucun paramètre = defaut
        String targetDisplay = "Ligne matiere nbeleves moyenne\n" +
                "0 anglais 41 10.2\n" +
                "1 francais 23 14.2\n" +
                "2 mathematiques 32 7.2\n" +
                "3 histoire 12 11.4";
        assertEquals(targetDisplay, dataFrame.display(""));

        assertEquals(targetDisplay, dataFrame.display("toto"));

        //:nbLines = n premières lignes
        targetDisplay = "Ligne matiere nbeleves moyenne\n" +
                "0 anglais 41 10.2\n" +
                "1 francais 23 14.2";
        assertEquals(targetDisplay, dataFrame.display(":2"));

        //-nbLines: = n dernières lignes
        targetDisplay = "Ligne matiere nbeleves moyenne\n" +
                "1 francais 23 14.2\n" +
                "2 mathematiques 32 7.2\n" +
                "3 histoire 12 11.4";

        assertEquals(targetDisplay, dataFrame.display("-3:"));

    }

    /**
     * Méthode de test selection de colonnes.
     */
    @Test
    public void shouldSelectTheRightColumns(){

        //Colonne "Moyenne"
        DataFrame columnMoyenne = dataFrame.selectColumn(dataFrame, "moyenne");
        String targetDisplay = "Ligne moyenne\n" +
                "0 10.2\n" +
                "1 14.2\n" +
                "2 7.2\n"  +
                "3 11.4";
        //dataFrame.getDataArray().keySet().forEach(key-> System.out.println(" " + dataFrame.getDataArray().get(key).getElements().get(1)));
        assertEquals(targetDisplay, columnMoyenne.display(""));

        //Colonne "Matiere"
        DataFrame columnMatiere = dataFrame.selectColumn(dataFrame, "matiere");
        targetDisplay = "Ligne matiere\n" +
                "0 anglais\n" +
                "1 francais\n" +
                "2 mathematiques\n"  +
                "3 histoire";

        assertEquals(targetDisplay, columnMatiere.display(""));

        DataFrame columnNbEleves = dataFrame.selectColumn(dataFrame, "nbeleves");
        targetDisplay = "Ligne nbeleves\n" +
                "0 41\n" +
                "1 23\n" +
                "2 32\n"  +
                "3 12";

        assertEquals(targetDisplay, columnNbEleves.display(""));

        //Colonne Matiere et nbEleves
        DataFrame columns = dataFrame.selectColumns(dataFrame, "matiere:nbeleves");
        targetDisplay = "Ligne matiere nbeleves\n" +
                "0 anglais 41\n" +
                "1 francais 23\n" +
                "2 mathematiques 32\n"  +
                "3 histoire 12";
        assertEquals(targetDisplay, columns.display(""));
    }

    @Test
    public void shouldSelectTheRightRows(){
        //Ligne n°1

        DataFrame row1 = dataFrame.selectLines(dataFrame, "0");
        String targetDisplay = "Ligne matiere nbeleves moyenne\n"+
                                "0 anglais 41 10.2";
        assertEquals(targetDisplay, row1.display(""));

        //Ligne n°2
        DataFrame row2 = new DataFrame(dataFrame);
        row2 = row2.selectLines(row2, "1");
        targetDisplay = "Ligne matiere nbeleves moyenne\n"+
                "1 francais 23 14.2";
        assertEquals(targetDisplay, row2.display(""));


        //Ligne n°2
        DataFrame row3 = dataFrame.selectLines(dataFrame, "2");
        targetDisplay = "Ligne matiere nbeleves moyenne\n"+
                "2 mathematiques 32 7.2";
        assertEquals(targetDisplay, row3.display(""));

        //Ligne n°3
        DataFrame row4 = dataFrame.selectLines(dataFrame, "3");
        targetDisplay = "Ligne matiere nbeleves moyenne\n"+
                "3 histoire 12 11.4";
        assertEquals(targetDisplay, row4.display(""));
    }
}