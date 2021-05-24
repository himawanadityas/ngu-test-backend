package ngutestbackend.demo.model.dto;

import lombok.Data;

@Data
public class InputDTO {
    private Integer id;
    private String title;
    private String location;
    private String participant;
    private String date;
    private byte[] file;
}
