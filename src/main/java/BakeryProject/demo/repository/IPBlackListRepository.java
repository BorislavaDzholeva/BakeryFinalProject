package BakeryProject.demo.repository;

import BakeryProject.demo.models.entity.IPBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPBlackListRepository extends JpaRepository<IPBlackList, Long> {
    IPBlackList findByIp(String ip);
}
