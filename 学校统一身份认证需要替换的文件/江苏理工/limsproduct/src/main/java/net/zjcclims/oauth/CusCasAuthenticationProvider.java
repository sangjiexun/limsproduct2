package net.zjcclims.oauth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * This implementation for AuthenticationUserDetailsService wraps a regular
 * Spring Security UserDetailsService implementation, to retrieve a UserDetails
 * object based on the user name contained in an <tt>Authentication</tt> object.
 *
 * @author Ruud Senden
 * @author Scott Battaglia
 * @since 2.0
 */
public class CusCasAuthenticationProvider<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {
	private UserDetailsService userDetailsService = null;

	/**
	 * Constructs an empty wrapper for compatibility with Spring Security
	 * 2.0.x's method of using a setter.
	 */
	public CusCasAuthenticationProvider() {
		// constructor for backwards compatibility with 2.0
	}

	/**
	 * Constructs a new wrapper using the supplied
	 * {@link org.springframework.security.core.userdetails.UserDetailsService}
	 * as the service to delegate to.
	 *
	 * @param userDetailsService
	 *            the UserDetailsService to delegate to.
	 */
	public CusCasAuthenticationProvider(final UserDetailsService userDetailsService) {
		Assert.notNull(userDetailsService, "userDetailsService cannot be null.");
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Check whether all required properties have been set.
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "UserDetailsService must be set");
	}

	/**
	 * Get the UserDetails object from the wrapped UserDetailsService
	 * implementation
	 */
	public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
		return userDetails;
	}

	/**
	 * Set the wrapped UserDetailsService implementation
	 *
	 * @param aUserDetailsService
	 *            The wrapped UserDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService aUserDetailsService) {
		this.userDetailsService = aUserDetailsService;
	}
}
