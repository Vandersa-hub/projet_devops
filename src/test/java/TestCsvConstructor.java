import org.junit.*;
import java.util.*;

import static jdk.nashorn.internal.objects.Global.print;

public class TestCsvConstructor {
    @Test
    public void test(){
        Map<String, DataElement> data = new DataFrame("src/main/csv/csvTest.csv").getDataArray();

        //Assert.assertTrue(data.containsKey(""));
        Assert.assertTrue(data.containsKey("matiere"));
        Assert.assertTrue(data.containsKey("nbeleves"));
        Assert.assertTrue(data.containsKey("moyenne"));

        ArrayList<String> matiere = data.get("matiere").getElements();

        Assert.assertEquals(matiere.get(0), "anglais");
        Assert.assertEquals(matiere.get(1), "francais");
        Assert.assertEquals(matiere.get(2), "mathematiques");
        Assert.assertEquals(matiere.get(3), "histoire");

        ArrayList<Integer> nbeleves = data.get("nbeleves").getElements();

        Assert.assertEquals(nbeleves.get(0), 41, 0);
        Assert.assertEquals(nbeleves.get(1), 23, 0);
        Assert.assertEquals(nbeleves.get(2), 32, 0);
        Assert.assertEquals(nbeleves.get(3), 12, 0);

        ArrayList<Double> moyenne = data.get("moyenne").getElements();

        Assert.assertEquals(moyenne.get(0).compareTo( 10.2), 0);
        Assert.assertEquals(moyenne.get(1), 14.2, 0);
        Assert.assertEquals(moyenne.get(2), 7.2, 0);
        Assert.assertEquals(moyenne.get(3), 11.4, 0);

        ArrayList<DataElement> de = new ArrayList<>();

        de.add(new DataElement( "matiere",matiere));
        de.add(new DataElement( "nbeleves", nbeleves));
        de.add(new DataElement( "moyenne",moyenne));

        Map<String, DataElement> data2 = new DataFrame(de).getDataArray();

        Assert.assertTrue(data2.containsKey("matiere"));
        Assert.assertTrue(data2.containsKey("nbeleves"));
        Assert.assertTrue(data2.containsKey("moyenne"));

        ArrayList<String> matiere2 = data.get("matiere").getElements();

        Assert.assertEquals(matiere2.get(0), "anglais");
        Assert.assertEquals(matiere2.get(1), "francais");
        Assert.assertEquals(matiere2.get(2), "mathematiques");
        Assert.assertEquals(matiere2.get(3), "histoire");

        ArrayList<Integer> nbeleves2 = data.get("nbeleves").getElements();

        Assert.assertEquals(nbeleves2.get(0), 41, 0);
        Assert.assertEquals(nbeleves2.get(1), 23, 0);
        Assert.assertEquals(nbeleves2.get(2), 32, 0);
        Assert.assertEquals(nbeleves2.get(3), 12, 0);

        ArrayList<Double> moyenne2 = data.get("moyenne").getElements();

        Assert.assertEquals(moyenne2.get(0), 10.2, 0);
        Assert.assertEquals(moyenne2.get(1), 14.2, 0);
        Assert.assertEquals(moyenne2.get(2), 7.2, 0);
        Assert.assertEquals(moyenne2.get(3), 11.4, 0);

    }
}