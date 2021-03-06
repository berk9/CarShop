package com.tusofia.project.carshop.service;

import com.tusofia.project.carshop.database.entity.Role;
import com.tusofia.project.carshop.database.entity.User;
import com.tusofia.project.carshop.database.repository.UserRepository;
import com.tusofia.project.carshop.dto.binding.auth.UserAddBindingModel;
import com.tusofia.project.carshop.dto.binding.auth.UserServiceModel;
import com.tusofia.project.carshop.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createAndLoginUser(UserAddBindingModel userModel) {
        User newUser = createUser(userModel);
        User user = loadUserByUsername(newUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                userModel.getPassword(),
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional
    public User createUser(UserAddBindingModel userModel) {
        LOGGER.info("Creating a new user with username {}.", userModel.getUsername());
        User user = this.modelMapper.map(userModel, User.class);

        if (userModel.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        }

        Role customerRole = this.roleService.findRoleByName("ROLE_CUSTOMER");
        user.setRoles(Set.of(customerRole));
        return userRepository.save(user);
    }

    @Transactional
    public void editUser(Long userId, UserServiceModel userModel){
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with given id was not found!"));

        user.setName(userModel.getName());
        user.setSurname(userModel.getSurname());

        if(!user.getEmail().equals(userModel.getEmail())){
            user.setEmail(userModel.getEmail());
        }
        if(!user.getPhoneNumber().equals(userModel.getPhoneNumber())){
            user.setPhoneNumber(userModel.getPhoneNumber());
        }

        user.setCity(userModel.getCity());
        user.setAddress(userModel.getAddress());
        this.userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User findUserById(Long userId){
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with given id was not found!"));
    }

    public boolean isUsernameAvailable(String username){
        return userRepository.findByUsername(username).isPresent();
    }
}
