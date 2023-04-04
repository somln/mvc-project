package hello.mvc2.web.file.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class FileForm {

    private Long id;
    private String fileTitle;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
