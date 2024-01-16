package com.User.ums.ServiceImpl;

 import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.User.ums.Entity.User;
import com.User.ums.Repository.UserRepository;
import com.User.ums.Service.UserService;
import com.User.ums.Utility.ResponseStructure;
import com.User.ums.exception.UserNotFoundByIdException;
import com.User.ums.requestdto.UserRequest;
import com.User.ums.responsedto.UserResponse;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ResponseStructure responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) {
		User user = userRepo.save(mapToUser(userRequest));
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("user saved sucessfully");
		responseStructure.setData(mapToUserResponse(user));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(User updateduser, int userid) {
		User user = userRepo.findById(userid).map(u -> {
			updateduser.setUserId(userid);
			return userRepo.save(updateduser);
		}).orElseThrow(() -> new UserNotFoundByIdException("user id not found"));
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("user updated sucessfully");
		responseStructure.setData(mapToUserResponse(user));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);
	}

	private User mapToUser(UserRequest request) {
		return User.builder().userName(request.getUsername()).userEmail(request.getEmail())
				.userPassword(request.getPassword()).build();

	}
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().username(user.getUserName()).email(user.getUserEmail())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<List<UserResponse>>> fetchAll() {
		List<User> userList=userRepo.findAll();
		List<UserResponse> userResponseList= userList.stream()
                .map(user -> mapToUserResponse(user))
                .collect(Collectors.toList());
		responseStructure.setStatus(HttpStatus.FOUND.value());
		responseStructure.setMessage("user found sucessfully");
		responseStructure.setData(userResponseList);
		return new ResponseEntity<ResponseStructure<List<UserResponse>>>(responseStructure, HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundByIdException("User id doesn't exist") );
		userRepo.delete(user);
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("user Deleted sucessfully");
		responseStructure.setData(mapToUserResponse(user));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> fetchUserById(int userId) {
		User user=userRepo.findById(userId).orElseThrow(()-> new UserNotFoundByIdException(" Id Not found") );
		
		responseStructure.setStatus(HttpStatus.FOUND.value());
		responseStructure.setMessage("user found sucessfully");
		responseStructure.setData(mapToUserResponse(user));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.FOUND);
	}
}
