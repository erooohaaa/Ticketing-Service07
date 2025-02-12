package dao;

import models.FullOrderDescription;

public interface OrderDAO {
    FullOrderDescription getFullOrderDescription(int orderId);
}
