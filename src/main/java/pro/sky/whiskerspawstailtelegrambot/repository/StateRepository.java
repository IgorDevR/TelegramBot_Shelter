package pro.sky.whiskerspawstailtelegrambot.repository;

import java.util.Optional;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

@Repository
@Transactional
public interface StateRepository extends
    JpaRepository<AdoptiveParent, Long> {

  Optional<AdoptiveParent> getAdoptiveParentByChatId(Long chatId);

}
