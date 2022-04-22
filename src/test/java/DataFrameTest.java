import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataFrameTest {
    private DataFrame dataFrame;

    @Before
    public  void initialize() {
        dataFrame = new DataFrame("src/main/csv/csvTest.csv");
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
    public void shouldDisplayNFirstLine() {
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

}