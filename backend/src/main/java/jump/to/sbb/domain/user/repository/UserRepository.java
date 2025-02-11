package jump.to.sbb.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jump.to.sbb.domain.user.entity.SiteUser;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByUsername(String username);

	Optional<SiteUser> findByEmail(String email);
}
