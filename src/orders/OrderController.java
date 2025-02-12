package orders;

import factory.DAOFactory;
import models.FullOrderDescription;

public class OrderController {
    public static FullOrderDescription getFullOrderDescription(int orderId) {
        return DAOFactory.getOrderDAO().getFullOrderDescription(orderId);
    }


    public static void main(String[] args) {
        int orderIdToFetch = 1;
        FullOrderDescription fod = getFullOrderDescription(orderIdToFetch);
        System.out.println(fod);
    }
}
