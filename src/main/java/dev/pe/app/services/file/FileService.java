package dev.pe.app.services.file;

import dev.pe.app.domain.handlers.FileNotFoundException;
import dev.pe.app.domain.handlers.StorageException;
import dev.pe.app.domain.utils.enums.Storage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileService implements IFileService {

  @Value("${images}")
  private String storageLocation;
  private final List<String> extensions = List.of("image/jpeg", "image/jpg");

  @PostConstruct
  @Override
  public void init() {
    try {
      Files.createDirectories(Paths.get(storageLocation));
    }catch (IOException e){
      throw new StorageException("failed to init", e);
    }
  }

  /// TODO HACER QUE LA CARPETA SEA EL ID DEL PRODUCTO O USUARIO
  /// TODO -> LOS PRODUCTOS TENDRAN HASTA 5 IMAGENES Y EL USUARIO SOLO 1 VALIDAR QUE SEA MAXIMO 5 IMAGENES
  /// TODO -> EL USUARIO TANTO SU CARPETA COMO EL NOMBRE DE ARCHIVO SERAN SU ID
  /// TODO -> EL PRODUCTO TENDRA UNA IMAGEN PRINCIPAL (PRINCIPAL.JPG/JPEG) Y EL RESTO (demo{1,2,3,4}/jpg/jpeg)
  @Override
  public String store(MultipartFile file, UUID id, Storage storage) {
    String filename = file.getOriginalFilename();
    String extension = file.getContentType();

    if(!extensions.contains(extension)) {
      throw new StorageException("failed extension file " + extension);
    }

    String extname = extension.replace("image/", ".");

    String filenameFinal = id + "-md" + extname;

    try {
      InputStream inputStream = file.getInputStream();
      Files.copy(
          inputStream,
          Paths
              .get(storageLocation + "/" + storage.name().toLowerCase())
              .resolve(filenameFinal), StandardCopyOption.REPLACE_EXISTING
      );
    } catch (IOException e){
      throw new StorageException("failed to save file " + filename, e);
    }

    return filenameFinal;
  }

  @Override
  public Path load(String filename) {
    return Paths.get(storageLocation).resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try{
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()){
        return resource;
      }else{
        throw new FileNotFoundException("file not found or cannot be read " + filename);
      }
    }catch (MalformedURLException e){
      throw new FileNotFoundException("file not found or cannot be read " + filename, e);
    }
  }

  @Override
  public void delete(String filename) {
    try {
      Path file = load(filename);
      FileSystemUtils.deleteRecursively(file);
    }catch (IOException e){
      throw new StorageException("cannot delete file " + filename, e);
    }
  }
}
