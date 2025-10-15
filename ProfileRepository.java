package Repository;

import Enitity.Profile;
import Enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile , Long>
{

    Optional<Object> findByApplicantId(Long applicantId);
}
