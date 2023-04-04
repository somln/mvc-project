package hello.mvc2.web.file;

import hello.mvc2.domain.file.File;
import hello.mvc2.domain.file.FileRepository;
import hello.mvc2.domain.file.FileStore;
import hello.mvc2.domain.file.UploadFile;
import hello.mvc2.web.file.form.FileForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/files")
public class FileController {

    private final FileRepository fileRepository;
    private final FileStore fileStore;

    @GetMapping("/upload")
    public String uploadForm(@ModelAttribute FileForm form){
        return "file/file-form";
    }

    @PostMapping("/upload")
    public String upload( @ModelAttribute FileForm form, RedirectAttributes redirectAttributes) throws IOException {

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> imageFiles = fileStore.storeFiles(form.getImageFiles());

        File file = new File();
        file.setFileTitle(form.getFileTitle());
        file.setAttachFile(attachFile);
        file.setImageFiles(imageFiles);
        fileRepository.save(file);

        redirectAttributes.addAttribute("fileId", file.getId());
        return "redirect:/files/{fileId}";
    }

    @GetMapping("/{fileId}")
    public String files(@PathVariable Long fileId, Model model){
        File file = fileRepository.findById(fileId);
        model.addAttribute("file", file);
        return "file/file-view";
    }

    @GetMapping("/attach/{fileId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long fileId) throws MalformedURLException {

        File file = fileRepository.findById(fileId);

        String storeFileName = file.getAttachFile().getStoreFileName();
        String uploadFileName = file.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @ResponseBody
    @GetMapping("/images/{fileTitle}")
    public Resource downloadImage(@PathVariable String fileTitle) throws MalformedURLException {
        //해당 경로에 있는 파일을 접근을 해서 반환
        return new UrlResource("file:" + fileStore.getFullPath(fileTitle));
    }


}
