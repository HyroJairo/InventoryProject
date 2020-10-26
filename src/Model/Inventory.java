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

    public Product lookUpProduct(int productId) {
        for(int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getId() == productId) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    public ObservableList<Part> lookUpPart(String partName) {
        ObservableList theParts = FXCollections.observableArrayList();
        if(partName != null) {
            for(int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i).getName().contains(partName)) {
                    theParts.add(allParts.get(i));
                }
            }
            return theParts;
        }
        return null;
    }

    public ObservableList<Product> lookUpProduct(String productName) {
        ObservableList theProducts = FXCollections.observableArrayList();
        if(productName != null) {
            for(int i = 0; i < allProducts.size(); i++) {
                if(allProducts.get(i).getName().contains(productName)) {
                    theProducts.add(allProducts.get(i));
                }
            }
            return theProducts;
        }
        return null;
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }


    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
