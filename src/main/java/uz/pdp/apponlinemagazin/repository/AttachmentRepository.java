package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apponlinemagazin.domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {
}
