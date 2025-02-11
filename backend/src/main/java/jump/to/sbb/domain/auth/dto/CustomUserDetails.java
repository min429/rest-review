package jump.to.sbb.domain.auth.dto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jump.to.sbb.domain.user.entity.UserRole;
import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
	private final Long id;
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;

	public CustomUserDetails(Long id, String username, String password, Set<UserRole> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = roles.stream()
			.map(UserRole::getValue)
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toSet());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
