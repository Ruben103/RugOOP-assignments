
import java.util.ArrayList;
import java.util.List;

public class Receipt implements Serializable {
    private List<ReceiptItem> itemList;
    private double totalCost;
    private String employeeName;
    private int registerNr;
    private int customerNr;

    public Receipt(String employeeName, int registerNr, int customerNr) {
        this.employeeName = employeeName;
        this.registerNr = registerNr;
        this.customerNr = customerNr;
        itemList = new ArrayList<>();
        totalCost = 0;
    }

    public double getTotalCost() {
		return totalCost;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public int getRegisterNr() {
		return registerNr;
	}

	public int getCustomerNr() {
		return customerNr;
	}
	
	public List<ReceiptItem> getItemList() {
		return itemList;
	}

	public void addItem(ReceiptItem item, int amount) {
        itemList.add(item);
        totalCost += item.getUnitCost() * amount;
    }

    @Override
    public void serialize(Serializer serializer) {
        //TODO: serialize all fields using serializer.
    	serializer.objectStart("Receipt");
    	serializer.addField("registerNr", this.getRegisterNr());
    	serializer.addField("customerNr", this.getCustomerNr());
    	serializer.addField("employeeName", this.getEmployeeName());
    	serializer.addField("totalCost", this.getTotalCost());
    	serializer.addField("itemList", this.getItemList());
    	serializer.objectEnd("Receipt");
    }
}
