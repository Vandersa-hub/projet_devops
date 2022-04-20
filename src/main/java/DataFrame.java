import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class DataFrame {
    private Map<String, DataElement> dataArray;

    public DataFrame() {
        this.dataArray = new HashMap<>();
    }

    public DataFrame(ArrayList<DataElement> data) {
        this.dataArray = new HashMap<>();
        for (DataElement dataElement : data) {
            this.dataArray.put(dataElement.getDataLabel(), dataElement);
        }
    }

    public DataFrame(String csv) {
        try {
            this.dataArray = new HashMap<>();
            List<String[]> data = new CSVReaderBuilder(new FileReader(csv)).build().readAll();
            for (int y = 0; y < data.get(0).length; y++) {
                try {
                    Integer.parseInt(data.get(1)[y]);
                    ArrayList<Integer> list_int = new ArrayList<>();
                    for (int i = 1; i < data.size(); i++) {
                        try {
                            list_int.add(Integer.parseInt(data.get(i)[y]));
                        } catch (IndexOutOfBoundsException e) {
                            list_int.add(null);
                        }
                    }
                    dataArray.put(data.get(0)[y], new DataElement(data.get(0)[y], list_int));
                } catch (final NumberFormatException e) {
                    try {
                        Double.parseDouble(data.get(1)[y]);
                        ArrayList<Double> list_double = new ArrayList<>();
                        for (int i = 1; i < data.size(); i++) {
                            try {
                                list_double.add(Double.parseDouble(data.get(i)[y]));
                            } catch (IndexOutOfBoundsException e2) {
                                list_double.add(null);
                            }
                        }
                        dataArray.put(data.get(0)[y], new DataElement(data.get(0)[y], list_double));
                    } catch (Exception exception) {
                        ArrayList<String> listS = new ArrayList<>();
                        for (int i = 1; i < data.size(); i++) {
                            try {
                                listS.add(data.get(i)[y]);
                            } catch (IndexOutOfBoundsException e2) {
                                listS.add(null);
                            }
                        }
                        this.dataArray.put(data.get(0)[y], new DataElement(data.get(0)[y], listS));
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, DataElement> getDataArray () {
        return dataArray;
    }
}