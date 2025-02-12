package factory;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.TicketDAO;
import dao.TicketDAOImpl;

public class DAOFactory {
    public static OrderDAO getOrderDAO() {
        return OrderDAOImpl.getInstance();
    }

    public static TicketDAO getTicketDAO() {
        return TicketDAOImpl.getInstance();
    }
}
