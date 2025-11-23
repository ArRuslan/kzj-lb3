package ua.nure.kz.model;

import jakarta.persistence.*;
import lombok.*;
import ua.nure.kz.utils.HexUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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

	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA3-256");
		byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		return HexUtils.bytesToHex(passwordHash);
	}

	public boolean checkPassword(String check) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA3-256");
		} catch (NoSuchAlgorithmException exc) {
			//log.error("SHA3-256 is not supported ig", exc);
			return false;
		}

		byte[] bytesToCheck = digest.digest(check.getBytes(StandardCharsets.UTF_8));
		byte[] passwordHash = HexUtils.hexToBytes(password);

		return Arrays.equals(bytesToCheck, passwordHash);
	}

	public enum Role {
		USER,
		ADMIN,
	}
}
