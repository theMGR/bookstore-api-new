package com.codesimple.bookstore.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.dto.LoginRequestDTO;
import com.codesimple.bookstore.dto.SignUpRequestDTO;
import com.codesimple.bookstore.entity.User;
import com.codesimple.bookstore.repo.UserRepository;
import com.codesimple.bookstore.util.JwtUtils;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtils jwtUtils;

	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
	public APIResponse signUp(SignUpRequestDTO signUpRequestDTO) throws Exception {
		APIResponse apiResponse = new APIResponse();

		Long usersCount = userRepository.countByEmailIdOrPhoneNumber(signUpRequestDTO.getEmailId(),
				signUpRequestDTO.getPhoneNumber());

		if (usersCount == 0) {
			// validation

			// dto to entity
			User userEntity = new User();
			userEntity.setName(signUpRequestDTO.getName());
			userEntity.setEmailId(signUpRequestDTO.getEmailId());
			userEntity.setActive(Boolean.TRUE);
			userEntity.setGender(signUpRequestDTO.getGender());
			userEntity.setPhoneNumber(signUpRequestDTO.getPhoneNumber());
			userEntity.setPassword(signUpRequestDTO.getPassword());

			// store entity
			userEntity = userRepository.save(userEntity);

			try {
				// generate jwt
				String token = jwtUtils.generateJwt(userEntity);

				Map<String, Object> data = new HashMap<>();
				data.put("accessToken", token);
				data.put("data", userEntity);

				apiResponse.setData(data);
			} catch (Exception e) {
				throw new Exception(e);
			}

		} else {
			apiResponse.setError("User already exists");
		}

		return apiResponse;
	}

	public APIResponse login(LoginRequestDTO loginRequestDTO) {

		APIResponse apiResponse = new APIResponse();

		Long usersCount = userRepository.countByEmailIdOrPhoneNumber(loginRequestDTO.getEmailId(), null);

		if (usersCount == 0) {
			apiResponse.setData("User not registered̥");
		} else {

			// validation

			// verify user exist with given email and password
			User user = userRepository.findOneByEmailIdIgnoreCaseAndPassword(loginRequestDTO.getEmailId(),
					loginRequestDTO.getPassword());

			// response
			if (user == null) {
				apiResponse.setData("User login failed");
			} else {

				// generate jwt
				String token = jwtUtils.generateJwt(user);

				Map<String, Object> data = new HashMap<>();
				data.put("accessToken", token);

				apiResponse.setData(data);
			}
		}
		return apiResponse;
	}

	public List<User> getAllUsers() {
		// APIResponse apiResponse = new APIResponse();

		List<User> userList = new ArrayList<>();

		userRepository.findAll().forEach(book -> userList.add(book));

		// apiResponse.setData(userList);

		return userList;
	}

}
