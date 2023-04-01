public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        String inputDim = "mile";
        String outputDim = "NM";
        Float value = 1.0f;
        Converter conv = new Converter(value, inputDim, outputDim);
        System.out.print(value + " " + inputDim + " = ");
        System.out.println(conv.Conversion() +  " " + outputDim);

    }
}