package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void addPart(Part newPart) {
        if(newPart != null) {
            this.allParts.add(newPart);
        }
    }

    public void addProduct(Product newProduct) {
        if(newProduct != null) {
            allProducts.add(newProduct);
        }
    }

    public Part lookupPart(int partId) {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getId() == partId) {
                return allParts.get(i);
            }
        }
        return null;
    }

    public Product lookupProduct(int productId) {
        for(int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getId() == productId) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> theParts = FXCollections.observableArrayList();
        if(partName != null) {
            for(int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i).getName() == partName) {
                    theParts.add(allParts.get(i));
                }
            }
        }
        return theParts;
    }

    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> theProducts = FXCollections.observableArrayList();
        if(productName != null) {
            for(int i = 0; i < allProducts.size(); i++) {
                if(allProducts.get(i).getName() == productName) {
                    theProducts.add(allProducts.get(i));
                }
            }
        }
        return theProducts;
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart) {
        return allProducts.remove(selectedPart);
    }

    public boolean deleteProduct(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
