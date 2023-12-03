package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddIpDTO;
import BakeryProject.demo.models.entity.IPBlackList;

import java.util.List;

public interface IPBlackListService {
    boolean isBlocked(String ip);

    List<IPBlackList> getAllBlackListIp();

    void removeIPById(Long id);

    void addIP(AdminAddIpDTO adminAddIpDTO);

    void removeExpired();
}
