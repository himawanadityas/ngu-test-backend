package ngutestbackend.demo.controller.restapi;


import io.swagger.models.auth.In;
import ngutestbackend.demo.model.entity.*;
import ngutestbackend.demo.model.dto.InputDTO;
import ngutestbackend.demo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Base64;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/input")
@CrossOrigin(origins = "http://localhost:3000")
public class InputAPI {
    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public List<InputDTO> getList() {
        List<Input> inputList = inputRepository.findAll();
        List<InputDTO> inputDTOList =
                inputList.stream()
                        .map(in -> mapToDTO(in))
                        .collect(Collectors.toList());
        return inputDTOList;
    }

//    @GetMapping("/{id}")
//    public MahasiswaDto getMahasiswa(@PathVariable Integer id) {
//
//        Mahasiswa mahasiswa = mahasiswaRepository.findById(id).get();
//
//        MahasiswaDto mahasiswaDto = new MahasiswaDto();
//
//        modelMapper.map(mahasiswa, mahasiswaDto); //   modelMapper.map(asal, tujuan);
//        modelMapper.map(mahasiswa.getAgama(), mahasiswaDto);
//        modelMapper.map(mahasiswa.getJurusan(), mahasiswaDto);
//
//        mahasiswaDto.setId(mahasiswa.getId());
//        return mahasiswaDto;
//
//    }

//    @PostMapping()
//    public InputDTO saveData(@RequestBody InputDTO inputDTO) throws Exception {
//        Input input = modelMapper.map(inputDTO, Input.class);
////        String userFolderPath = "C:/Users/Lenovo/IMAGE/";
////        Path path = Paths.get(userFolderPath);
////        Path filePath = path.resolve(file.getOriginalFilename());
////        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
////        input.setFile(file.getOriginalFilename());
//        input = inputRepository.save(input);
//        InputDTO inputDTO1 = mapToDTO(input);
//        return inputDTO1;
//    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/save")
    public InputDTO saveData(@RequestPart(value="data", required = true) InputDTO inputDTO, @RequestPart(value="file", required = true) MultipartFile file) throws Exception {
        Input input = modelMapper.map(inputDTO, Input.class);
        String userFolderPath = "C:/Users/Lenovo/IMAGE/";
//        String userFolderPath = "C:/Users/Lenovo/FullStackTech/ngu-test/src/assets/utils/images/";
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

    @GetMapping("/getImage/{id}")
    public byte[] getImage(@PathVariable Integer id) throws IOException {
        Input input = inputRepository.findById(id).get();
        String userFolderPath = "C:/Users/Lenovo/IMAGE/";
//        String userFolderPath = "C:/Users/Lenovo/FullStackTech/ngu-test/src/assets/utils/images/";
        String pathFile = userFolderPath + input.getFile();
        Path paths = Paths.get(pathFile);
        byte[] foto = Files.readAllBytes(paths);
        return foto;
    }

    @DeleteMapping
    public void del(){
        inputRepository.deleteAll();
    }
}
