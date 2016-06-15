
public class ReceiptItem implements Serializable {
    private String productName;
    private double unitCost;

    public ReceiptItem(String productName, double unitCost) {
        this.productName = productName;
        this.unitCost = unitCost;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public String getProductName() {
		return productName;
	}

	@Override
    public void serialize(Serializer serializer) {
        //TODO: serialize all fields using serializer.
    	serializer.objectStart("ReceiptItem");
    	serializer.addField("productName", this.getProductName());
    	serializer.addField("unitCost", this.getUnitCost());
    	serializer.objectEnd("ReceiptItem");
    }
}
