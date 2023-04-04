package hello.mvc2.domain.file;

import hello.mvc2.domain.file.File;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileRepository {

    private final Map<Long, File> store = new HashMap<>();
    private long sequence = 0L;

    public File save(File file){
        file.setId(++sequence);
        store.put(file.getId(), file);
        return file;
    }

    public File findById(Long id){
        return store.get(id);
    }
}
