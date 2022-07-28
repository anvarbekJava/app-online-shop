package uz.pdp.apponlinemagazin.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    public AttachmentContent(byte[] data, Attachment attachment) {
        this.data = data;
        this.attachment = attachment;
    }


}
