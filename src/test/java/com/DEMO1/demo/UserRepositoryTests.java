package com.DEMO1.demo;
import static org.assertj.core.api.Assertions.assertThat;
import com.DEMO1.demo.model.User;
import com.DEMO1.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){
        User user=new User();
        user.setUsername("agent023");
        user.setPassword("023");
        user.setEmail("brian023@gmail.com");
        user.setName("briam");
        User savedUser=userRepository.save(user);

       User existUser = entityManager.find(User.class,savedUser.getId());
       assertThat(existUser.getEmail()).isEqualTo(user.getEmail());

    }
}
