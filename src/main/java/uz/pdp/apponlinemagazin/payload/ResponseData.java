package uz.pdp.apponlinemagazin.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private String fileName;

    private String downloadUrl;

    private String fileType;

    private long fileSize;

    private String fileId;
}
