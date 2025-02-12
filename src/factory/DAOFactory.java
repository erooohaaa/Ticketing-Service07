package factory;

import dao.OrderDAO;
import dao.OrderDAOImpl;

public class DAOFactory {
    public static OrderDAO getOrderDAO() {
        return OrderDAOImpl.getInstance();
    }
}
