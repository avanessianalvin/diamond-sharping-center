package com.vozni.springjwt.model.repository;

import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {

    public static Specification<User> usernameContains(String username) {
        return (root, query, cb) ->
                (username == null || username.isBlank())? cb.conjunction()
                        : cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<User> addressEquals(String email) {
        return (root, query, cb) ->
                email == null ? cb.conjunction()
                        : cb.equal(root.get("address"), email);
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, cb) ->
            (role == null || role.isBlank())? cb.conjunction():
            // join to the roles collection
            cb.equal(cb.upper(root.join("roleSet").get("role")), role.toUpperCase());
    }

}
