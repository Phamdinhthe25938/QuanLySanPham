package Service;

import Dao.ProductDao;
import Model.Product;

import java.util.List;

public class ProductService {
    static ProductDao userDao = new ProductDao();
   static public List<Product> products = userDao.selectAllProducts();

    public ProductService(){

    }

    public static void add(Product product){
        products.add(product);
    }
    public static void delete(int index){
        products.remove(index);
    }
     public static void edit(int index,Product product){
        products.set(index,product);
    }
    public static int findProduct(int id){
        for(int i =0;i<products.size();i++){
            if(products.get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }
}
