package net.zjcclims.service.system;

import net.zjcclims.domain.User;
import net.zjcclims.domain.User2;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Set;

public interface User2DetailService {
    public int getUserTotalRecords(String number);
    public List<User2> findAllUser2s(int curr, int size);
    public List<User2> findAllUsers();
}
