package ua.nure.kz.model;

import jakarta.persistence.*;
import lombok.*;

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

	public enum Role {
		USER,
		ADMIN,
	}
}
