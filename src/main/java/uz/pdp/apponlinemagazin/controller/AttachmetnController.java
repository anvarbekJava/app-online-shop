package uz.pdp.apponlinemagazin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.pdp.apponlinemagazin.domain.Attachment;
import uz.pdp.apponlinemagazin.domain.AttachmentContent;
import uz.pdp.apponlinemagazin.payload.ResponseData;
import uz.pdp.apponlinemagazin.service.impl.AttachmentServiceImpl;

@RestController
@RequestMapping("/api")
public class AttachmetnController {

    @Autowired
    AttachmentServiceImpl attachmentService;

    @PostMapping("/upload")
    public HttpEntity<?> upload(@RequestParam("file")MultipartFile file) throws Exception {
        Attachment attachment = null;
        String downloadURL = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(attachment.getId()).toUriString();
        ResponseData responseData =  new ResponseData(
                attachment.getFileName(),
                downloadURL,
                file.getContentType(),
                file.getSize(),
                attachment.getId()
        );
        return ResponseEntity.ok(responseData);
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment  = null;
        attachment = attachmentService.getAttachment(fileId);
        AttachmentContent attachmentContent = attachmentService.getAttachmentContent(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName=\""+attachment.getFileName()
                                +"\"")
                .body(new ByteArrayResource(attachmentContent.getData()));
    }

}
