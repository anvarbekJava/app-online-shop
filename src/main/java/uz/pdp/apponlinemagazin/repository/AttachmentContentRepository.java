package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apponlinemagazin.domain.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, String> {
    Optional<AttachmentContent> findByAttachmentId(String attachment_id);
}
