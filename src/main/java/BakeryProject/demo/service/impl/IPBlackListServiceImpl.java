package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AdminAddIpDTO;
import BakeryProject.demo.models.entity.IPBlackList;
import BakeryProject.demo.repository.IPBlackListRepository;
import BakeryProject.demo.service.IPBlackListService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IPBlackListServiceImpl implements IPBlackListService {
    private final IPBlackListRepository ipBlackListRepository;
    private final ModelMapper modelMapper;

    public IPBlackListServiceImpl(IPBlackListRepository ipBlackListRepository, ModelMapper modelMapper) {
        this.ipBlackListRepository = ipBlackListRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean isBlocked(String ip) {
        return ipBlackListRepository.findByIp(ip) != null;
    }

    @Override
    public List<IPBlackList> getAllBlackListIp() {
        return ipBlackListRepository.findAll();
    }

    @Override
    public void removeIPById(Long id) {
        ipBlackListRepository.deleteById(id);
    }

    @Override
    public void addIP(AdminAddIpDTO adminAddIpDTO) {
        IPBlackList ipBlackList = modelMapper.map(adminAddIpDTO, IPBlackList.class);
        ipBlackListRepository.save(ipBlackList);
    }

    @Override
    public void removeExpired() {
        List<IPBlackList> blacklist = ipBlackListRepository.findAll();
        blacklist.forEach(ipBlackList -> {
            if (ipBlackList.getAddedOn().plusHours(24).isBefore(java.time.LocalDateTime.now())) {
                ipBlackListRepository.delete(ipBlackList);
            }
        });
    }
}
