package dev.pe.app.services.file;

import dev.pe.app.domain.utils.enums.Storage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileService {
  void init();
  String store(MultipartFile file, UUID id, Storage storage);
  Path load(String filename);
  Resource loadAsResource(String filename);
  void delete(String filename);
}
