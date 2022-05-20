package com.bokchoywarrior.api.repositories;

import com.bokchoywarrior.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findOneByUsername(String username);
}
