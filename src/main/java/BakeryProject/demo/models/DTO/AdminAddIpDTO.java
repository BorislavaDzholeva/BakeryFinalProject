package BakeryProject.demo.models.DTO;
import java.time.LocalDateTime;

public class AdminAddIpDTO {
    private String ip;
    private LocalDateTime addedOn = LocalDateTime.now();

    public AdminAddIpDTO() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }
}
