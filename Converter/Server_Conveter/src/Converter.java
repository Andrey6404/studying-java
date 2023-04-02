import java.util.HashMap;

public class Converter {
    private Float value;
    private String InputDimension;
    private String OutputDimension;

    //заданная map для хранения отношения конвертируемой размерности и метра. Пример: 1км == 1000м, 1миля == 1609.344м и т.д.
    private final static HashMap<String, Float> DimensionMap;

    static {
        DimensionMap = new HashMap<>();
        DimensionMap.put("m", 1.0f);
        DimensionMap.put("mm", 0.001f);
        DimensionMap.put("cm", 0.01f);
        DimensionMap.put("km", 1000.0f);
        DimensionMap.put("yard", 0.9144f);
        DimensionMap.put("foot", 0.3048f);
        DimensionMap.put("inch", 0.0254f);
        DimensionMap.put("mile", 1609.344f);
        DimensionMap.put("NM", 1852.0f); // NM = Nautical Mile
        System.out.println("static HashMap is created");
    }

    // конструктор, принимает на вход:
    // величину - valueв(InputDimension размерности);
    // входную размерность - InputDimension;
    // размерность, в которую необходиом конвертировать - OutputDimension
    public Converter(Float value, String InputDimension, String OutputDimension)
    {
        this.value           = value;
        this.InputDimension  = InputDimension;
        this.OutputDimension = OutputDimension;
        System.out.println("Converter is  created");
    }

    // функция конвертирует величину в необходимую нам размерность
    // данная функция возращает Float величину в OutputDimension размрности
    // если размерность не задана в DimensionMap, то функция вернет null
    public Float Conversion()
    {
        Float Meterbase = DimensionMap.get(InputDimension);
        if (Meterbase == null)
        {
            return null;
        }

        Float OuputBase = DimensionMap.get(OutputDimension);
        if (OuputBase == null)
        {
            return null;
        }

        return value * Meterbase * (1 / OuputBase);
    }
}