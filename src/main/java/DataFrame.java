import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class DataFrame {
    private Map<String, DataElement> dataArray;

    public DataFrame() {
        this.dataArray = new LinkedHashMap<>();
    }

    public DataFrame(ArrayList<DataElement> data) {
        this.dataArray = new LinkedHashMap<>();
        for (DataElement dataElement : data) {
            this.dataArray.put(dataElement.getDataLabel(), dataElement);
        }
    }

    public DataFrame(String csv) {
        try {
            this.dataArray = new LinkedHashMap<>();
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

    public Map<String, DataElement> getDataArray() {
        return dataArray;
    }

    /**
     * Calcule la moyenne des valeurs de la colonne nommée "label".
     * @param label
     * @return La moyenne ou null s'il s'agit d'une colonne de String.
     */
    public Double mean(String label) {
        ArrayList array = dataArray.get(label).getElements();
        Object type = array.get(0);
        double mean = 0;
        if (array == null) {
            System.err.println("Unrecognized label : " + label);
            return null;
        } else if (type instanceof Integer) {
            for (int line = 0; line < array.size(); line++) {
                mean += (double) (int) array.get(line);
            }
            return mean / array.size();
        } else if (type instanceof Double) {
            for (int line = 0; line < array.size(); line++) {
                mean += (double) array.get(line);
            }
            return mean / array.size();
        } else {
            System.err.println("You try to mean strings or something");
            return null;
        }
    }

    /**
     * Calcule le maximum des valeurs de la colonne nommée "label".
     * @param label
     * @return La valeur maximale ou null s'il s'agit d'une colonne de String ou de Date.
     */
    public Double max(String label) {
        Double max = -Double.MAX_VALUE;
        ArrayList array = dataArray.get(label).getElements();
        Object type = array.get(0);
        if (array == null) {
            System.err.println("Unrecognized label : " + label);
            return null;
        } else if (type instanceof Integer) {
            for (int line = 0; line < array.size(); line++) {
                if (max < (double) (int) array.get(line)) {
                    max = (double) (int) array.get(line);
                }
            }
            return max;
        } else if (type instanceof Double) {
            for (int line = 0; line < array.size(); line++) {
                if (max < (double) array.get(line)) {
                    max = (double) array.get(line);
                }
            }
            return max;
        } else {
            System.err.println("You try to mean strings or something");
            return null;
        }
    }

    /**
     * Calcule le minimum des valeurs de la colonne nommée "label".
     * @param label
     * @return La valeur minimale ou null s'il s'agit d'une colonne de String ou de Date.
     */
    public Double min(String label) {
        Double min = Double.MAX_VALUE;
        ArrayList array = dataArray.get(label).getElements();
        Object type = array.get(0);
        if (array == null) {
            System.err.println("Unrecognized label : " + label);
            return null;
        } else if (type instanceof Integer) {
            for (int line = 0; line < array.size(); line++) {
                if (min > (double) (int) array.get(line)) {
                    min = (double) (int) array.get(line);
                }
            }
            return min;
        } else if (type instanceof Double) {
            for (int line = 0; line < array.size(); line++) {
                if ((double) array.get(line) < min) {
                    min = (double) array.get(line);
                }
            }
            return min;
        } else {
            System.err.println("You try to mean strings or something");
            return null;
        }
    }

    /**
     * Méthode de mise en forme par défaut de l'affichage.
     * Sélection de la totalité du DataFrame.
     *
     * @return Une chaine de caractère contenant l'ensemble des données du DataFrame sous une forme donnée.
     */
    public String defaultDisplay() {
        StringBuilder result = new StringBuilder("Ligne");
        Set<String> keys = dataArray.keySet();
        int nbLine = getNumberOfLine();

        keys.forEach(key -> result.append(" " + key));

        result.append("\n");

        for (int i = 0; i < nbLine; i++) {
            result.append(i);
            for (String key : keys) {
                result.append(" " + dataArray.get(key).getElements().get(i));
            }
            if (i < nbLine - 1)
                result.append("\n");
        }

        System.out.println(result);

        return result.toString();
    }

    public String displayFirstLines(int numberOfLine) {
        StringBuilder result = new StringBuilder("Ligne");
        Set<String> keys = dataArray.keySet();

        keys.forEach(key -> result.append(" " + key));

        result.append("\n");

        for (int i = 0; i < numberOfLine; i++) {
            result.append(i);
            for (String key : keys) {
                result.append(" " + dataArray.get(key).getElements().get(i));
            }
            if (i < numberOfLine - 1)
                result.append("\n");
        }

        System.out.println(result);

        return result.toString();
    }

    /**
     * Puisque toutes les colonnes ont la même taille, nous ne renvoyons que la taille de la première.
     *
     * @return Le nombre de ligne d'une colonne de notre DataFrame
     */
    private int getNumberOfLine() {
        return dataArray.values().stream().findFirst().get().getElements().size();
    }


}