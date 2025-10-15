package Service;


import Enitity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private static final String UPLOAD_DIR = "uploads/resumes/";

    public void uploadResume(User user, MultipartFile file) throws Exception
    {
        if (file.isEmpty())
        {
            throw new Exception("Empty file");
        }


        File dir = new File(UPLOAD_DIR);
        if (!dir.exists())
        {
            dir.mkdirs();
        }


        String filename = user.getEmail().replaceAll("[^a-zA-Z0-9]", "_") + "_" + file.getOriginalFilename();
        
        Path path = Paths.get(UPLOAD_DIR + filename);

        Files.write(path, file.getBytes());
    }
}
