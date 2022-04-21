import org.junit.*;

import java.util.Map;

public class TestCsvStats {

    private Map<String, DataElement> data;

    @Before
    public void Initialize() {
        data = new DataFrame("src/main/csv/csvTest.csv").getDataArray();
    }

    @Test
    public void shouldTestConstructor() {
        DataFrame csv = new DataFrame("src/main/csv/csvTest.csv");
        Assert.assertEquals(csv.mean("moyenne"), 10.75, 0.0);
        Assert.assertEquals(csv.mean("nbeleves"), 27, 0.0);
        Assert.assertEquals(csv.max("nbeleves"), 41, 0.0);
        Assert.assertEquals(csv.max("moyenne"), 14.2, 0.0);
        Assert.assertEquals(csv.min("nbeleves"), 12, 0.0);
        Assert.assertEquals(csv.min("moyenne"), 7.2, 0.0);

        Assert.assertNull(csv.min("matiere"));
        Assert.assertNull(csv.max("matiere"));
        Assert.assertNull(csv.mean("matiere"));

    }
}