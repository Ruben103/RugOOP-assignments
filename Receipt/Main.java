
public class Main {

    public static void main(String[] args) {
        // Create receipts
        Receipt receipt1 = new Receipt("Peter Selie", 42, 1337);
        Receipt receipt2 = new Receipt("Connie Veren", 11, 65536);

        // Populate receipts
        receipt1.addItem(new ReceiptItem("Unicorn Meat", 999.99), 1);
        receipt1.addItem(new ReceiptItem("HandzOff", 19.95), 3);
        receipt1.addItem(new ReceiptItem("Diet Water", 9.95), 30);

        receipt2.addItem(new ReceiptItem("Crib Dribbler", 49.99), 1);
        receipt2.addItem(new ReceiptItem("Bling Teeth", 4.99), 2);
        receipt2.addItem(new ReceiptItem("Pet Petter", 39.95), 3);
        receipt2.addItem(new ReceiptItem("AB-hancer", 2.95), 1);

        //TODO: Construct Serializer subclasses.
        Serializer jsonSerializer = null; //new JSONSerializer();
        Serializer xmlSerializer = new XMLSerializer();

        // Print serialized receipts. The OutputStream of jsonSerializer and xmlSerializer should
        //   be set to System.out, which will cause printing to occur during serialization.
        System.out.println("JSON:");
        System.out.println("Receipt 1:");
        //receipt1.serialize(jsonSerializer);
        System.out.println("\nReceipt 2:");
        //receipt2.serialize(jsonSerializer);

        System.out.println("\n\nXML:");
        System.out.println("Receipt 1:");
        receipt1.serialize(xmlSerializer);
        System.out.println("\nReceipt 2:");
        receipt2.serialize(xmlSerializer);
    }
}
