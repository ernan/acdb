package programmer.ie.sample;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    int id;
    String productName;
    int quantity;

    public Product(int id, String productName, int quantity) {

        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        productName = in.readString();
        quantity = in.readInt();
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(productName);
        dest.writeInt(quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (getQuantity() != product.getQuantity()) return false;
        return getProductName().equals(product.getProductName());

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + getProductName().hashCode();
        result = 31 * result + getQuantity();
        return result;
    }
}
