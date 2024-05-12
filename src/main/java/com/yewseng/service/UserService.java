package com.yewseng.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yewseng.dto.LdapUser;
import com.yewseng.dto.User;
import com.yewseng.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
    @Autowired
    private LdapService ldapService;
		
	public User createUser(User user) {		
		LdapUser ldapUser = new LdapUser();
		ldapUser.setUsername(user.getUsername());
		ldapUser.setPassword(user.getPassword());
		ldapUser.setCn(user.getName());
		ldapUser.setSn(user.getEmail());
		ldapService.addUser(ldapUser);
		user.setPassword(BcryptService.hashPasssword(user.getPassword()));
		return userRepo.save(user);
	}
	
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    public User getUserById(String userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        return optionalUser.orElse(null);
    }
    
    public User updateUser(String userId, User updatedUser) {
    	Optional<User> optionalUser = userRepo.findById(userId);
    	if (optionalUser.isPresent()) {
    		 User existingUser = optionalUser.get();
    		 String uid = existingUser.getUsername();
    		 LdapUser updatedLdapUser = new LdapUser();
    		 updatedLdapUser.setUsername(updatedUser.getUsername());
    		 updatedLdapUser.setPassword(updatedUser.getPassword());
    		 updatedLdapUser.setCn(updatedUser.getName());
    		 updatedLdapUser.setSn(updatedUser.getEmail());
    		 ldapService.updateUser(uid, updatedLdapUser);
    		 existingUser.setEmail(updatedUser.getEmail());
    		 existingUser.setName(updatedUser.getName());
    		 existingUser.setUsername(updatedUser.getUsername());
    		 existingUser.setPassword(BcryptService.hashPasssword(updatedUser.getPassword()));
    		 return userRepo.save(existingUser);
    	}
    	return null;
    }
    
    public void deleteUser(String userId) {
    	Optional<User> optionalUser = userRepo.findById(userId);
    	if (optionalUser.isPresent()) {
    		User user = optionalUser.get();
    		String uid = user.getUsername();
    		ldapService.deleteUser(uid);
    		userRepo.delete(user);
    	}
    }
}
