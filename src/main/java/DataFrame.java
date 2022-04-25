import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class DataFrame{
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

    public DataFrame(DataFrame frame){
        this.dataArray = new HashMap<>(frame.getDataArray());
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
        StringBuilder result = getDisplayHeader();

        formatLines(0, getNumberOfLine(), result);

        return result.toString();
    }

    /**
     * Recherche les élements de chaque colonnes du début jusqu'à nbLines et les formattent pour les renvoyer.
     * @param nbLines
     * @return Un chaine de caractères contenant les valeurs des nbLines premières lignes de chaque colonnes.
     */
    public String displayFirstLines(int nbLines) {
        if(nbLines > getNumberOfLine())
            return null;

        StringBuilder result = getDisplayHeader();

        formatLines(0, nbLines, result);

        return result.toString();
    }

    /**
     * Recherche les élements de chaque colonnes à partir des nbLines dernières lignes jusqu'à la fin et les formattent pour les renvoyer.
     * @param nbLines
     * @return Un chaine de caractères contenant les valeurs des nbLines dernières lignes de chaque colonne.
     */
    public String displayEndLines(int nbLines) {
        if(nbLines > getNumberOfLine())
            return null;

        StringBuilder result = getDisplayHeader();

        formatLines(getNumberOfLine()-nbLines ,getNumberOfLine(), result);

        return result.toString();
    }

    private StringBuilder getDisplayHeader() {
        StringBuilder result = new StringBuilder("Ligne");
        dataArray.keySet().forEach(key -> result.append(" " + key));
        result.append("\n");
        return result;
    }

    private void formatLines(int linesMin, int linesMax, StringBuilder result) {
        for (int i = linesMin; i < linesMax; i++) {
            result.append(i);
            for (String key : dataArray.keySet()) {
                result.append(" " + dataArray.get(key).getElements().get(i));
            }
            if (i < linesMax - 1)
                result.append("\n");
        }
    }

    /**
     * Puisque toutes les colonnes ont la même taille, nous ne renvoyons que la taille de la première.
     *
     * @return Le nombre de ligne d'une colonne de notre DataFrame
     */
    private int getNumberOfLine() {
        return dataArray.values().stream().findFirst().get().getElements().size();
    }


    public String display(String param) {
        if(param.matches(":[0-9]+")) {
            return displayFirstLines(Integer.parseInt(param.substring(1)));
        } else if(param.matches("-[0-9]+:")) {
            return displayEndLines(Integer.parseInt(param.substring(1, param.indexOf(':'))));
        }
        return defaultDisplay();
    }

    /**
     * Selectionne les n premieres lignes d'un dataframe et en créer un nouveau avec.
     * @param frame : dataframe sur lequel on travaille
     * @param param : numéro de la ligne a isoler
     * @return nouvel element DataFrame avec la/les ligne(s) souhaité(es)
     */
    public DataFrame selectLines(DataFrame frame, String param){
        if(param.matches(":[0-9]+")) {
                return createSubDataFrame(frame, Integer.parseInt(param.substring(1)));
        }/* else if(param.matches("-[0-9]+:")) {

        }else if (param.matches("[0-9]+")){

        }*/
        return createSubDataFrame(this, Integer.parseInt(param));
    }

    public DataFrame selectColumn(DataFrame frame, String label){
        DataFrame copyFrame = new DataFrame(frame);
        copyFrame.getDataArray().keySet().removeIf(key -> !key.equals(label));
        return copyFrame;
    }

    /**
     * Selectionne plusieurs colonnes et crée un nouvel element DataFrame correspondant
     * @param frame : le dataframe sur lequel on travaille
     * @param labels : le label des colonnes que l'on veut extraire
     * @return un nouvel element DataFrame
     */
    public DataFrame selectColumns(DataFrame frame, String labels){
        String[] lab = labels.split(":");
        DataFrame returnFrame = new DataFrame(frame);
        returnFrame.dataArray = new LinkedHashMap<>();
        for(int i = 0; i < lab.length; i++){
            DataFrame copyFrame = selectColumn(frame, lab[i]);
            returnFrame.dataArray.putAll(copyFrame.dataArray);
        }
        return returnFrame;
    }

    /**
     * Création d'un dataFrame ne contenant que ce que la ligne que l'on souhaite
     * @param frame : le dataframe sur lequel on travaille
     * @param param integer : numéro de la ligne que l'on veut isoler
     * @return nouveau dataframe
     */
    public DataFrame createSubDataFrame(DataFrame frame, Integer param){
        int i = 0;
        DataFrame copyFrame = new DataFrame(frame);
        for(Map.Entry<String, DataElement> entry : copyFrame.getDataArray().entrySet()){
            copyFrame.getDataArray().get(entry.getKey()).getElements().removeIf(e -> !e.equals(copyFrame.getDataArray().get(entry.getKey()).getElements().get(param)));
        }
        return copyFrame;
    }

}