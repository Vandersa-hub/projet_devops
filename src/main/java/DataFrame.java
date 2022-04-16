import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataFrame<DataElement> {
    private Map<String, DataElement> dataArray;

    public DataFrame() {
        this.dataArray = new HashMap<>();
    }

    public DataFrame(ArrayList<DataElement> data, ArrayList<String> columnsNames) {
        for (String columnsName : columnsNames) {
            this.dataArray.put(columnsName, data.get(columnsNames.indexOf(columnsName)));
        }
    }

    public Map<String, DataElement> getDataArray() {
        return dataArray;
    }
}