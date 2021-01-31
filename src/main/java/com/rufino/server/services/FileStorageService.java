package com.rufino.server.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  public void init();

  public String save(MultipartFile file);

  public void deleteAll();

  public boolean delete(String filename);

}