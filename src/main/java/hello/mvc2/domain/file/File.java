package hello.mvc2.domain.file;

import lombok.Data;

import java.util.List;

@Data
public class File {

    private Long id;
    private String fileTitle;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;

}
