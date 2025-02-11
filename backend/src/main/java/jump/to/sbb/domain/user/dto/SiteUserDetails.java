package jump.to.sbb.domain.user.dto;

import java.util.Set;

import jump.to.sbb.domain.user.entity.SiteUser;
import jump.to.sbb.domain.user.entity.UserRole;

public record SiteUserDetails(
	Long id,
	String username,
	String email,
	Set<UserRole> roles
) {
	public static SiteUserDetails from(SiteUser su) {
		return new SiteUserDetails(su.getId(), su.getUsername(), su.getEmail(), su.getRoles());
	}

	public SiteUser toEntity() {
		return new SiteUser(
			id,
			username,
			email,
			null,
			roles
		);
	}
}
