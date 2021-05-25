package ngutestbackend.demo.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InputDTO {
    private Integer id;
    private String title;
    private String location;
    private String participant;
    private String date;
    private String note;
    private String file;

}
