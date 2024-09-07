package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.model.UserRole;
import com.softserve.itacademy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class ToDoListApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running Spring Boot Application");
		User newUser = new User();
		newUser.setEmail("valid@cv.edu.ua");
		newUser.setFirstName("Anna-Maria");
		newUser.setLastName("Kovalenko-Bynzar");
		newUser.setPassword("qwQW12!@");
		newUser.setRole(UserRole.USER);


		User savedUser = userRepository.save(newUser);
		System.out.println("User successfully saved with ID: " + savedUser.getId());

		User fetchedUser = userRepository.findById(savedUser.getId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		System.out.println("Fetched User: " + fetchedUser.getEmail() + ", Role: " + fetchedUser.getRole());
    }
}