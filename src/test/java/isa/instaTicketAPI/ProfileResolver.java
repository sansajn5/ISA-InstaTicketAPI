package isa.instaTicketAPI;

import org.springframework.test.context.ActiveProfilesResolver;

public class ProfileResolver implements ActiveProfilesResolver {

	@Override
	public String[] resolve(Class<?> arg0) {
		// TODO Auto-generated method stub
		return new String[] {"test"};
	}

}
