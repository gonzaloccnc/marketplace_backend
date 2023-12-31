package dev.pe.app.v1.services.file;

import dev.pe.app.v1.domain.utils.enums.Storage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileService {
  void init();
  String store(MultipartFile file, UUID id, Storage storage);
  Path load(String filename, Storage storage);
  Resource loadAsResource(String filename, Storage storage);
  void delete(String filename, Storage storage);
}
