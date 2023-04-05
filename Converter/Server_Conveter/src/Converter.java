import java.util.HashMap;

public class Converter {
    private Float value;
    private String InputDimension;
    private String OutputDimension;

    // заданная map для хранения отношения конвертируемой размерности и метра.
    // Пример: 1км == 1000м, 1миля == 1609.344м и т.д.
    private final static HashMap<String, Float> DimensionMap;

    static {
        DimensionMap = new HashMap<>();
        DimensionMap.put("m", 1.0f);
        DimensionMap.put("mm", 0.001f);
        DimensionMap.put("cm", 0.01f);
        DimensionMap.put("km", 1000.0f);
        DimensionMap.put("yard", 0.9144f);
        DimensionMap.put("foot", 0.3048f);
        DimensionMap.put("ft", 0.3048f);// reserving
        DimensionMap.put("inch", 0.0254f);
        DimensionMap.put("in", 0.0254f);// reserving
        DimensionMap.put("mile", 1609.344f);
        DimensionMap.put("NM", 1852.0f); // NM = Nautical Mile
        System.out.println("static HashMap is created");
    }

    // конструктор, принимает на вход:
    // величину - valueв(InputDimension размерности);
    // входную размерность - InputDimension;
    // размерность, в которую необходиом конвертировать - OutputDimension
    public Converter(Float value, String InputDimension, String OutputDimension) {
        this.value = value;
        this.InputDimension = InputDimension;
        this.OutputDimension = OutputDimension;
        System.out.println("[CONVERTER] :: FULL CONSTUCTOR");
    }

    public Converter() {
        System.out.println("[CONVERTER] :: EMPTY CONSTUCTOR");
    }// пустой конструктор для создания объекта до получения значений

    public void setData(Float value, String InputDimension, String OutputDimension) {
        this.value = value;
        this.InputDimension = InputDimension;
        this.OutputDimension = OutputDimension;
        System.out.println("[CONVERTER] :: SETTER DATA");
    }

    // функция конвертирует величину в необходимую нам размерность
    // данная функция возращает Float величину в OutputDimension размрности
    // если размерность не задана в DimensionMap, то функция вернет null
    public Float Conversion() {
        System.out.println("[CONVERTER] :: IN PROGRESS");
        Float Meterbase = DimensionMap.get(InputDimension);
        if (Meterbase == null) {
            return null;
        }

        Float OuputBase = DimensionMap.get(OutputDimension);
        if (OuputBase == null) {
            return null;
        }

        return (float) (value * Meterbase * (1 / OuputBase));
    }

    public String MeasureCheck(String InpMes, String OutMes) {
        String returnstring = "";
        if (!DimensionMap.containsKey(InpMes)) {
            returnstring = "ERROR IN InputDimension, use this keys:::";
            returnstring += String.join("|", DimensionMap.keySet());
        }
        if (!DimensionMap.containsKey(OutMes)) {
            returnstring = "ERROR IN OutputDimension, use this keys:::";
            returnstring += String.join("|", DimensionMap.keySet());
        }
        return returnstring;
    }

    public String pushMeasures() {
        return String.join("/", DimensionMap.keySet());
    }
}