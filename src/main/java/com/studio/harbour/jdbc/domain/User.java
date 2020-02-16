package com.studio.harbour.jdbc.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	Long id;

	private String email;
	private String username;
	private String password;
	private String bio;
	private String image;

	private Set<FollowRef> follows = new HashSet<FollowRef>();

	public void follow(User target) {
		follows.add(createFollowRef(target));
	}
	
	public void unfollow(User target) {
		for(FollowRef ref: follows) {
			if(ref.follow == target.id) {
				follows.remove(ref);
				break;
			}
		}
	}

	private FollowRef createFollowRef(User target) {
		Assert.notNull(target, "User must not be null");
		Assert.notNull(target.id, "User id must not be null");

		FollowRef followRef = new FollowRef();
		followRef.follow = target.id;
		return followRef;
	}
}
