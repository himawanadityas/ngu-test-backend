package ngutestbackend.demo.controller.restapi;


import ngutestbackend.demo.model.dto.InputDTO;
import ngutestbackend.demo.model.entity.Input;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ngutestbackend.demo.repository.InputRepository;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ngutestbackend.demo.controller.restapi.InputAPI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Controller
@RequestMapping("/upload")
public class ImageAPI {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InputRepository inputRepository;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public InputDTO saveData(@RequestPart(value= "data", required=true) InputDTO inputDTO, @RequestPart(value = "file", required = true) MultipartFile file) throws Exception {
        Input input = modelMapper.map(inputDTO, Input.class);
        String userFolderPath = "C:/Users/Lenovo/IMAGE/";
        Path path = Paths.get(userFolderPath);
        Path filePath = path.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        input.setFile(file.getOriginalFilename());
        input = inputRepository.save(input);
        InputDTO inputDTO1 = mapToDTO(input);
        return inputDTO1;
    }
    private InputDTO mapToDTO(Input input) {
        InputDTO inputDTO = modelMapper.map(input, InputDTO.class);
        return inputDTO;
    }
}
