package dev.pe.app.domain.services.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IFileService {
  void init();
  String store(MultipartFile file);
  Path load(String filename);
  Resource loadAsResource(String filename);
  void delete(String filename);
}
