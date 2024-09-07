package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User create(User user) {

        return null;
    }

    @Override
    public User readById(long id) {

        return null;
    }

    @Override
    public User update(User user) {

        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<User> getAll() {

        return Collections.emptyList();
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(long id) {

        return Optional.empty();
    }

}

