package ua.nure.kz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Getter
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	private long id;

	@Setter
	@Column(unique = true, nullable = false)
	private String login;

	@Setter
	@Column(nullable = false)
	private String fullName;

	@Setter
	@Column(nullable = false)
	private String password;

	@Setter
	@Column(nullable = false)
	private Role role;

	@ManyToMany
	@JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> groups;

	public enum Role {
		USER,
		ADMIN,
	}
}
