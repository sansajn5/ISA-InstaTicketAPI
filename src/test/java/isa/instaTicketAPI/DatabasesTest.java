package isa.instaTicketAPI;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles(resolver = ProfileResolver.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = InstaTicketApiApplication.class)
public class DatabasesTest {
	
	@Test
	public void contextLoads() {
		
	}
	
}